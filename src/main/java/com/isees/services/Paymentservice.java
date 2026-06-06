package com.isees.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.isees.dtos.ReceiptResponse;
import com.isees.dtos.StripeResponse;
import com.isees.entities.Application;
import com.isees.repos.ApplicationRepo;
import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.checkout.Session;
import com.stripe.param.checkout.SessionCreateParams;

@Service
public class Paymentservice {

    @Value("${stripe.secretKey}")
    private String secretKey;

    @Autowired
    private ApplicationRepo applicationRepo;

    // ================= CREATE STRIPE SESSION =================
    public StripeResponse checkoutProducts(String courseName, double fees, long applicationId) {

        try {
            Stripe.apiKey = secretKey;

            Application app = applicationRepo.findById(applicationId)
                    .orElseThrow(() -> new RuntimeException("Application not found"));

            long amountInPaise = (long) (fees * 100);

            SessionCreateParams.LineItem lineItem =
                    SessionCreateParams.LineItem.builder()
                            .setPriceData(
                                    SessionCreateParams.LineItem.PriceData.builder()
                                            .setCurrency("inr")
                                            .setUnitAmount(amountInPaise)
                                            .setProductData(
                                                    SessionCreateParams.LineItem.PriceData.ProductData.builder()
                                                            .setName(courseName)
                                                            .build()
                                            )
                                            .build()
                            )
                            .setQuantity(1L)
                            .build();

            SessionCreateParams params =
                    SessionCreateParams.builder()
                            .setMode(SessionCreateParams.Mode.PAYMENT)
                            .setSuccessUrl(
                                    "http://localhost:8080/payment-success.html" +
                                    "?appId=" + applicationId +
                                    "&session_id={CHECKOUT_SESSION_ID}"
                            )
                            .setCancelUrl("http://localhost:8080/cancel.html")

                            // ⭐⭐⭐ VERY IMPORTANT ⭐⭐⭐
                            .putMetadata("applicationId", String.valueOf(applicationId))
                            .putMetadata("course", courseName)

                            .addLineItem(lineItem)
                            .build();

            Session session = Session.create(params);

            return StripeResponse.builder()
                    .status("SUCCESS")
                    .message("Stripe session created")
                    .sessionId(session.getId())
                    .sessionUrl(session.getUrl())
                    .build();

        } catch (Exception e) {
            e.printStackTrace();
            return StripeResponse.builder()
                    .status("FAILED")
                    .message(e.getMessage())
                    .build();
        }
    }

    // ================= GET REAL PAID AMOUNT =================
    public double getRealAmountPaid(String sessionId) throws StripeException {
        Stripe.apiKey = secretKey;
        Session session = Session.retrieve(sessionId);
        return session.getAmountTotal() / 100.0;
    }

    // ================= GET RECEIPT DATA =================
    public ReceiptResponse getReceiptByApplicationId(Long appId){

        Application app = applicationRepo
                .findById(appId)
                .orElseThrow(() -> new RuntimeException("Application not found"));

        ReceiptResponse r = new ReceiptResponse();
        r.setReceiptNo("REC" + System.currentTimeMillis());
        r.setStudentName(app.getStudentname());
        r.setEmail(app.getEmailid());
        r.setCourse(app.getCoursename());
        r.setPaidAmount(app.getAmountPaid());

        return r;
    }

    public ReceiptResponse updatePaymentAndGetReceipt(Long appId, double amountPaid){

        // Fetch application
        Application app = applicationRepo.findById(appId)
                .orElseThrow(() -> new RuntimeException("Application not found"));

        // Update DB with actual amount
        app.setAmountPaid(amountPaid);
        applicationRepo.save(app);

        // Build receipt
        ReceiptResponse r = new ReceiptResponse();
        r.setReceiptNo("REC" + System.currentTimeMillis());
        r.setStudentName(app.getStudentname());
        r.setEmail(app.getEmailid());
        r.setCourse(app.getCoursename());
        r.setPaidAmount(app.getAmountPaid());

        return r;
    }
}

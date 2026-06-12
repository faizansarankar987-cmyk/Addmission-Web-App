package com.isees.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.isees.dtos.ReceiptResponse;
import com.isees.dtos.StripeResponse;
import com.isees.entities.Application;
import com.isees.services.ApplicationService;
import com.isees.services.Paymentservice;
import com.isees.services.RazorpayService;
import com.stripe.exception.StripeException;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/Courses")
@CrossOrigin("*")
public class PaymentControll {

    @Autowired
    private Paymentservice stripeService;

    @Autowired
    private RazorpayService razorpayService;

    @Autowired
    private ApplicationService applicationService;

    // =====================================================
    // 1️⃣ CREATE STRIPE CHECKOUT SESSION
    // =====================================================
    @PostMapping("/checkout")
    public ResponseEntity<StripeResponse> checkout(@RequestBody Map<String, Object> data) {

        Object courseObj = data.get("coursename");
        Object feesObj = data.get("fees");
        Object appIdObj = data.get("applicationId");

        if (courseObj == null || feesObj == null || appIdObj == null) {
            return ResponseEntity.badRequest().body(
                StripeResponse.builder()
                    .status("FAILED")
                    .message("coursename, fees, and applicationId are required")
                    .build()
            );
        }

        StripeResponse resp = stripeService.checkoutProducts(
                courseObj.toString(),
                Double.parseDouble(feesObj.toString()),
                Long.parseLong(appIdObj.toString())
        );

        return ResponseEntity.ok(resp);
    }

    // =====================================================
    // 2️⃣ GET REAL AMOUNT PAID FROM STRIPE
    // =====================================================
    @GetMapping("/paymentStatus")
    public ResponseEntity<?> paymentStatus(@RequestParam String session_id) throws Exception {
        double amount = stripeService.getRealAmountPaid(session_id);
        return ResponseEntity.ok(Map.of("amountPaid", amount));
    }

    // =====================================================
    // 3️⃣ UPDATE PAYMENT AFTER STRIPE SUCCESS
    // =====================================================
    @PostMapping("/updatePayment")
    public ResponseEntity<String> updatePayment(@RequestBody Map<String,Object> data){

        if(data.get("email")==null || data.get("course")==null || data.get("amount")==null){
            return ResponseEntity.badRequest().body("Missing payment data");
        }

        String email = data.get("email").toString();
        String course = data.get("course").toString();
        double amount = Double.parseDouble(data.get("amount").toString());

        String msg = applicationService.updatePayment(email, course, amount);
        return ResponseEntity.ok(msg);
    }

    // =====================================================
    // 4️⃣ GENERATE RECEIPT AFTER STRIPE PAYMENT
    // =====================================================
    @GetMapping("/receipt/{appId}/{sessionId}")
    public ReceiptResponse getReceipt(@PathVariable Long appId,
                                      @PathVariable String sessionId) throws StripeException {

        double realAmount = stripeService.getRealAmountPaid(sessionId);
        return stripeService.updatePaymentAndGetReceipt(appId, realAmount);
    }

    // =====================================================
    // 5️⃣ STRIPE SUCCESS REDIRECT HANDLER
    // =====================================================
    @GetMapping("/payment-success")
    public ResponseEntity<Map<String, Object>> stripePaymentSuccess(
            @RequestParam Long appId,
            @RequestParam String session_id) {

        try {
            double paidAmount = stripeService.getRealAmountPaid(session_id);
            Application app = applicationService.updatePaymentAfterStripe(appId, paidAmount, session_id);

            Map<String, Object> resp = new HashMap<>();
            resp.put("status", "SUCCESS");
            resp.put("amountPaid", paidAmount);
            resp.put("applicationId", app.getId());
            resp.put("studentName", app.getStudentname());
            resp.put("email", app.getEmailid());
            resp.put("course", app.getCoursename());

            return ResponseEntity.ok(resp);

        } catch (Exception e) {
            return ResponseEntity.status(500)
                    .body(Map.of("status","FAILED", "message", e.getMessage()));
        }
    }

    // =====================================================
    // 6️⃣ UPI SCREENSHOT UPLOAD (STUDENT SIDE)
    // =====================================================
//    @PostMapping("/upload-upi-proof")
//    public ResponseEntity<?> uploadUpiProof(
//            @RequestParam MultipartFile file,
//            @RequestParam Long applicationId) throws Exception {
//
//        // create uploads folder if not exists
//        Path uploadDir = Paths.get("uploads");
//        Files.createDirectories(uploadDir);
//
//        String fileName = "UPI_" + applicationId + "_" + file.getOriginalFilename();
//        Path filePath = uploadDir.resolve(fileName);
//
//        Files.write(filePath, file.getBytes());
//
//        // save file name + set status PENDING_VERIFICATION
//        applicationService.saveUpiScreenshot(applicationId, fileName);
//
//        return ResponseEntity.ok("UPI proof uploaded successfully");
//    }
//
//    // =====================================================
//    // 7️⃣ ADMIN VERIFY UPI PAYMENT
//    // =====================================================
//    @PostMapping("/verify-upi/{id}")
//    public ResponseEntity<?> verifyUpi(@PathVariable Long id){
//        applicationService.verifyUpiPayment(id);
//        return ResponseEntity.ok("UPI Payment Verified");
//    }
 // =====================================================
 // UPI DIRECT PAYMENT (NO SCREENSHOT)
 // =====================================================
 @PostMapping("/upi-pay")
 public ResponseEntity<?> upiDirectPayment(@RequestBody Map<String,Object> data){

     if(data.get("applicationId")==null || data.get("amount")==null){
         return ResponseEntity.badRequest().body("Missing data");
     }

     Long appId = Long.parseLong(data.get("applicationId").toString());
     double amount = Double.parseDouble(data.get("amount").toString());

     applicationService.addUpiPaymentDirect(appId, amount);

     return ResponseEntity.ok("UPI Payment added successfully");
 }
 @PostMapping("/upi/create-order")
 public ResponseEntity<?> createUpiOrder(@RequestBody Map<String,Object> data) throws Exception {

     Long appId = Long.parseLong(data.get("applicationId").toString());
     double amount = Double.parseDouble(data.get("amount").toString());

     var order = razorpayService.createOrder(amount, appId);

     Map<String,Object> resp = new HashMap<>();
     resp.put("orderId", order.get("id"));
     resp.put("amount", order.get("amount"));

     return ResponseEntity.ok(resp);
 }
 @PostMapping("/upi/verify")
 public ResponseEntity<?> verifyUpiPayment(@RequestBody Map<String,Object> data){

     Long appId = Long.parseLong(data.get("applicationId").toString());
     double amount = Double.parseDouble(data.get("amount").toString());

     applicationService.addUpiPaymentDirect(appId, amount);

     return ResponseEntity.ok("UPI Payment Success");
 }


}

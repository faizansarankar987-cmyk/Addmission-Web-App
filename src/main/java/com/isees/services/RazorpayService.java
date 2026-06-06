package com.isees.services;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import com.razorpay.RazorpayClient;
import org.json.JSONObject;
import java.util.HashMap;
import java.util.Map;

@Service
public class RazorpayService {

    @Value("${razorpay.key.id}")
    private String keyId;

    @Value("${razorpay.key.secret}")
    private String keySecret;

    private RazorpayClient getClient() throws Exception {
        return new RazorpayClient(keyId, keySecret);
    }

    // ⭐ THIS METHOD YOUR CONTROLLER IS CALLING
    public Map<String, Object> createOrder(double amount, Long appId) throws Exception {

        RazorpayClient client = getClient();

        JSONObject options = new JSONObject();
        options.put("amount", (int)(amount * 100)); // rupees → paisa
        options.put("currency", "INR");
        options.put("receipt", "app_" + appId);

        var order = client.orders.create(options);

        Map<String, Object> response = new HashMap<>();
        response.put("id", order.get("id"));
        response.put("amount", order.get("amount"));

        return response;
    }
}

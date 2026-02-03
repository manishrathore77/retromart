package com.thinking.machines.retro.controller;

import com.razorpay.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.json.JSONObject;

@RestController
@RequestMapping("/api/payment")
public class RazorpayOrderController {

    private static final String KEY_ID = "rzp_test_IZd6puJn8y7Y2Y";
    private static final String KEY_SECRET = "hONgIwCseqXJeuFv3abmMJ0X";

    @PostMapping("/razorpay-order")
    public ResponseEntity<?> createOrder(@RequestBody RazorRequest razorReq) {
        try {
            RazorpayClient client = new RazorpayClient(KEY_ID, KEY_SECRET);

            JSONObject options = new JSONObject();
            options.put("amount", razorReq.amount * 100); // amount in paise
            options.put("currency", "INR");
            options.put("receipt", "txn_" + System.currentTimeMillis());

            Order order = client.orders.create(options);

            return ResponseEntity.ok(new RazorResponse(order.get("id"), KEY_ID));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body("Error creating Razorpay order: " + e.getMessage());
        }
    }

    // Request class for incoming data
    static class RazorRequest {
        public int amount;
    }

    // Response class for frontend
    static class RazorResponse {
        public String orderId;
        public String key;
        public RazorResponse(String orderId, String key) {
            this.orderId = orderId;
            this.key = key;
        }
    }
}

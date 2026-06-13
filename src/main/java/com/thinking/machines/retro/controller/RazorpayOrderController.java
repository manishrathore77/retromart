package com.thinking.machines.retro.controller;

import com.razorpay.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.json.JSONObject;

@RestController
@RequestMapping("/api/payment")
public class RazorpayOrderController {

    @Value("${razorpay.key.id:}")
    private String keyId;

    @Value("${razorpay.key.secret:}")
    private String keySecret;

    @Value("${razorpay.demo-mode:false}")
    private boolean demoMode;

    @PostMapping("/razorpay-order")
    public ResponseEntity<?> createOrder(@RequestBody RazorRequest razorReq) {
        if (razorReq.amount <= 0) {
            return ResponseEntity.badRequest().body("Invalid amount");
        }

        if (demoMode) {
            return ResponseEntity.ok(new RazorResponse(
                "demo_order_" + System.currentTimeMillis(),
                "demo_key",
                true,
                "Demo mode — no real charge"
            ));
        }

        if (keyId.isBlank() || keySecret.isBlank()) {
            return ResponseEntity.status(500).body("Razorpay keys not configured");
        }

        try {
            RazorpayClient client = new RazorpayClient(keyId, keySecret);

            JSONObject options = new JSONObject();
            options.put("amount", Math.round(razorReq.amount * 100));
            options.put("currency", "INR");
            options.put("receipt", "retro_order_" + System.currentTimeMillis());

            Order order = client.orders.create(options);
            return ResponseEntity.ok(new RazorResponse(order.get("id"), keyId, false, null));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body("Razorpay error: " + e.getMessage());
        }
    }

    static class RazorRequest {
        public double amount;
    }

    static class RazorResponse {
        public String orderId;
        public String key;
        public boolean demo;
        public String message;

        public RazorResponse(String orderId, String key, boolean demo, String message) {
            this.orderId = orderId;
            this.key = key;
            this.demo = demo;
            this.message = message;
        }
    }
}

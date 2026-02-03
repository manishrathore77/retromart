package com.thinking.machines.retro.dao;

import com.thinking.machines.retro.modal.Order;
import com.thinking.machines.retro.utility.RetroConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class OrderDAO {

    public int addOrder(Order order) throws SQLException {
        Connection conn = RetroConnection.getConnection();
        String sql = "INSERT INTO orders (buyer_id, product_id, payment_status, delivery_status, shipping_address, contact_phone, payment_mode, transaction_id) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
        stmt.setInt(1, order.getBuyerId());
        stmt.setInt(2, order.getProductId());
        stmt.setString(3, order.getPaymentStatus());
        stmt.setString(4, order.getDeliveryStatus());
        stmt.setString(5, order.getShippingAddress());
        stmt.setString(6, order.getContactPhone());
        stmt.setString(7, order.getPaymentMode());
        stmt.setString(8, order.getTransactionId());

        stmt.executeUpdate();
        ResultSet rs = stmt.getGeneratedKeys();
        int id = -1;
        if (rs.next()) id = rs.getInt(1);

        rs.close();
        stmt.close();
        conn.close();
        return id;
    }

public List<Order> getAllOrders() throws SQLException {
    List<Order> orders = new ArrayList<>();
    Connection con = RetroConnection.getConnection();
    PreparedStatement ps = con.prepareStatement("SELECT * FROM orders");
    ResultSet rs = ps.executeQuery();
    while (rs.next()) {
        Order o = new Order();
        o.setOrderId(rs.getInt("order_id"));
        o.setBuyerId(rs.getInt("buyer_id"));
        o.setProductId(rs.getInt("product_id"));
        o.setOrderDate(rs.getString("order_date"));
        o.setPaymentStatus(rs.getString("payment_status"));
        o.setDeliveryStatus(rs.getString("delivery_status"));
        o.setShippingAddress(rs.getString("shipping_address"));
        o.setContactPhone(rs.getString("contact_phone"));
        o.setPaymentMode(rs.getString("payment_mode"));
        o.setTransactionId(rs.getString("transaction_id"));
        orders.add(o);
    }
    rs.close();
    ps.close();
    con.close();
    return orders;
}


public boolean confirmPayment(int orderId, String transactionId, String paymentStatus) throws SQLException {
    String sql = "UPDATE orders SET transaction_id=?, payment_status=? WHERE order_id=?";
    try (Connection conn = RetroConnection.getConnection();
         PreparedStatement stmt = conn.prepareStatement(sql)) {
        stmt.setString(1, transactionId);
        stmt.setString(2, paymentStatus);
        stmt.setInt(3, orderId);
        return stmt.executeUpdate() == 1;
    }
}


    public List<Order> getOrdersByBuyer(int buyerId) throws SQLException {
        Connection conn = RetroConnection.getConnection();
        String sql = "SELECT * FROM orders WHERE buyer_id = ?";
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setInt(1, buyerId);
        ResultSet rs = stmt.executeQuery();

        List<Order> orders = new ArrayList<>();
        while (rs.next()) {
            Order o = new Order();
            o.setOrderId(rs.getInt("order_id"));
            o.setBuyerId(rs.getInt("buyer_id"));
            o.setProductId(rs.getInt("product_id"));
            o.setOrderDate(rs.getString("order_date"));
            o.setPaymentStatus(rs.getString("payment_status"));
            o.setDeliveryStatus(rs.getString("delivery_status"));
            o.setShippingAddress(rs.getString("shipping_address"));
            o.setContactPhone(rs.getString("contact_phone"));
            o.setPaymentMode(rs.getString("payment_mode"));
            o.setTransactionId(rs.getString("transaction_id"));
            orders.add(o);
        }

        rs.close();
        stmt.close();
        conn.close();
        return orders;
    }

    public boolean updatePaymentStatus(int orderId, String status, String txnId) throws SQLException {
        Connection conn = RetroConnection.getConnection();
        String sql = "UPDATE orders SET payment_status = ?, transaction_id = ? WHERE order_id = ?";
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setString(1, status);
        stmt.setString(2, txnId);
        stmt.setInt(3, orderId);
        int rows = stmt.executeUpdate();

        stmt.close();
        conn.close();
        return rows > 0;
    }

    public boolean updateDeliveryStatus(int orderId, String status) throws SQLException {
        Connection conn = RetroConnection.getConnection();
        String sql = "UPDATE orders SET delivery_status = ? WHERE order_id = ?";
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setString(1, status);
        stmt.setInt(2, orderId);
        int rows = stmt.executeUpdate();

        stmt.close();
        conn.close();
        return rows > 0;
    }
}

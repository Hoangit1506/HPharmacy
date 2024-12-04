package com.project.HPharmacy.entity;

public enum OrderStatus {
    PENDING,      // Đơn hàng đang chờ xử lý
    CONFIRMED,    // Đơn hàng đã được xác nhận
    DELIVERING,   // Đơn hàng đang được giao
    DELIVERED,    // Đơn hàng đã giao thành công
    COMPLETED,    // Đơn hàng hoàn tất
    CANCELED,     // Đơn hàng đã hủy
    REFUNDED      // Đơn hàng đã được hoàn tiền
}

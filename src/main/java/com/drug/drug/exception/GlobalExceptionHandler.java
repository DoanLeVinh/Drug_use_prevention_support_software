package com.drug.drug.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ClassCastException.class)
    public ResponseEntity<String> handleClassCastException(ClassCastException ex) {
        // Ghi log nhẹ
        System.out.println("🚫 ClassCastException bị bắt: " + ex.getMessage());
        // Không trả stack trace, trả thông báo nhẹ
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body("Lỗi dữ liệu không hợp lệ: sai kiểu.");
    }

    // Bạn có thể bắt thêm Exception khác nếu cần
    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleOtherException(Exception ex) {
        System.out.println("🚫 Lỗi khác: " + ex.getMessage());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("Lỗi hệ thống, hãy thử lại sau.");
    }
}

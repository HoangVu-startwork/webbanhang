package com.example.webbanhang.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;

import lombok.Getter;

@Getter
public enum ErrorCode {
    SIZE_PASSWORD(1002, "Mật khẩu phải có ít nhất 8 ký tự.", HttpStatus.BAD_GATEWAY),
    SO_PASSWORD(1003, "Mật khẩu phải chứa ít nhất một chữ số.", HttpStatus.BAD_GATEWAY),
    CHUTHUONG_PASSWORD(1004, "Mật khẩu phải chứa ít nhất một chữ thưởng.", HttpStatus.BAD_GATEWAY),
    CHUHOA_PASSWORD(1005, "Mật khẩu phải chứa ít nhất một chữ hoa.", HttpStatus.BAD_GATEWAY),
    KYTUDATBIET_PASSWORD(1006, "Mật khẩu phải chứa ít nhất một ký tự đặc biệt.", HttpStatus.BAD_GATEWAY),
    KHOANGTRANG_PASSWORD(1007, "Mật khẩu không được chứa khoảng trắng.", HttpStatus.BAD_GATEWAY),
    UNCATEGORIZED_EXCEPTION(9999, "Uncategorized error", HttpStatus.INTERNAL_SERVER_ERROR),
    USER_EXISTED(1001, "Email đã tồn tại.", HttpStatus.BAD_GATEWAY),
    USER_NOT_EXISTED(1008, "Tài khoản không tồn tại.", HttpStatus.NOT_FOUND),
    UNAUTHENTICATED(1006, "Chưa đăng nhập", HttpStatus.UNAUTHORIZED),
    UNAUTHENTICATED_PASSWORD(1006, "Tài khoản và mật khẩu không đúng", HttpStatus.UNAUTHORIZED),
    UNAUTHORIZED(10011, "You do not have permission", HttpStatus.FORBIDDEN),
    INTERNAL_SERVER_ERROR(1010, "Gửi email bị lỗi", HttpStatus.BAD_GATEWAY),
    INVALID_KEY(1999, "Lỗi Key không đúng", HttpStatus.BAD_GATEWAY),

    TENSANPHAM(5000, "Tên sản phẩm không được để trống", HttpStatus.BAD_GATEWAY),
    BAOHANH(5001, "Bảo hành không được để trống", HttpStatus.BAD_GATEWAY),
    THIETBIDIKEM(5002, "Thiết bị đi kèm không được để trống", HttpStatus.BAD_GATEWAY),
    TINHTRANGMAY(5003, "Tình trạng máy không được để trống", HttpStatus.BAD_GATEWAY),
    TENDIENTHOAI(5004, "Không tìm thấy điện thoại", HttpStatus.BAD_GATEWAY),
    THONGTINDIENTHOAI(5005, "Thông tin của điện thoại đã tồn tại", HttpStatus.BAD_GATEWAY),
    UPDATETHONGTINDIENTHOAI(5006, "Thông tin điện thoại không tồn tại", HttpStatus.BAD_GATEWAY),
    THONGSOKYTHUAT(5007, "Thông số kỹ thuật của điện thoại đã tồn tại", HttpStatus.BAD_GATEWAY),
    THONGTINPHANLOAI(5008, "Thông tin phân loại đã tồn tại", HttpStatus.BAD_GATEWAY),
    LOAISANPHAM(5009, "Loại sản phẩm không tồn tại", HttpStatus.BAD_GATEWAY),
    TENDANHMUC(5011, "Tên danh mục đã tồn tại", HttpStatus.BAD_GATEWAY),
    MUCLUC(5012, "Mục lục không tồn tại", HttpStatus.BAD_GATEWAY),
    HEDIEUHANH(5013, "Hệ điều hành không tồn tại", HttpStatus.BAD_GATEWAY),
    HEDIEUHANHTONTAI(5020, "Hệ điều hành tồn tại", HttpStatus.BAD_GATEWAY),
    DANHMUC(5014, "Danh mục không tồn tại", HttpStatus.BAD_GATEWAY),
    DIENTHOAI(5015, "Diện thoại đã tồn tại", HttpStatus.BAD_GATEWAY),
    MAUSAC(5016, "Màu sắc không tồn tại", HttpStatus.BAD_GATEWAY),
    THONGTINPHANLOAIDIENTHOAI(5017, "Thông tin phân loại không tồn tại", HttpStatus.BAD_GATEWAY),
    KHOHANG(5018, "Hiện tại không còn hàng", HttpStatus.BAD_GATEWAY),
    KHOHANGGIOHANG(5019, "Hiện tại không còn đủ hàng", HttpStatus.BAD_GATEWAY),
    MAUSACVSDIENTHOAI(5021, "Sản phẩm này với màu đã chọn đã tồn tại trong kho", HttpStatus.BAD_GATEWAY),
    LOAISANPHAMTONTAI(5022, "Loại sản phẩm đã tồn tại", HttpStatus.BAD_GATEWAY),
    MAUSACTONTAI(5023, "Màu sắc đã tồn tại", HttpStatus.BAD_GATEWAY),
    MAUSACTONTAITRONGDIENTHOAI(5024, "Màu sắc đã tồn tại trong điện thoại này", HttpStatus.BAD_GATEWAY),
    MUCLUCTONTAI(5015, "Mục lục đã tồn tại", HttpStatus.BAD_GATEWAY),
    SANPHAMHOADON(5015, "Sản phẩm đã hết", HttpStatus.BAD_GATEWAY),
    NHAPKHO(5015, "Dữ liệu không tồn tại", HttpStatus.BAD_GATEWAY),
    INVALID_DOB(2010, "Ngày sinh không hợp lệ {min}", HttpStatus.BAD_REQUEST),
    HOADON(2022, "Hoá đon không tồn tại", HttpStatus.BAD_REQUEST);

    ErrorCode(int code, String message, HttpStatusCode statusCode) {
        this.code = code;
        this.message = message;
        this.statusCode = statusCode;
    }

    private int code;
    private String message;
    private HttpStatusCode statusCode;
}

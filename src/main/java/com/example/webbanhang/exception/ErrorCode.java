package com.example.webbanhang.exception;

import org.springframework.http.HttpStatus;

import lombok.Getter;

@Getter
public enum ErrorCode {
    SIZE_PASSWORD(1002, "Mật khẩu phải có ít nhất 8 ký tự.", HttpStatus.BAD_REQUEST),
    SO_PASSWORD(1003, "Mật khẩu phải chứa ít nhất một chữ số.", HttpStatus.BAD_REQUEST),
    CHUTHUONG_PASSWORD(1004, "Mật khẩu phải chứa ít nhất một chữ thường.", HttpStatus.BAD_REQUEST),
    CHUHOA_PASSWORD(1005, "Mật khẩu phải chứa ít nhất một chữ hoa.", HttpStatus.BAD_REQUEST),
    KYTUDATBIET_PASSWORD(1006, "Mật khẩu phải chứa ít nhất một ký tự đặc biệt.", HttpStatus.BAD_REQUEST),
    KHOANGTRANG_PASSWORD(1007, "Mật khẩu không được chứa khoảng trắng.", HttpStatus.BAD_REQUEST),
    UNCATEGORIZED_EXCEPTION(9999, "Uncategorized error", HttpStatus.INTERNAL_SERVER_ERROR),
    USER_EXISTED(1001, "Email đã tồn tại.", HttpStatus.BAD_REQUEST),
    USER_NOT_EXISTED(1008, "Tài khoản không tồn tại.", HttpStatus.NOT_FOUND),
    UNAUTHENTICATED(1006, "Chưa đăng nhập", HttpStatus.UNAUTHORIZED),
    UNAUTHENTICATED_PASSWORD(1006, "Tài khoản và mật khẩu không đúng", HttpStatus.UNAUTHORIZED),
    UNAUTHORIZED(10011, "You do not have permission", HttpStatus.FORBIDDEN),
    INTERNAL_SERVER_ERROR(1010, "Gửi email bị lỗi", HttpStatus.INTERNAL_SERVER_ERROR),
    INVALID_KEY(1999, "Lỗi Key không đúng", HttpStatus.BAD_REQUEST),

    TENSANPHAM(5000, "Tên sản phẩm không được để trống", HttpStatus.BAD_REQUEST),
    BAOHANH(5001, "Bảo hành không được để trống", HttpStatus.BAD_REQUEST),
    THIETBIDIKEM(5002, "Thiết bị đi kèm không được để trống", HttpStatus.BAD_REQUEST),
    TINHTRANGMAY(5003, "Tình trạng máy không được để trống", HttpStatus.BAD_REQUEST),
    TENDIENTHOAI(5004, "Không tìm thấy điện thoại", HttpStatus.NOT_FOUND),
    THONGTINDIENTHOAI(5005, "Thông tin của điện thoại đã tồn tại", HttpStatus.BAD_REQUEST),
    UPDATETHONGTINDIENTHOAI(5006, "Thông tin điện thoại không tồn tại", HttpStatus.NOT_FOUND),
    THONGSOKYTHUAT(5007, "Thông số kỹ thuật của điện thoại đã tồn tại", HttpStatus.BAD_REQUEST),
    THONGTINPHANLOAI(5008, "Thông tin phân loại đã tồn tại", HttpStatus.BAD_REQUEST),
    LOAISANPHAM(5009, "Loại sản phẩm không tồn tại", HttpStatus.NOT_FOUND),
    TENDANHMUC(5011, "Tên danh mục đã tồn tại", HttpStatus.BAD_REQUEST),
    MUCLUC(5012, "Mục lục không tồn tại", HttpStatus.NOT_FOUND),
    HEDIEUHANH(5013, "Hệ điều hành không tồn tại", HttpStatus.NOT_FOUND),
    HEDIEUHANHTONTAI(5020, "Hệ điều hành tồn tại", HttpStatus.BAD_REQUEST),
    DANHMUC(5014, "Danh mục không tồn tại", HttpStatus.NOT_FOUND),
    DIENTHOAI(5015, "Diện thoại đã tồn tại", HttpStatus.BAD_REQUEST),
    MAUSAC(5016, "Màu sắc không tồn tại", HttpStatus.NOT_FOUND),
    THONGTINPHANLOAIDIENTHOAI(5017, "Thông tin phân loại không tồn tại", HttpStatus.NOT_FOUND),
    KHOHANG(5018, "Hiện tại không còn hàng", HttpStatus.BAD_REQUEST),
    KHOHANGGIOHANG(5019, "Hiện tại không còn đủ hàng", HttpStatus.BAD_REQUEST),
    MAUSACVSDIENTHOAI(5021, "Sản phẩm này với màu đã chọn đã tồn tại trong kho", HttpStatus.BAD_REQUEST),
    LOAISANPHAMTONTAI(5022, "Loại sản phẩm đã tồn tại", HttpStatus.BAD_REQUEST),
    MAUSACTONTAI(5023, "Màu sắc đã tồn tại", HttpStatus.BAD_REQUEST),
    MAUSACTONTAITRONGDIENTHOAI(5024, "Màu sắc đã tồn tại trong điện thoại này", HttpStatus.BAD_REQUEST),
    MUCLUCTONTAI(5025, "Mục lục đã tồn tại", HttpStatus.BAD_REQUEST),
    SANPHAMHOADON(5026, "Sản phẩm đã hết", HttpStatus.BAD_REQUEST),
    NHAPKHO(5027, "Dữ liệu không tồn tại", HttpStatus.NOT_FOUND),
    INVALID_DOB(2028, "Ngày sinh không hợp lệ {min}", HttpStatus.BAD_REQUEST),
    HOADON(5029, "Hoá đơn không tồn tại", HttpStatus.NOT_FOUND),
    KETNOINHUCAU(5030, "Nhu cầu điện thoại đã tồn tại", HttpStatus.BAD_REQUEST),
    NHUCAUDIENTHOAI(5031, "Nhu cầu điện thoại đã tồn tại", HttpStatus.BAD_REQUEST),
    YEUTHICH_EXISTED(5032, "Yêu thích đã tồn tại", HttpStatus.BAD_REQUEST),
    YEUTHICH_NOT_EXISTED(5033, "Yêu thích chưa tồn tại", HttpStatus.NOT_FOUND);

    private final int code;
    private final String message;
    private final HttpStatus statusCode;

    ErrorCode(int code, String message, HttpStatus statusCode) {
        this.code = code;
        this.message = message;
        this.statusCode = statusCode;
    }
}

package com.example.webbanhang.controller;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/anh")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UploadController {
    @Autowired
    private Cloudinary cloudinary;

    @PostMapping("/image")
    public ResponseEntity<String> uploadImage(@RequestParam("image") MultipartFile file) {
        try {
            // Kiểm tra nếu file rỗng
            if (file.isEmpty()) {
                log.warn("Uploaded file is empty.");
                return ResponseEntity.badRequest().body("File not found");
            }

            // Tải ảnh lên Cloudinary
            Map<?, ?> uploadResult =
                    cloudinary.uploader().upload(file.getBytes(), ObjectUtils.asMap("folder", "image-mucluc"));

            // Log kết quả upload
            log.info("Uploaded image URL: {}", uploadResult.get("secure_url"));

            // Trả về URL của ảnh đã upload
            return ResponseEntity.ok((String) uploadResult.get("secure_url"));
        } catch (IOException e) {
            log.error("Failed to upload image", e);
            return ResponseEntity.status(500).body("Failed to upload image: " + e.getMessage());
        } catch (Exception e) {
            log.error("An unexpected error occurred", e);
            return ResponseEntity.status(500).body("An unexpected error occurred: " + e.getMessage());
        }
    }

    @DeleteMapping("/image")
    public ResponseEntity<String> deleteImage(@RequestParam("imageUrl") String imageUrl) {
        try {
            // Trích xuất public_id từ URL
            String publicId = extractPublicIdFromUrl(imageUrl);
            if (publicId == null) {
                return ResponseEntity.badRequest().body("Invalid image URL");
            }

            // Xóa ảnh khỏi Cloudinary
            Map<?, ?> result = cloudinary.uploader().destroy(publicId, ObjectUtils.emptyMap());

            // Kiểm tra kết quả xóa
            String resultStatus = (String) result.get("result");
            if ("ok".equals(resultStatus)) {
                return ResponseEntity.ok("Image deleted successfully");
            } else {
                return ResponseEntity.status(400).body("Failed to delete image");
            }
        } catch (URISyntaxException e) {
            log.error("Invalid URL format", e);
            return ResponseEntity.badRequest().body("Invalid URL format: " + e.getMessage());
        } catch (Exception e) {
            log.error("Failed to delete image", e);
            return ResponseEntity.status(500).body("Failed to delete image: " + e.getMessage());
        }
    }

    private String extractPublicIdFromUrl(String imageUrl) throws URISyntaxException {
        URI uri = new URI(imageUrl);
        String path = uri.getPath(); // /du6ybb3by/image/upload/v1727429115/test-avart/file_xxcjpq.png
        String[] segments = path.split("/");

        // Public ID là tất cả sau "upload/" và trước ".<file extension>"
        String publicIdWithExtension = segments[segments.length - 1]; // file_xxcjpq.png
        String publicId = publicIdWithExtension.substring(0, publicIdWithExtension.lastIndexOf(".")); // file_xxcjpq
        return segments[segments.length - 2] + "/" + publicId; // test-avart/file_xxcjpq
    }

    @PostMapping("/image-dienthoai")
    public ResponseEntity<String> uploadImageDienthoai(@RequestParam("image") MultipartFile file) {
        try {
            // Kiểm tra nếu file rỗng
            if (file.isEmpty()) {
                log.warn("Uploaded file is empty.");
                return ResponseEntity.badRequest().body("File not found");
            }

            // Tải ảnh lên Cloudinary
            Map<?, ?> uploadResult =
                    cloudinary.uploader().upload(file.getBytes(), ObjectUtils.asMap("folder", "dienthoai"));

            // Log kết quả upload
            log.info("Uploaded image URL: {}", uploadResult.get("secure_url"));

            // Trả về URL của ảnh đã upload
            return ResponseEntity.ok((String) uploadResult.get("secure_url"));
        } catch (IOException e) {
            log.error("Failed to upload image", e);
            return ResponseEntity.status(500).body("Failed to upload image: " + e.getMessage());
        } catch (Exception e) {
            log.error("An unexpected error occurred", e);
            return ResponseEntity.status(500).body("An unexpected error occurred: " + e.getMessage());
        }
    }

    @PostMapping("/imagesd")
    public ResponseEntity<String> uploadImages(@RequestParam("images") MultipartFile[] files) {
        try {
            // Kiểm tra nếu không có file nào được upload
            if (files.length == 0) {
                log.warn("No files uploaded.");
                return ResponseEntity.badRequest().body("No files found");
            }

            // Danh sách để lưu trữ các URL đã upload
            List<String> uploadedUrls = new ArrayList<>();

            // Tải từng ảnh lên Cloudinary
            for (MultipartFile file : files) {
                if (file.isEmpty()) {
                    log.warn("One of the uploaded files is empty.");
                    continue; // Bỏ qua file rỗng
                }

                // Tải ảnh lên Cloudinary
                Map<?, ?> uploadResult =
                        cloudinary.uploader().upload(file.getBytes(), ObjectUtils.asMap("folder", "anhduyetdienthoai"));

                // Log kết quả upload
                log.info("Uploaded image URL: {}", uploadResult.get("secure_url"));

                // Thêm URL vào danh sách
                uploadedUrls.add((String) uploadResult.get("secure_url"));
            }

            // Trả về danh sách URL của các ảnh đã upload, ngăn cách bởi dấu phẩy
            String responseUrls = String.join(",", uploadedUrls);
            return ResponseEntity.ok(responseUrls);
        } catch (IOException e) {
            log.error("Failed to upload images", e);
            return ResponseEntity.status(500).body("Failed to upload images: " + e.getMessage());
        } catch (Exception e) {
            log.error("An unexpected error occurred", e);
            return ResponseEntity.status(500).body("An unexpected error occurred: " + e.getMessage());
        }
    }

    @DeleteMapping("/images")
    public ResponseEntity<String> deleteImages(@RequestParam("imageUrls") String imageUrls) {
        StringBuilder responseMessage = new StringBuilder();

        try {
            // Tách chuỗi URL thành mảng các URL
            String[] urlArray = imageUrls.split(",");

            for (String imageUrl : urlArray) {
                imageUrl = imageUrl.trim(); // Loại bỏ khoảng trắng thừa

                // Trích xuất public_id từ URL
                String publicId = extractPublicIdFromUrlxoa(imageUrl);
                if (publicId == null) {
                    responseMessage
                            .append("Invalid image URL: ")
                            .append(imageUrl)
                            .append("\n");
                    continue;
                }

                // Xóa ảnh khỏi Cloudinary
                Map<?, ?> result = cloudinary.uploader().destroy(publicId, ObjectUtils.emptyMap());

                // Kiểm tra kết quả xóa
                String resultStatus = (String) result.get("result");
                if ("ok".equals(resultStatus)) {
                    responseMessage
                            .append("Image deleted successfully: ")
                            .append(imageUrl)
                            .append("\n");
                } else {
                    responseMessage
                            .append("Failed to delete image: ")
                            .append(imageUrl)
                            .append("\n");
                }
            }
            return ResponseEntity.ok(responseMessage.toString());
        } catch (Exception e) {
            log.error("Failed to delete images", e);
            return ResponseEntity.status(500).body("Failed to delete images: " + e.getMessage());
        }
    }

    // Hàm để trích xuất public ID từ URL
    private String extractPublicIdFromUrlxoa(String imageUrl) throws URISyntaxException {
        URI uri = new URI(imageUrl);
        String path = uri.getPath(); // /du6ybb3by/image/upload/v1727429115/test-avart/file_xxcjpq.png
        String[] segments = path.split("/");

        // Public ID là tất cả sau "upload/" và trước ".<file extension>"
        String publicIdWithExtension = segments[segments.length - 1]; // file_xxcjpq.png
        String publicId = publicIdWithExtension.substring(0, publicIdWithExtension.lastIndexOf(".")); // file_xxcjpq
        return segments[segments.length - 2] + "/" + publicId; // test-avart/file_xxcjpq
    }
}

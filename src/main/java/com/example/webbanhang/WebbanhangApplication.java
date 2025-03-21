package com.example.webbanhang;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableScheduling;

// @SpringBootApplication được sử dụng với một tham số exclude, với mục đích loại bỏ một số cấu hình tự động mặc định mà
// Spring Boot cung cấp. Trong trường hợp này, SecurityAutoConfiguration.class được loại bỏ, điều này có nghĩa là cấu
// hình bảo mật tự động mà Spring Boot thường áp dụng sẽ không được kích hoạt trong ứng dụng. Điều này có thể là lựa
// chọn nếu bạn định cấu hình bảo mật theo cách của riêng mình, thay vì sử dụng cấu hình mặc định của Spring Boot.

@SpringBootApplication(scanBasePackages = "com.example.webbanhang")
@EnableJpaRepositories(basePackages = "com.example.webbanhang.repository")
@EntityScan(basePackages = "com.example.webbanhang.entity")
@EnableScheduling
public class WebbanhangApplication {
    public static void main(String[] args) {
        SpringApplication.run(WebbanhangApplication.class, args);
    }
}

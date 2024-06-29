package com.example.webbanhang.service;
import com.example.webbanhang.dto.request.AuthenticationRequest;
import com.example.webbanhang.dto.request.IntrospectRequest;
import com.example.webbanhang.dto.response.AuthenticationResponse;
import com.example.webbanhang.dto.response.IntrospectResponse;
import com.example.webbanhang.entity.User;
import com.example.webbanhang.exception.AppException;
import com.example.webbanhang.exception.ErrorCode;
import com.example.webbanhang.repository.UserRepository;
import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jose.crypto.MACVerifier;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.experimental.NonFinal;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.text.ParseException;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.StringJoiner;

@Service
@RequiredArgsConstructor
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AuthenticationService {
    UserRepository userRepository;

    @NonFinal
    @Value("${jwt.signerKey}")
    protected String SIGNER_KEY;
    public IntrospectResponse introspect(IntrospectRequest request)
            throws JOSEException, ParseException {
        var token = request.getToken();

        JWSVerifier verifier = new MACVerifier(SIGNER_KEY.getBytes());

        SignedJWT signedJWT = SignedJWT.parse(token);

        Date expiryTime = signedJWT.getJWTClaimsSet().getExpirationTime();

        var verified = signedJWT.verify(verifier);

        return IntrospectResponse.builder()
                .valid(verified && expiryTime.after(new Date()))
                .build();
    }


    public AuthenticationResponse authenticate(AuthenticationRequest request){
        var user = userRepository.findByEmail(request.getEmail()).orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));
        // Tìm kiếm người dùng trong cơ sở dữ liệu theo email. Nếu không tìm thấy, ném ra ngoại lệ AppException với mã lỗi USER_NOT_EXISTED.


        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(10);
        boolean authenticated = passwordEncoder.matches(request.getPassword(), user.getPassword());
        // Khởi tạo một đối tượng PasswordEncoder sử dụng thuật toán BCrypt với độ mạnh là 10. So sánh mật khẩu người dùng nhập vào với mật khẩu đã mã hóa trong cơ sở dữ liệu.

        if (!authenticated)
            throw new AppException(ErrorCode.UNAUTHENTICATED);
        // Nếu mật khẩu không khớp, ném ra ngoại lệ AppException với mã lỗi UNAUTHENTICATED.

        var token = generateToken(user);
        return AuthenticationResponse.builder().token(token).authenticated(true).build();
        // Nếu mật khẩu khớp, tạo một JWT bằng phương thức generateToken và trả về đối tượng AuthenticationResponse chứa token và trạng thái xác thực thành công.
    }

    private String generateToke(String email) {
        JWSHeader header = new JWSHeader(JWSAlgorithm.HS512);
        // Tạo header cho JWT sử dụng thuật toán ký HS512.

        JWTClaimsSet jwtClaimsSet = new JWTClaimsSet.Builder().subject(email)
                .issuer("devteria.com")
                .issueTime(new Date(
                Instant.now().plus(1, ChronoUnit.HOURS).toEpochMilli()
        ))
                .claim("customClaim", "Custom").build();
        // Tạo payload cho JWT với các thông tin:
        //subject: email của người dùng.
        //issuer: thông tin về nơi phát hành token (ở đây là "devteria.com").
        //issueTime: thời gian phát hành token (hiện tại cộng thêm 1 giờ).
        //customClaim: một custom claim.

        Payload payload = new Payload(jwtClaimsSet.toJSONObject());
        JWSObject jwsObject = new JWSObject(header, payload);
        try {
            jwsObject.sign(new MACSigner(SIGNER_KEY.getBytes()));
            return jwsObject.serialize();
        } catch (JOSEException e) {
            throw new RuntimeException(e);
        }
        // Tạo đối tượng JWSObject từ header và payload. Sử dụng khóa bí mật (SIGNER_KEY) để ký token.
        // Trả về chuỗi JWT đã ký nếu ký thành công, nếu không, ném ra ngoại lệ RuntimeException.
    }

    private String generateToken(User user) {
        JWSHeader header = new JWSHeader(JWSAlgorithm.HS512);

        JWTClaimsSet jwtClaimsSet = new JWTClaimsSet.Builder()
                .subject(user.getEmail())
                .issuer("devteria.com")
                .issueTime(new Date())
                .expirationTime(new Date(
                        Instant.now().plus(1, ChronoUnit.HOURS).toEpochMilli()
                ))
                .claim( "scope", buildScope(user))
                .build();

        Payload payload = new Payload(jwtClaimsSet.toJSONObject());

        JWSObject jwsObject = new JWSObject(header, payload);

        try {
            jwsObject.sign(new MACSigner(SIGNER_KEY.getBytes()));
            return jwsObject.serialize();
        } catch (JOSEException e) {
            log.error("Cannot create token", e);
            throw new RuntimeException(e);
        }
    }

    private String buildScope(User user){
        StringJoiner stringJoiner = new StringJoiner(" ");
        //if (!CollectionUtils.isEmpty(user.getRoles()))
            //user.getRoles().forEach(stringJoiner::add);

        return stringJoiner.toString();
    }


}

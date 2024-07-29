package com.example.webbanhang.configuration;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtException;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.stereotype.Component;

import com.example.webbanhang.service.AuthenticationService;

@Component
public class CustomJwtDecoder implements JwtDecoder {

    @Value("${jwt.signerKey}")
    private String SIGNER_KEY;

    @Autowired
    private AuthenticationService authenticationService;

    private NimbusJwtDecoder nimbusJwtDecoder = null;

    //    private final String signerKey = "Vabsn1UFPMGt9i0mKDaqRU2NdNwEw+VIZg8/Qa+37L521P1gqO4bRBEJyem2R7Zy";
    private final NimbusJwtDecoder jwtDecoder;

    public CustomJwtDecoder() {
        String signerKeyString = "Vabsn1UFPMGt9i0mKDaqRU2NdNwEw+VIZg8/Qa+37L521P1gqO4bRBEJyem2R7Zy";
        byte[] keyBytes = signerKeyString.getBytes(); // Convert String to byte array
        SecretKey secretKey = new SecretKeySpec(keyBytes, 0, keyBytes.length, "HmacSHA256");
        this.jwtDecoder = NimbusJwtDecoder.withSecretKey(secretKey).build();
    }

    @Override
    public Jwt decode(String token) throws JwtException {
        try {
            return jwtDecoder.decode(token);
        } catch (Exception e) {
            throw new JwtException("Token invalid", e);
        }
    }
}

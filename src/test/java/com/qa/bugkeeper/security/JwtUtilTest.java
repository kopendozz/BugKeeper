package com.qa.bugkeeper.security;

import com.qa.bugkeeper.config.JwtProperties;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Base64;
import java.util.Date;

import static org.assertj.core.api.Assertions.assertThat;

class JwtUtilTest {

    public static final String SECRET = "0123456789ABCDEF0123456789ABCDEF";
    private static final String BASE64_SECRET = Base64.getEncoder()
            .encodeToString(SECRET.getBytes());
    JwtUtil jwtUtil;

    @BeforeEach
    void setup() {
        var props = new JwtProperties();
        // Use a known secret – 256-bit base64 encoded
        props.setSecret(BASE64_SECRET);
        props.setExpirationMs(1000 * 60); // 1 minute
        jwtUtil = new JwtUtil(props);
    }

    @Test
    void shouldGenerateAndValidateToken() {
        // given
        var username = "testuser";

        // when
        var token = jwtUtil.generateToken(username);

        // then
        assertThat(token).isNotBlank();
        assertThat(jwtUtil.isTokenValid(token)).isTrue();
        assertThat(jwtUtil.extractUsername(token)).isEqualTo(username);
    }

    @Test
    void shouldRejectMalformedToken() {
        assertThat(jwtUtil.isTokenValid("not.a.jwt")).isFalse();
    }

    @Test
    void shouldRejectExpiredTokenWithoutSleep() {
        // given
        var username = "testuser";
        var expiredDate = new Date(System.currentTimeMillis() - 1000); // 1s in the past

        //when
        var expiredToken = jwtUtil.generateTokenWithExpiration(username, expiredDate);

        //then
        assertThat(jwtUtil.isTokenValid(expiredToken)).isFalse();
    }
}

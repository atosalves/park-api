package com.atosalves.park_api.jwt;

import java.time.ZoneId;
import java.util.Date;

import javax.crypto.SecretKey;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@NoArgsConstructor
public class JwtUtils {

        public static final String JWT_BEARER = "Bearer ";
        public static final String JWT_AUTHORIZATION = "Authorization";
        public static final String SECRET_KEY = "Y4b8A9yRZCpJ9AytN3G5qdrZ4PYiyRhpD3h34cp0iCY="; // mudar depois
        public static final Long EXPIRE_DAYS = 0L;
        public static final Long EXPIRE_HOURS = 0L;
        public static final Long EXPIRE_MINUTES = 2L;

        public static JwtToken createToken(String username, String role) {
                var issuedAt = new Date();
                var limit = toExpireDate(issuedAt);

                String token = Jwts.builder()
                                .header()
                                .type("JWT")
                                .and()
                                .subject(username)
                                .issuedAt(issuedAt)
                                .expiration(limit)
                                .claim("role", role)
                                .signWith(generateKey())
                                .compact();

                return new JwtToken(token);
        }

        public static String getUsernameFromToken(String token) {
                return getClaims(token).getSubject();
        }

        public static boolean isTokenValid(String token) {
                try {
                        getClaims(token);
                        return true;
                } catch (JwtException e) {
                        log.error(String.format("Token inválido '%s'", e.getMessage()));
                }
                return false;
        }

        private static Claims getClaims(String token) {
                Claims payload = Jwts
                                .parser()
                                .verifyWith(generateKey())
                                .build()
                                .parseSignedClaims(refactorToken(token))
                                .getPayload();
                return payload;
        }

        private static SecretKey generateKey() {
                var keyBytes = Decoders.BASE64.decode(SECRET_KEY);
                return Keys.hmacShaKeyFor(keyBytes);
        }

        private static String refactorToken(String token) {
                if (token.contains(JWT_BEARER)) {
                        return token.substring(JWT_BEARER.length());
                }
                return token;
        }

        private static Date toExpireDate(Date start) {
                var dateTime = start.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
                var end = dateTime.plusDays(EXPIRE_DAYS).plusHours(EXPIRE_HOURS).plusMinutes(EXPIRE_MINUTES);

                return Date.from(end.atZone(ZoneId.systemDefault()).toInstant());
        }
}

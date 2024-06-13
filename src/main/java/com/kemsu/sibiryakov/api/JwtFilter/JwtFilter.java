package com.kemsu.sibiryakov.api.JwtFilter;

import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.InvalidKeyException;
import io.jsonwebtoken.security.Keys;

import javax.crypto.SecretKey;

public class JwtFilter {
    private static final String JWT_KEY =
            "CGuZtKUBu3JIvXOCwWIyJYSS4cP+TNiDIDdvhr6aqnpQ45y3nw0qC9WY4cJPiHXcKKKlILZhpJI8hX5MTRn9QQ==";

    public static Claims getBody(String token) {
        try {
            SecretKey secretKey = Keys.hmacShaKeyFor(
                    Decoders.BASE64.decode(JWT_KEY)
            );

            JwtParser parser = Jwts.parserBuilder()
                    .setSigningKey(secretKey)
                    .build();

            Jws<Claims> claimsJwt = parser.parseClaimsJws(token);

            return claimsJwt.getBody();
        } catch (Exception e) {
            throw new InvalidKeyException("Invalid JWT token");
        }
    }
}

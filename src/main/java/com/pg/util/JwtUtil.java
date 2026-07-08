package com.pg.util;

import java.nio.charset.StandardCharsets;
import java.util.Date;

import javax.crypto.SecretKey;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtUtil {

	private static final String SECRET_KEY = "SecretKeyMustBeVeryLongToSatisfyThe256BitRequirement!!!";

	private static final long ACCESS_TOKEN_EXPIRATION_TIME = 15 * 60 * 1000; // 15 minutes
	private static final long REFRESH_TOKEN_EXPIRATION_TIME = 7 * 24 * 60 * 60 * 1000; // 7 days

	private static final String TOKEN_TYPE_CLAIM = "type";
	private static final String ACCESS_TOKEN_TYPE = "access";
	private static final String REFRESH_TOKEN_TYPE = "refresh";

	private SecretKey getSigningKey() {
		return Keys.hmacShaKeyFor(SECRET_KEY.getBytes(StandardCharsets.UTF_8));
	}

	/**
	 * Backward compatibility.
	 * Generates an access token.
	 */
	public String generateToken(String email) {
		return generateAccessToken(email);
	}

	public String generateAccessToken(String userId) {
		return Jwts.builder()
				.subject(
						userId)
				.issuedAt(new Date())
				.claim(TOKEN_TYPE_CLAIM, ACCESS_TOKEN_TYPE)
				.expiration(new Date(System.currentTimeMillis() + ACCESS_TOKEN_EXPIRATION_TIME))
				.signWith(getSigningKey())
				.compact();
	}

	public String generateRefreshToken(String userId) {
		return Jwts.builder()
				.subject(
						userId)
				.issuedAt(new Date())
				.claim(TOKEN_TYPE_CLAIM, REFRESH_TOKEN_TYPE)
				.expiration(new Date(System.currentTimeMillis() + REFRESH_TOKEN_EXPIRATION_TIME))
				.signWith(getSigningKey())
				.compact();
	}

	public String extractUsername(String token) {
		return extractClaims(token).getSubject();
	}

	public boolean validateToken(String token, UserDetails userDetails) {
		return validateAccessToken(token, userDetails);
	}

	public boolean validateAccessToken(String token, UserDetails userDetails) {
		return extractUsername(token).equals(userDetails.getUsername())
				&& isAccessToken(token)
				&& !isTokenExpired(token);
	}

	public boolean validateRefreshToken(String token, String email) {
		return extractUsername(token).equals(email)
				&& isRefreshToken(token)
				&& !isTokenExpired(token);
	}

	public boolean isAccessToken(String token) {
		return ACCESS_TOKEN_TYPE.equals(
				extractClaims(token).get(TOKEN_TYPE_CLAIM, String.class));
	}

	public boolean isRefreshToken(String token) {
		return REFRESH_TOKEN_TYPE.equals(
				extractClaims(token).get(TOKEN_TYPE_CLAIM, String.class));
	}

	private Claims extractClaims(String token) {
		return Jwts.parser()
				.verifyWith(getSigningKey())
				.build()
				.parseSignedClaims(token)
				.getPayload();
	}

	private boolean isTokenExpired(String token) {
		return extractClaims(token)
				.getExpiration()
				.before(new Date());
	}
}
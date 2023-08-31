package com.prakat.projectx.serviceImpl;

import com.prakat.projectx.entity.User;
import com.prakat.projectx.service.JwtService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
/**
 * JwtServiceImpl is a service class that provides functionality for generating and parsing JSON Web Tokens (JWTs)
 * using the EC (Elliptic Curve) algorithm with ES256 signature. It also includes methods to extract claims
 * from JWTs, generate tokens based on provided UserDetails, and validate tokens against UserDetails.
 */
@Service
public class JwtServiceImpl implements JwtService {
    private static final Logger logger = LoggerFactory.getLogger(JwtServiceImpl.class);
    private static final KeyPair keyPair = generateKeyPair();
    @Autowired
    UserDetailsService userDetailsService;

    /**
     * Generates an EC KeyPair for use in signing and parsing JWTs with the ES256 algorithm.
     *
     * @return The generated KeyPair
     * @throws RuntimeException if an error occurs while generating the KeyPair
     */
    private static KeyPair generateKeyPair() {
        try {
            KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("EC");
            return keyPairGenerator.generateKeyPair();
        } catch (NoSuchAlgorithmException e) {
            logger.error("Error generating key pair for ES256 algorithm", e);
            throw new RuntimeException("Error generating key pair for ES256 algorithm", e);
        }
    }

    /**
     * Extracts the username (subject) claim from the provided JWT token.
     *
     * @param jwtAccessToken The JWT token from which to extract the username claim
     * @return The username claim value
     */
    @Override
    public String extractUsername(String jwtAccessToken) {
        return extractClaim(jwtAccessToken, Claims::getSubject);
    }

    /**
     * Extracts a claim from the provided JWT token using the specified claims resolver function.
     *
     * @param jwtAccessToken         The JWT token from which to extract the claim
     * @param claimsResolver The claims resolver function to apply to the token's claims
     * @param <T>           The type of the claim to be extracted
     * @return The extracted claim value
     */
    @Override
    public <T> T extractClaim(String jwtAccessToken, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(jwtAccessToken);
        return claimsResolver.apply(claims);
    }

    /**
     * Generates a new JWT token for the provided UserDetails.
     *
     * @param userEmail The UserDetails object representing the user for whom the token is generated
     * @return The generated JWT token
     */
    @Override
    public String generateToken(String userEmail, String secretCode) {
        UserDetails userDetails = userDetailsService.loadUserByUsername(userEmail);
        Map<String, Object> claims = new HashMap<>();
        claims.put("secretCode", secretCode);
        return generateToken(claims, userDetails);
    }

    /**
     * Generates a new JWT token with additional custom claims for the provided UserDetails.
     * @param extractClaims Additional claims to include in the JWT
     * @param userDetails   The UserDetails object representing the user for whom the token is generated
     * @return The generated JWT token
     */
    @Override
    public String generateToken(Map<String, Object> extractClaims, UserDetails userDetails) {

    String secretCode = "";
    if(userDetails instanceof User) {
        User user = (User) userDetails;
        secretCode = user.getSecretCode();
    }
    if (secretCode!= null && !secretCode.isEmpty()){
        extractClaims.put("secretCode", secretCode);
    }
        return Jwts.builder()
                .setClaims(extractClaims)
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 24))
                .signWith(keyPair.getPrivate(), SignatureAlgorithm.ES256)
                .compact();
    }

    /**
     * Generates a new refresh JWT token for the provided UserDetails.
     *
     * @param userDetails The UserDetails object representing the user for whom the token is generated
     * @return The generated refresh JWT token
     */
    @Override
    public String generateRefreshToken(UserDetails userDetails) {
        String secretCode = "";
        if (userDetails instanceof User) {
            User user = (User) userDetails;
            secretCode = user.getSecretCode();
        }

        Map<String, Object> claims = new HashMap<>();
        claims.put("secretCode", secretCode);

        return Jwts.builder()
                .setClaims(claims)
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 24 * 7)) // 7 days validity for refresh token
                .signWith(keyPair.getPrivate(), SignatureAlgorithm.ES256)
                .compact();
    }
    /**
     * Extracts the secretCode claim from the provided JWT token's payload.
     *
     * @param jwtAccessToken The JWT token from which to extract the secretCode claim
     * @return The secretCode claim value
     */
    @Override
    public String extractSecretCode(String jwtAccessToken) {
        return extractClaim(jwtAccessToken, claims -> claims.get("secretCode", String.class));
    }
    /**
     * Validates the provided JWT token against the given UserDetails object.
     * @param jwtToken       The JWT token to be validated
     * @param userDetails The UserDetails object representing the user against whom the token should be validated
     * @return true if the token is valid for the given user, false otherwise
     */
    @Override
    public Boolean isTokenValid(String jwtToken, UserDetails userDetails) {
        final String username = extractUsername(jwtToken);
        if (username == null || isTokenExpired(jwtToken)) {
            return false;
        }
        final String secretCode = extractSecretCode(jwtToken);
        return username.equals(userDetails.getUsername()) && isSecretCodeValid(secretCode, userDetails);
    }
    @Override
    public boolean isSecretCodeValid(String secretCode, UserDetails userDetails) {
        if (userDetails instanceof User) {
            User user = (User) userDetails;
            return secretCode.equals(user.getSecretCode());
        }
        return false;
    }

    /**
     * Validates the provided refresh JWT token against the given UserDetails object.
     *
     * @param refreshToken The refresh JWT token to be validated
     * @param userDetails  The UserDetails object representing the user against whom the token should be validated
     * @return true if the refresh token is valid for the given user, false otherwise
     */
    @Override
    public Boolean isRefreshTokenValid(String refreshToken, UserDetails userDetails) {
        final String username = extractUsername(refreshToken);
        return (username.equals(userDetails.getUsername())) && !isTokenExpired(refreshToken);
    }

    /**
     * Checks if the provided JWT token has expired.
     *
     * @param jwtAccessToken The JWT token to be checked for expiration
     * @return true if the token has expired, false otherwise
     */
    @Override
    public boolean isTokenExpired(String jwtAccessToken) {
        return extractExpiration(jwtAccessToken).before(new Date());
    }

    /**
     * Extracts the expiration date claim from the provided JWT token.
     *
     * @param jwtAccessToken The JWT token from which to extract the expiration claim
     * @return The expiration date of the token
     */
    @Override
    public Date extractExpiration(String jwtAccessToken) {
        return extractClaim(jwtAccessToken, Claims::getExpiration);
    }

    /**
     * Parses the provided JWT token and extracts all claims from it.
     *
     * @param jwtAccessToken The JWT token to be parsed
     * @return The Claims object containing all the extracted claims
     */
    @Override
    public Claims extractAllClaims(String jwtAccessToken) {
        return Jwts
                .parserBuilder()
                .setSigningKey(keyPair.getPublic())
                .build()
                .parseClaimsJws(jwtAccessToken)
                .getBody();
    }
    @Override
    public UserDetails getUserDetailsFromRefreshToken(String refreshToken) {
            Claims claims = extractAllClaims(refreshToken);
            String username = claims.getSubject();
            UserDetails userDetails = userDetailsService.loadUserByUsername(username);
            return userDetails;
    }
}

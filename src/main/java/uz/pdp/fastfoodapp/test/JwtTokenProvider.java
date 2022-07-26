package uz.pdp.fastfoodapp.test;

import io.jsonwebtoken.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import uz.pdp.fastfoodapp.entity.user.User;

import java.util.Date;

@Component

public class JwtTokenProvider {


    private static final Logger logger = LoggerFactory.getLogger(JwtTokenProvider.class);


    @Value("${app.jwtSecret}")
    private String jwtSecret;


    @Value("${app.accessJwtExpirationInMs}")
    private int accessJwtExpirationInMs;


    @Value("${app.refreshJwtExpirationInMs}")
    private int refreshJwtExpirationInMs;


    public String generateToken(Authentication authentication, boolean isAccessToken) {

        User userPrincipal = (User) authentication.getPrincipal();


        Date expiryDate;

        if (isAccessToken) {

            expiryDate = new Date(new Date().getTime() + accessJwtExpirationInMs);

        } else {

            expiryDate = new Date(new Date().getTime() + refreshJwtExpirationInMs);

        }


        return Jwts.builder()

                .setSubject(userPrincipal.getId().toString())

                .setIssuedAt(new Date())

                .setExpiration(expiryDate)

                .signWith(SignatureAlgorithm.HS512, jwtSecret)

                .compact();

    }


    public String getUserIdFromJWT(String token) {

        Claims claims = Jwts.parser()

                .setSigningKey(jwtSecret)

                .parseClaimsJws(token)

                .getBody();


        return claims.getSubject();

    }


    public boolean validateToken(String authToken) {

        try {

            Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(authToken);

            return true;

        } catch (SignatureException ex) {

            logger.error("Invalid JWT signature");

        } catch (MalformedJwtException ex) {

            logger.error("Invalid JWT token");

        } catch (ExpiredJwtException ex) {

            logger.error("Expired JWT token");

        } catch (UnsupportedJwtException ex) {

            logger.error("Unsupported JWT token");

        } catch (IllegalArgumentException ex) {

            logger.error("JWT claims string is empty.");

        }

        return false;

    }


}

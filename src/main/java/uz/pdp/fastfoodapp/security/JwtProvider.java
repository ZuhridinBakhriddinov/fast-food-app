package uz.pdp.fastfoodapp.security;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import uz.pdp.fastfoodapp.entity.user.permission.Permission;
import uz.pdp.fastfoodapp.entity.user.role.Roles;

import java.util.Date;
import java.util.Map;
import java.util.Set;

@Component
@RequiredArgsConstructor
public class JwtProvider {

    static final long expirationTime = 1000 * 60 * 60 *24;
    static final String key = "secretKeyFoodfjasklfjdkafjdklfjklsfjaslfiaslfjaslfjdaslfjdslafjaslfdaslfkasfjdasjsklfjdkljslfjsdlkfjsdalfjdasfdafklsjfklajasiofjail";

    public String generateToken(String username, Set<Roles> roles, Set<Permission> permissions) {
        /**
         *  look at expiration date is 10 days
         */
        Date expireDate = new Date(System.currentTimeMillis() + expirationTime);
        String token = Jwts
                .builder()
                .setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(expireDate)
                .addClaims(Map.of("roles", roles, "permissions", permissions))
                .signWith(SignatureAlgorithm.HS512, key)
                .compact();
        return token;
    }
    public String generatedToken(String phoneNumber, boolean forAccess) {
        Date expire = new Date(System.currentTimeMillis() + (forAccess ? expirationTime : expirationTime*7));
        return Jwts
                .builder()
                .signWith(Keys.hmacShaKeyFor(Decoders.BASE64.decode(key)))
                .setSubject(phoneNumber)
                .setIssuedAt(new Date())
                .setExpiration(expire)
                .compact();
    }

    public String getUsernameFromToken(String token) {
        try {
            String username = Jwts
                    .parser()
                    .setSigningKey(key)
                    .parseClaimsJws(token)
                    .getBody()
                    .getSubject();
            return username;
        } catch (Exception e) {
            return null;
        }
    }

    public String generateRefreshToken(String username, Set<Roles> roles, Set<Permission> permissions) {
        /**
         *  look at expiration date is 30 days
         */
        Date expireDate = new Date(System.currentTimeMillis() + expirationTime * 7);
        String token = Jwts
                .builder()
                .setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(expireDate)
                .addClaims(Map.of("roles", roles, "permissions", permissions))
                .signWith(SignatureAlgorithm.HS512, key)
                .compact();
        return token;
    }
}
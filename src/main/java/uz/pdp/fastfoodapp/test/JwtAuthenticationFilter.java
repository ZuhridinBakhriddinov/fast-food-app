/*
package uz.pdp.fastfoodapp.test;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.UUID;

public class JwtAuthenticationFilter extends OncePerRequestFilter {


    @Value("${authentication.auth.accessTokenCookieName}")

    private String accessTokenCookieName;

    @Value("${authentication.auth.refreshTokenCookieName}")

    private String refreshTokenCookieName;

    @Value("${authentication.auth.randomSecretKeyCookieName}")

    private String randomKeyName;


    @Value("${app.security.api-key}")

    private String securityApiKey;


    @Autowired

    AuthServiceCookie authServiceCookie;


    @Autowired

    private JwtTokenProvider tokenProvider;


    private static final Logger logger = LoggerFactory.getLogger(JwtAuthenticationFilter.class);


    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain) throws ServletException, IOException {
        try {
            // to check header, it is only for server to server
            String apiKey = httpServletRequest.getHeader("Authorization");

            if (apiKey != null && apiKey.equals(securityApiKey)) {

                UserDetails server = authServiceCookie.loadUserByUsername("server");

                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(server, null, server.getAuthorities());

                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(httpServletRequest));

                SecurityContextHolder.getContext().setAuthentication(authentication);

            } else {


                ResponseToken responseToken = getJwtToken(httpServletRequest, true);


                boolean accessTokenHasText = StringUtils.hasText(responseToken.getAccessToken());

                boolean accessTokenIsValid = tokenProvider.validateToken(responseToken.getAccessToken());

                boolean refreshTokenHasText = StringUtils.hasText(responseToken.getRefreshToken());

                boolean refreshTokenIsValid = tokenProvider.validateToken(responseToken.getRefreshToken());


                if ((refreshTokenHasText && refreshTokenIsValid) || (accessTokenHasText && accessTokenIsValid)) {

                    String userId;

                    if (refreshTokenHasText && refreshTokenIsValid) {

                        userId = tokenProvider.getUserIdFromJWT(responseToken.getRefreshToken());

                    } else {

                        userId = tokenProvider.getUserIdFromJWT(responseToken.getAccessToken());

                    }

                    UserDetails userDetails = authServiceCookie.loadUserById(UUID.fromString(userId));

                    UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());

                    authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(httpServletRequest));

                    SecurityContextHolder.getContext().setAuthentication(authentication);

                    if (!accessTokenHasText || !accessTokenIsValid) {

                        String accessToken = tokenProvider.generateToken(authentication, true);

                        authServiceCookie.setCookie(httpServletResponse, "Bearer_" + accessToken, accessTokenCookieName, 60 * 60 * 24);

//                    authService.setCookie(httpServletResponse, "Bearer_"+accessToken,accessTokenCookieName,60*15);

                        String refreshToken = tokenProvider.generateToken(authentication, false);

                        authServiceCookie.setCookie(httpServletResponse, "Bearer_" + refreshToken, refreshTokenCookieName, 60 * 60 * 24 * 7);

//                    authService.setCookie(httpServletResponse,"Bearer_"+refreshToken, refreshTokenCookieName, 60*30);

                    }

                    if (!refreshTokenHasText || !refreshTokenIsValid) {

                        String refreshToken = tokenProvider.generateToken(authentication, false);
                        authServiceCookie.setCookie(httpServletResponse, "Bearer_" + refreshToken, refreshTokenCookieName, 60 * 60 * 24 * 7);

//                    authService.setCookie(httpServletResponse,"Bearer_"+refreshToken, refreshTokenCookieName, 60*30);

                    }

                }

            }

        } catch (Exception ex) {

            logger.error("Could not set user authentication in security context", ex);

        }


        filterChain.doFilter(httpServletRequest, httpServletResponse);

    }


    private ResponseToken getJwtToken(HttpServletRequest request, boolean fromCookie) {

        if (fromCookie) return getJwtFromCookie(request);

//        return getJwtFromRequest(request);

        return null;

    }


    private ResponseToken getJwtFromCookie(HttpServletRequest request) {

        if (request.getCookies() != null) {

            ResponseToken responseToken = new ResponseToken();

            for (Cookie cookie : request.getCookies()) {

                if (cookie.getName().equals(refreshTokenCookieName)) {

                    String refreshToken = cookie.getValue();

                    if (StringUtils.hasText(refreshToken) && refreshToken.startsWith("Bearer_")) {

                        responseToken.setRefreshToken(refreshToken.substring(7));

                    }

                }

                if (cookie.getName().equals(accessTokenCookieName)) {

                    String accessToken = cookie.getValue();

                    if (StringUtils.hasText(accessToken) && accessToken.startsWith("Bearer_")) {

                        responseToken.setAccessToken(accessToken.substring(7));

                    }

                }

            }

            return responseToken;

        }

        return null;

    }


    private String getJwtFromRequest(HttpServletRequest request) {

        String bearerToken = request.getHeader("Authorization");

        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {

            return bearerToken.substring(7);

        }

        return null;

    }

}*/

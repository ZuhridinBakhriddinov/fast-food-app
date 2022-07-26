package uz.pdp.fastfoodapp.test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import uz.pdp.fastfoodapp.entity.authService.LoginDto;
import uz.pdp.fastfoodapp.entity.user.User;
import uz.pdp.fastfoodapp.entity.user.UserRepository;
import uz.pdp.fastfoodapp.security.JwtProvider;
import uz.pdp.fastfoodapp.template.ApiResponse;

import javax.servlet.http.HttpServletResponse;
import java.util.UUID;

@Service

public class AuthServiceCookie implements UserDetailsService {


    @Value("${authentication.auth.accessTokenCookieName}")
    private String accessTokenCookieName;

    @Value("${authentication.auth.refreshTokenCookieName}")
    private String refreshTokenCookieName;

    @Value("${authentication.auth.randomSecretKeyCookieName}")
    private String randomSecretKeyName;

    @Value("${app.security.api-key}")
    private String securityApiKey;


    private final UserRepository userRepository;

    private final JwtProvider jwtTokenProvider;

    private final AuthenticationManager authenticationManager;


    @Autowired

    public AuthServiceCookie(UserRepository userRepository, JwtProvider jwtTokenProvider, AuthenticationManager authenticationManager) {

        this.userRepository = userRepository;

        this.jwtTokenProvider = jwtTokenProvider;

        this.authenticationManager = authenticationManager;

    }


    @Override

    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        return userRepository.findByPhoneNumber(username).orElseThrow(() -> new UsernameNotFoundException(username));

    }


    public UserDetails loadUserById(UUID userId) throws UsernameNotFoundException {

        return userRepository.findById(userId).orElseThrow(() -> new UsernameNotFoundException("User id not found: " + userId));

    }


    public ResponseEntity<?> getJwt(LoginDto reqSignIn, HttpServletResponse response) {

        try {

            ResponseToken responseToken = generateToken(reqSignIn.getPhoneNumber(), reqSignIn.getSmsCode());

            if (responseToken.getAccessToken() != null && responseToken.getRefreshToken() != null) {

                setCookie(response, "Bearer_" + responseToken.getAccessToken(), accessTokenCookieName, 60 * 60 * 24);

                setCookie(response, "Bearer_" + responseToken.getRefreshToken(), refreshTokenCookieName, 60 * 60 * 24 * 7);
                User user = responseToken.getUser();

//                String random = RandomStringUtils.random(6, true, true);

//                setCookie(response,random, randomSecretKeyName, 60*60*24);

//                user.setJwt(random);

//                userRepository.save(user);

                User userByUsername = userRepository.findByPhoneNumber(user.getUsername()).get();

                return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse("Success", true,userByUsername));

            }

            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(new ApiResponse("Token yaratilmadi", false));

        } catch (BadCredentialsException e) {

            e.printStackTrace();

            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new ApiResponse("Login yoki parol xato!", false));

        } catch (DisabledException e) {

            e.printStackTrace();

            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new ApiResponse("Siz ish faoliyatida emassiz, ma'muriyat bilan bog'laning!", false));

        } catch (Exception e) {

            e.printStackTrace();

            return ResponseEntity.status(HttpStatus.CONFLICT).body(new ApiResponse("Conflict", false));

        }

    }


    public ResponseToken generateToken(String phoneNumber, int smsCode) {

        /*Authentication authentication = authenticationManager.authenticate(

                new UsernamePasswordAuthenticationToken(phoneNumber, smsCode)

        );

        SecurityContextHolder.getContext().setAuthentication(authentication);*/
        User user = userRepository.findByPhoneNumber(phoneNumber).orElseThrow(() -> new IllegalStateException("User not found"));

     //   User user = (User) authentication.getPrincipal();

        String accessToken = jwtTokenProvider.generatedToken(user.getPhoneNumber(), true);

        String refreshToken = jwtTokenProvider.generatedToken(user.getPhoneNumber(), false);

        return new ResponseToken(accessToken, refreshToken, user);

    }


    public ResponseEntity<?> logout(HttpServletResponse response) {

        setCookie(response, "", accessTokenCookieName, 0);

        setCookie(response, "", refreshTokenCookieName, 0);

        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);

    }

    public void setCookie(HttpServletResponse response, String value, String cookieName, long maxAgeSeconds) {


        ResponseCookie cookie = ResponseCookie.from(cookieName, value)

                .path("/")

                .maxAge(maxAgeSeconds)

                .httpOnly(true)

                .secure(false)

                .sameSite("Strict")

                .build();


//        ResponseCookie cookie = ResponseCookie.from(cookieName, value)
//
//                .path("/api")
//
//                .maxAge(maxAgeSeconds)
//
//                .httpOnly(true)
//
//                .secure(false)
//
//                .sameSite("none")
//
//
//                .build();



        response.addHeader(HttpHeaders.SET_COOKIE, cookie.toString());

    }

}

package uz.pdp.fastfoodapp.test;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.web.bind.annotation.*;
import uz.pdp.fastfoodapp.entity.authService.LoginDto;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@CrossOrigin(origins = "https://fast-food-client.herokuapp.com")
public class AuthControllerCookie {


    private final AuthServiceCookie authServiceCookie;

    @PostMapping(value = "/login")
    public HttpEntity<?> login(@Valid @RequestBody LoginDto reqSignIn, HttpServletResponse response) {
        return authServiceCookie.getJwt(reqSignIn, response);

    }


    @GetMapping("/logout")
    public HttpEntity<?> logout(HttpServletResponse response) {
        return authServiceCookie.logout(response);
    }


}

package uz.pdp.fastfoodapp.entity.authService;


import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import uz.pdp.fastfoodapp.entity.authService.LoginDto;

import javax.validation.Valid;

@RestController
@RequestMapping("${app.domain}/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;


    @PostMapping("/register")
    public HttpEntity<?> registerUser(@Valid @RequestBody RegisterDto registerDto) {
        return authService.registerUser(registerDto);
    }


    @PostMapping("/login")
    public HttpEntity<?> loginUser(@Valid @RequestBody LoginDto loginDto) {
        return authService.loginUser(loginDto);
    }
}

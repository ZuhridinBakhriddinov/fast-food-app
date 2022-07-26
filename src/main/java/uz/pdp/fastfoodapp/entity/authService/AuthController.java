package uz.pdp.fastfoodapp.entity.authService;


import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import uz.pdp.fastfoodapp.entity.user.User;
import uz.pdp.fastfoodapp.entity.user.UserRepository;
import uz.pdp.fastfoodapp.template.ApiResponse;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;


@RestController
@RequestMapping("${app.domain}/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;
    private final UserRepository userRepository;

    @PostMapping("/register")
    public HttpEntity<?> registerUser(@Valid @RequestBody RegisterDto registerDto) {
        return authService.registerUser(registerDto);
    }


    @PostMapping("/login")
    public HttpEntity<?> loginUser(@Valid @RequestBody LoginDto loginDto) {
        return authService.loginUser(loginDto);

    }

    @PostMapping("/refresh/token")
    public HttpEntity<?> refreshTheAccessToken(HttpServletRequest request, HttpServletResponse response) {
        return authService.refreshAccessToken(request, response);
    }

    @GetMapping("/me")
    public HttpEntity<?> getUser(@AuthenticationPrincipal User user) {


       // User user = userService.findByUsername(username);
     //   String login = principal.getName();
        System.out.println(user.getId());
        // User user = usersService.getUserByLogin(login);
        //Optional<User> optionalUser = userRepository.findByPhoneNumber(userDetails.getUsername());
        try {
           return  ResponseEntity.status(200).body(new ApiResponse("success", true, user));
        }catch (Exception e){

            return  ResponseEntity.status(400).body(new ApiResponse("wrong", false, false));
        }
    }
}

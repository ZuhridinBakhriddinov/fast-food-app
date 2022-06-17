
package uz.pdp.fastfoodapp.entity.authService;


import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import uz.pdp.fastfoodapp.entity.user.User;
import uz.pdp.fastfoodapp.entity.user.UserRepository;
import uz.pdp.fastfoodapp.entity.user.permission.Permission;
import uz.pdp.fastfoodapp.entity.user.role.Role;
import uz.pdp.fastfoodapp.entity.user.role.Roles;
import uz.pdp.fastfoodapp.entity.user.role.RolesRepository;
import uz.pdp.fastfoodapp.entity.user.verificationCodes.VerificationService;
import uz.pdp.fastfoodapp.security.JwtProvider;
import uz.pdp.fastfoodapp.template.ApiResponse;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.*;

import static org.springframework.security.config.web.server.SecurityWebFiltersOrder.ANONYMOUS_AUTHENTICATION;

@Service
@RequiredArgsConstructor
public class AuthService implements UserDetailsService {
    private final AuthenticationManager authenticationManager;
    private final VerificationService verificationService;
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final JwtProvider jwtProvider;

    private final RolesRepository rolesRepository;

    public HttpEntity<?> registerUser(RegisterDto registerDto) {
        ApiResponse apiResponse = verificationService.validateSmsCode(registerDto.getPhoneNumber(), registerDto.getSmsCode());
        if (!apiResponse.isSuccess())
            return ResponseEntity.status(404).body(apiResponse);

        if (userRepository.existsByPhoneNumber(registerDto.getPhoneNumber()))
            return ResponseEntity.status(409).body(new ApiResponse("This user registered before", false));
        Set<Roles> roleUser = new HashSet<>();
        Roles userRole = new Roles(Role.ROLE_CUSTOMER);
        Roles save = rolesRepository.save(userRole);
        roleUser.add(save);
        Set<Permission> permissions = new HashSet<>();
        User user = new User(
                registerDto.getFullName(),
                registerDto.getPhoneNumber(),
            //    passwordEncoder.encode(String.valueOf(registerDto.getSmsCode())),
                roleUser,
                permissions
        );
        User savedUser = userRepository.save(user);
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                savedUser.getPhoneNumber(), registerDto.getSmsCode());
        authenticationManager.authenticate(authenticationToken);
        String access_token = jwtProvider.generateToken(user.getPhoneNumber(), user.getRoles(), user.getPermissions());
        String refresh_token = jwtProvider.generateRefreshToken(user.getPhoneNumber(), user.getRoles(), user.getPermissions());
        Map<String, Object> registerMap = new HashMap<>();
        registerMap.put("access_token", access_token);
        registerMap.put("refresh_token", refresh_token);
        registerMap.put("user_id", savedUser.getId().toString());
        return ResponseEntity.status(200).body(new ApiResponse("Registered Successfully", true, registerMap));
    }

    @Override
    public UserDetails loadUserByUsername(String phoneNumber) {
        return userRepository.findByPhoneNumber(phoneNumber).orElseThrow(() -> new UsernameNotFoundException(phoneNumber));
    }

    public HttpEntity<?> loginUser(@Valid LoginDto loginDto) {
        ApiResponse apiResponse = verificationService.validateSmsCode(loginDto.getPhoneNumber(), loginDto.getCodeSent());
        if (!apiResponse.isSuccess())
            return ResponseEntity.status(404).body(apiResponse);

//        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
//                loginDto.getPhoneNumber(), loginDto.getCodeSent());
//        Authentication authenticate = authenticationManager.authenticate(authenticationToken);
//        User user = (User) authenticate.getPrincipal();
        User user = userRepository.findByPhoneNumber(loginDto.getPhoneNumber()).orElseThrow(() -> new IllegalStateException("User not found"));

        String access_token = jwtProvider.generateToken(user.getPhoneNumber(), user.getRoles(), user.getPermissions());
        String refresh_token = jwtProvider.generateRefreshToken(user.getPhoneNumber(), user.getRoles(), user.getPermissions());
        Map<String, Object> loginMap = new HashMap<>();
        loginMap.put("access_token", access_token);
        loginMap.put("refresh_token", refresh_token);
        loginMap.put("user_id", user.getId().toString());
        return ResponseEntity.status(200).body(new ApiResponse("Successful signed in.", true, loginMap));
    }

    public HttpEntity<?> refreshAccessToken(HttpServletRequest request, HttpServletResponse response) {
        String authentication = request.getHeader("Authorization");
        if (authentication != null && authentication.startsWith("Bearer") && authentication.length() > 7) {
            authentication = authentication.substring(7);
            String number = jwtProvider.getUsernameFromToken(authentication);
            Optional<User> phoneNumber = userRepository.findByPhoneNumber(number);
            if (!phoneNumber.isPresent())
                return ResponseEntity.status(200).body(new ApiResponse("This user did not registered yet", false));
            ;
            User user = phoneNumber.get();
            String access_token = jwtProvider.generateToken(number, user.getRoles(), user.getPermissions());
            Map<String, Object> loginMap = new HashMap<>();
            loginMap.put("access_token", access_token);
            loginMap.put("user_id", user.getId().toString());
            return ResponseEntity.status(200).body(new ApiResponse("Successful signed in.", true, loginMap));
        }
        return ResponseEntity.status(200).body(new ApiResponse("Something is wrong in client side ", false));
    }
}

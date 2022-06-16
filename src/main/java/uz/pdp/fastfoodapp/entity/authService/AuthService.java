
package uz.pdp.fastfoodapp.entity.authService;


import com.twilio.rest.preview.deployedDevices.fleet.Device;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import uz.pdp.fastfoodapp.entity.user.User;
import uz.pdp.fastfoodapp.entity.user.UserRepository;
import uz.pdp.fastfoodapp.entity.user.role.Role;
import uz.pdp.fastfoodapp.entity.user.role.Roles;
import uz.pdp.fastfoodapp.entity.user.role.RolesRepository;
import uz.pdp.fastfoodapp.entity.user.verificationCodes.VerificationService;
import uz.pdp.fastfoodapp.security.JwtProvider;
import uz.pdp.fastfoodapp.template.ApiResponse;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class AuthService implements UserDetailsService {
    private final AuthenticationManager authenticationManager;
    private final VerificationService verificationService;
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final JwtProvider jwtProvider;

    public HttpEntity<?> registerUser(RegisterDto registerDto) {
        ApiResponse apiResponse = verificationService.validateSmsCode(registerDto.getPhoneNumber(), registerDto.getSmsCode());
        if (!apiResponse.isSuccess())
            return ResponseEntity.status(404).body(apiResponse);

        if (userRepository.existsByPhoneNumber(registerDto.getPhoneNumber()))
            return ResponseEntity.status(409).body(new ApiResponse("This user registered before", false));
        Set<Roles> roleUser = new HashSet<>();
        Roles userRole =  new Roles(Role.CUSTOMER);
        roleUser.add(userRole);
        User user = new User(
                registerDto.getFullName(),
                registerDto.getPhoneNumber(),
                passwordEncoder.encode(String.valueOf(registerDto.getSmsCode())),
                roleUser,
                null
        );
        User savedUser = userRepository.save(user);
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                savedUser.getPhoneNumber(), registerDto.getSmsCode());
        authenticationManager.authenticate(authenticationToken);
        String token = jwtProvider.generateToken(user.getPhoneNumber(), user.getRoles(),user.getPermissions());
        Map<String, Object> registerMap = new HashMap<>();
        registerMap.put("token", token);
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

        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                loginDto.getPhoneNumber(), loginDto.getCodeSent());
        Authentication authenticate = authenticationManager.authenticate(authenticationToken);
        User user = (User) authenticate.getPrincipal();
        String token = jwtProvider.generateToken(user.getPhoneNumber(), user.getRoles(),user.getPermissions());
        Map<String, Object> loginMap = new HashMap<>();
        loginMap.put("token", token);
        loginMap.put("user_id", user.getId().toString());
        return ResponseEntity.status(200).body(new ApiResponse("Successful signed in.", true, loginMap));
    }
}

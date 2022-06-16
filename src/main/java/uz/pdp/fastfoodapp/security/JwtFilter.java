package uz.pdp.fastfoodapp.security;


import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import uz.pdp.fastfoodapp.entity.user.User;
import uz.pdp.fastfoodapp.entity.user.UserRepository;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class JwtFilter extends OncePerRequestFilter {
    final JwtProvider jwtProvider;
    final UserRepository userRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String authentication = request.getHeader("Authentication");
        if (authentication != null && authentication.startsWith("Bearer") && authentication.length() > 7) {
            authentication = authentication.substring(7);
            String number = jwtProvider.getUsernameFromToken(authentication);
            if (number != null) {
                Optional<User> optionalUser = userRepository.findByPhoneNumber(number);
                UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = null;
                if (optionalUser.isPresent()) {
                    usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(optionalUser.get(), null, optionalUser.get().getAuthorities());
                }
                SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
            }
        }
        filterChain.doFilter(request, response);
    }
}

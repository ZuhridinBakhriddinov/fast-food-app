//package uz.pdp.fastfoodapp.test;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.http.HttpMethod;
//import org.springframework.security.authentication.AuthenticationManager;
//import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
//import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.builders.WebSecurity;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
//import org.springframework.security.config.http.SessionCreationPolicy;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
//import org.springframework.security.web.firewall.HttpFirewall;
//import org.springframework.security.web.firewall.StrictHttpFirewall;
//import uz.pdp.fastfoodapp.entity.authService.AuthService;
//
//import javax.servlet.Filter;
//
//@Configuration
//
//@EnableWebSecurity
//
//@EnableGlobalMethodSecurity(
//
//        securedEnabled = true,
//
//        jsr250Enabled = true,
//
//        prePostEnabled = true
//
//)
//
//public class SecurityConfig extends WebSecurityConfigurerAdapter {
//
//
//    @Autowired
//
//    AuthService authService;
//
//
//    @Autowired
//
//    JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
//
//
//    @Bean
//
//    public PasswordEncoder passwordEncoder() {
//
//        return new BCryptPasswordEncoder();
//
//    }
//
//
//
//    @Override
//
//    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//
//        auth.userDetailsService(authService).passwordEncoder(passwordEncoder());
//
//    }
//
//
//    @Bean
//
//    public HttpFirewall allowUrlEncodedSlashHttpFirewall() {
//
//        StrictHttpFirewall firewall = new StrictHttpFirewall();
//
//        firewall.setAllowUrlEncodedSlash(true);
//
//        return firewall;
//
//    }
//
//
//    @Bean
//
//    @Override
//
//    public AuthenticationManager authenticationManagerBean() throws Exception {
//
//        return super.authenticationManagerBean();
//
//    }
//
//
//    @Bean
//
//    public JwtAuthenticationFilter jwtAuthenticationFilter() {
//
//        return new JwtAuthenticationFilter();
//
//    }
//
//
//
//    @Override
//
//    protected void configure(HttpSecurity http) throws Exception {
//
//        http
//
//                .cors().and()
//
//                .csrf()
//
//                .disable()
//
//                .exceptionHandling()
//
//                .authenticationEntryPoint(jwtAuthenticationEntryPoint)
//
//                .and()
//
//                .sessionManagement()
//
//                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
//
//                .and()
//
//                .authorizeRequests()
//
//                .antMatchers(HttpMethod.GET, "/",
//
//                        "/favicon.ico",
//
//                        "/**/*.png",
//
//                        "/**/*.gif",
//
//                        "/**/*.pdf",
//
//                        "/**/*.svg",
//
//                        "/**/*.jpg",
//
//                        "/**/*.mp3",
//
//                        "/**/*.jpeg",
//
//                        "/**/*.html",
//
//                        "/**/*.css",
//
//                        "/**/*.js",
//
////                        "/swagger-ui/**",
//
////                        "/swagger-ui.html",
//
////                        "/swagger-resources/**",
//
////                        "/v3/api-docs/**",
//
////                        "/v2/**",
//
//                        "/csrf",
//
//                        "/webjars/**")
//
//                .permitAll()
//
//                .antMatchers(HttpMethod.POST, "/api/auth/login")
//
//                .permitAll()
//
//                .antMatchers(HttpMethod.GET, "/","/login","/api/auth/logout")
//
//                .permitAll()
//
//                .anyRequest().authenticated();
//
//
//
//        // Add our custom JWT security filter
//
//        http.addFilterBefore((Filter) jwtAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);
//
//
//    }
//
//
//    @Override
//
//    public void configure(WebSecurity web) throws Exception {
//
//        //@formatter:off
//
//        super.configure(web);
//
//        web.httpFirewall(allowUrlEncodedSlashHttpFirewall());
//
//    }
//
//}

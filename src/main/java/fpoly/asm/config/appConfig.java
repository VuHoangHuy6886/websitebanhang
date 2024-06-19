package fpoly.asm.config;

import fpoly.asm.service.impl.NhanVienConfigIpm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class appConfig {
    public final UserDetailsService userDetailsService;

    @Autowired
    public appConfig(NhanVienConfigIpm nhanVienService) {
        this.userDetailsService = nhanVienService;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return NoOpPasswordEncoder.getInstance(); // Sử dụng NoOpPasswordEncoder để không mã hóa mật khẩu
    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService);
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(authorizeRequests -> authorizeRequests
                        .requestMatchers("/admin/**").hasRole("NV") // Kiểm tra quyền ROLE_EMPLOYEE cho /admin
                        .requestMatchers("/hoa-don/index/**").hasRole("NV")
                        .requestMatchers("/danh-muc/index/**").hasRole("NV")
                        .requestMatchers("/san-pham/**").hasRole("NV")
                        .requestMatchers("/khach-hang/index/**").hasRole("NV")
                        .requestMatchers("/ban-hang/index/**").hasRole("NV")
                        .requestMatchers("/ban-hang/them-san-pham/**").hasRole("NV")
                        .requestMatchers("/nhan-vien/index/**").hasRole("ADMIN")
                        .requestMatchers("/trang-chu/**").permitAll()
                        .anyRequest().permitAll() // Các request khác đều được phép truy cập
                )
                .formLogin(form -> form
                        .loginPage("/login")
                        .loginProcessingUrl("/loginSucces")
                        .defaultSuccessUrl("/admin")
                        .permitAll()
                )
                .logout(logout -> logout
                        .logoutUrl("/logout") // Định nghĩa URL cho trang logout
                        .permitAll()
                )
                .exceptionHandling(exceptionHandling -> exceptionHandling
                        .accessDeniedPage("/access-denied") // Trang lỗi khi bị từ chối truy cập
                )
                //.logout(logout -> logout.permitAll())
                .csrf(csrf -> csrf.disable()); // Vô hiệu hóa CSRF (nếu cần thiết)

        return http.build();
    }
}

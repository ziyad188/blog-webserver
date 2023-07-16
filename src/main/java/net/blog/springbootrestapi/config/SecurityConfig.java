package net.blog.springbootrestapi.config;

import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

//this class become java  based config we can diffened spring bean defnition
@Configuration
//  to provide role based access
@EnableMethodSecurity
public class SecurityConfig {
    private UserDetailsService userDetailsService;
    SecurityConfig(UserDetailsService userDetailsService){
        this.userDetailsService = userDetailsService;
    }
    //password encoder
    @Bean
    public static PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
    //database auth here authentication manager will take creditioals from userdetailservevice and do the encodeing
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }


    //toenable basic auth
    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{
        //disable csrf
        http.csrf( (csrf) -> csrf.disable())
                .authorizeHttpRequests((authorize) ->
                        //to authorise all request
                        //authorize.anyRequest().authenticated()
                        // to authrise based on role so here det request will acessible to all of them and all other request will auth
                        authorize.requestMatchers(HttpMethod.GET,"/api/**").permitAll()
                                .requestMatchers("/api/auth/**").permitAll()
                                .anyRequest().authenticated()

                ).httpBasic(Customizer.withDefaults());

        return http.build();
    }
    //users
//    @Bean
//    public UserDetailsService userDetailsService(){
//        UserDetails ramesh = User.builder()
//                .username("ziyad")
//                .password(passwordEncoder().encode("ziyad"))
//                .roles("USER")
//                .build();
//        UserDetails admin = User.builder()
//                .username("admin")
//                .password(passwordEncoder().encode("admin"))
//                .roles("ADMIN")
//                .build();
//        return new InMemoryUserDetailsManager(ramesh,admin);
//    }

}

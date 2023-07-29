package net.blog.springbootrestapi.service.impl;

import lombok.Setter;
import net.blog.springbootrestapi.Security.JwtTokenProvider;
import net.blog.springbootrestapi.entity.Roles;
import net.blog.springbootrestapi.entity.User;
import net.blog.springbootrestapi.exception.BlogApiException;
import net.blog.springbootrestapi.payload.LoginDto;
import net.blog.springbootrestapi.payload.SignUpDto;
import net.blog.springbootrestapi.repository.RoleRepository;
import net.blog.springbootrestapi.repository.UserRespository;
import net.blog.springbootrestapi.service.AuthService;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class AuthServiceImpl implements AuthService {
    private AuthenticationManager authenticationManager;
    private UserRespository userRespository;
    private RoleRepository roleRepository;
    private PasswordEncoder passwordEncoder;
    private JwtTokenProvider jwtTokenProvider;


    public AuthServiceImpl(AuthenticationManager authenticationManager, UserRespository userRespository, RoleRepository roleRepository, PasswordEncoder passwordEncoder, JwtTokenProvider jwtTokenProvider) {
        this.authenticationManager = authenticationManager;
        this.userRespository = userRespository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @Override
    public String login(LoginDto loginDto) {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginDto.getUsernameOrEmail(),loginDto.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String token = jwtTokenProvider.generateToken(authentication);
        return token;

    }

    @Override
    public String register(SignUpDto signUpDto) {
        //check for username exist
        if(userRespository.existsByUsername(signUpDto.getUsername())){
            throw new BlogApiException(HttpStatus.BAD_REQUEST,"Username is already registered");
        }
        //check user email
        if(userRespository.existsByEmail(signUpDto.getEmail())){
            throw  new BlogApiException(HttpStatus.BAD_REQUEST,"email already exits");
        }
        //object if user class
        User user = new User();
        user.setName(signUpDto.getName());
        user.setUsername(signUpDto.getUsername());
        user.setEmail(signUpDto.getEmail());
        user.setPassword(passwordEncoder.encode(signUpDto.getPassword()));

        Set<Roles> roles = new HashSet<>();
        Roles userRole =roleRepository.findByName("ROLE_USER").get();
                roles.add(userRole);
        user.setRoles(roles);
        userRespository.save(user);
        return "User registered successfully";

    }
}

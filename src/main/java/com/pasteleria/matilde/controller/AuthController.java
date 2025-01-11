package com.pasteleria.matilde.controller;

import com.pasteleria.matilde.config.JwtProvider;
import com.pasteleria.matilde.model.Cart;
import com.pasteleria.matilde.model.USER_ROLE;
import com.pasteleria.matilde.model.User;
import com.pasteleria.matilde.repository.CartRepository;
import com.pasteleria.matilde.repository.UserRepository;
import com.pasteleria.matilde.request.ForgotPasswordRequest;
import com.pasteleria.matilde.request.LoginRequest;
import com.pasteleria.matilde.request.ResetPasswordRequest;
import com.pasteleria.matilde.response.AuthResponse;
import com.pasteleria.matilde.service.CustomerUserDetailsService;
import com.pasteleria.matilde.service.EmailService;
import com.pasteleria.matilde.service.PasswordResetService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {


    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    private final  JwtProvider jwtProvider;

    private final CustomerUserDetailsService customerUserDetailsService;

    private final CartRepository cartRepository;

    private final PasswordResetService passwordResetService;

    private final EmailService emailService;

    @PostMapping("/signup")
    public ResponseEntity<AuthResponse> createUserHandler(@RequestBody User user) throws Exception {

        User isEmailExist = userRepository.findByEmail(user.getEmail());
        if(isEmailExist!=null){
            throw new Exception("The email is already used with another account");
        }

        User createdUser = new User();
        createdUser.setEmail(user.getEmail());
        createdUser.setFullName(user.getFullName());
        createdUser.setRole(user.getRole());
        createdUser.setPassword(passwordEncoder.encode(user.getPassword()));

        User savedUser = userRepository.save(createdUser);

        Cart cart = new Cart();
        cart.setCustomer(savedUser);
        cartRepository.save(cart);

        Authentication authentication = new UsernamePasswordAuthenticationToken(user.getEmail(), user.getPassword());
        SecurityContextHolder.getContext().setAuthentication(authentication);

        String jwt = jwtProvider.generateToken(authentication);

        AuthResponse authResponse = new AuthResponse();
        authResponse.setJwt(jwt);
        authResponse.setMessages("Register success");
        authResponse.setRole(savedUser.getRole());


        return new ResponseEntity<>(authResponse, HttpStatus.CREATED);
    }


    @PostMapping("/signin")
    public ResponseEntity<AuthResponse> singnin(@RequestBody LoginRequest req){

        String username = req.getEmail();
        String password = req.getPassword();

        Authentication authentication = authenticate(username,password);

        Collection<? extends GrantedAuthority>authorities=authentication.getAuthorities();
        String role = authorities.isEmpty()?null:authorities.iterator().next().getAuthority();

        String jwt = jwtProvider.generateToken(authentication);

        AuthResponse authResponse = new AuthResponse();
        authResponse.setJwt(jwt);
        authResponse.setMessages("Login success");
        authResponse.setRole(USER_ROLE.valueOf(role));



        //authResponse.setRole(savedUser.getRole());


        return new ResponseEntity<>(authResponse, HttpStatus.OK);


    }

    private Authentication authenticate(String username, String password) {

        UserDetails userDetails = customerUserDetailsService.loadUserByUsername(username);

        if(userDetails==null){
            throw new BadCredentialsException("Invalid user.");
        }
        if(!passwordEncoder.matches(password, userDetails.getPassword())){
            throw new BadCredentialsException("Invalid password.");
        }
        return new UsernamePasswordAuthenticationToken(userDetails,null,userDetails.getAuthorities());
    }



    @PostMapping("/forgot-password")
    public ResponseEntity<String> handleForgotPassword(@RequestBody ForgotPasswordRequest request) {
        String email = request.getEmail();
        User user = userRepository.findByEmail(email);
        if (user != null) {
            String token = passwordResetService.createPasswordResetTokenForUser(user);
            String resetLink = "http://localhost:3000/reset-password?token=" + token;
            String emailBody = "To reset your password, please click the following link: " + resetLink;
            emailService.sendSimpleMessage(email, "Password Reset Request", emailBody);
            return ResponseEntity.ok("Password reset link sent to your email.");
        } else {
            return ResponseEntity.badRequest().body("User not found.");
        }
    }


    @PostMapping("/reset-password")
    public ResponseEntity<String> resetPassword(@RequestBody ResetPasswordRequest request) {
        User user = passwordResetService.validatePasswordResetToken(request.getToken());
        if (user == null) {
            return new ResponseEntity<>("Invalid or expired token", HttpStatus.BAD_REQUEST);
        }

        passwordResetService.changePassword(user, request.getNewPassword());
        return new ResponseEntity<>("Password successfully reset", HttpStatus.OK);
    }

    /*@PostMapping("/forgot-password")
    public ResponseEntity<String> forgotPassword(@RequestBody ForgotPasswordRequest request) {
        String email = request.getEmail();
        Optional<User> optionalUser = Optional.ofNullable(userRepository.findByEmail(email));
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            String token = passwordResetService.createPasswordResetTokenForUser(user);

            // Enviar el correo electrónico con el token
            String resetLink = "http://localhost:4545/" + token;
            String emailBody = "To reset your password, please click the following link: " + resetLink;
            emailService.sendSimpleMessage(email, "Password Reset Request", emailBody);

            return ResponseEntity.ok("Password reset token sent.");
        } else {
            return ResponseEntity.badRequest().body("User not found.");
        }
    }


    @PostMapping("/reset-password")
    public ResponseEntity<String> resetPassword(@RequestBody ResetPasswordRequest request) {
        User user = passwordResetService.validatePasswordResetToken(request.getToken());
        if (user == null) {
            return new ResponseEntity<>("Invalid or expired token", HttpStatus.BAD_REQUEST);
        }

        passwordResetService.changePassword(user, request.getNewPassword());
        return new ResponseEntity<>("Password successfully reset", HttpStatus.OK);
    }*/

}

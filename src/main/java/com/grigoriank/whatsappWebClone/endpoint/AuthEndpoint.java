package com.grigoriank.whatsappWebClone.endpoint;

import com.grigoriank.whatsappWebClone.dto.request.LoginRequestDTO;
import com.grigoriank.whatsappWebClone.dto.response.AuthResponseDTO;
import com.grigoriank.whatsappWebClone.entity.User;
import com.grigoriank.whatsappWebClone.exception.UserException;
import com.grigoriank.whatsappWebClone.repository.UserRepository;
import com.grigoriank.whatsappWebClone.security.CustomerUserServiceImpl;
import com.grigoriank.whatsappWebClone.util.JwtTokenUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthEndpoint {

    private final CustomerUserServiceImpl customerUserService;
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final JwtTokenUtil tokenUtil;

    @PostMapping("/signup")
    public ResponseEntity<AuthResponseDTO> register(@RequestBody User user) throws UserException {
        String fullName = user.getFullName();
        String email = user.getEmail();
        String password = user.getPassword();

        User isUser = userRepository.findByEmail(email);
        if (isUser != null) {
            throw new UserException("Email is used with another account " + email);
        }
        User createdUser = new User();
        createdUser.setFullName(fullName);
        createdUser.setEmail(email);
        createdUser.setPassword(passwordEncoder.encode(password));
        userRepository.save(createdUser);

        Authentication authentication = new UsernamePasswordAuthenticationToken(email, password);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = tokenUtil.generateToken(authentication);

        AuthResponseDTO authDTO = AuthResponseDTO.builder()
                .jwt(jwt)
                .isAuth(true)
                .build();

        return new ResponseEntity<>(authDTO, HttpStatus.OK);
    }

    @PostMapping("/sign-in")
    public ResponseEntity<AuthResponseDTO> login(@RequestBody LoginRequestDTO loginDTO) {
        String email = loginDTO.getEmail();
        String password = loginDTO.getPassword();

        Authentication authentication = authenticate(email, password);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = tokenUtil.generateToken(authentication);

        AuthResponseDTO responseDTO = AuthResponseDTO.builder()
                .jwt(jwt)
                .isAuth(true)
                .build();

        return new ResponseEntity<>(responseDTO, HttpStatus.OK);
    }

    private Authentication authenticate(String username, String password) {
        UserDetails userDetails = customerUserService.loadUserByUsername(username);
        if (userDetails == null) {
            throw new BadCredentialsException("Invalid username");
        }
        if (!passwordEncoder.matches(password, userDetails.getPassword())) {
            throw new BadCredentialsException("Invalid password or username");
        }

        return new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
    }
}

package com.example.auth.application;

import com.example.auth.domain.User;
import com.example.auth.domain.UserRepository;
import com.example.auth.infrastructure.security.JwtTokenProvider;
import com.example.auth.shared.functional.Result;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Servicio de autenticación.
 * Demuestra programación funcional con Result, Optional y Streams.
 */
@Service
public class AuthService implements UserDetailsService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;

    public AuthService(UserRepository userRepository,
                      PasswordEncoder passwordEncoder,
                      JwtTokenProvider jwtTokenProvider) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByUsername(username)
            .map(this::toUserDetails)
            .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado: " + username));
    }

    @Transactional
    public Result<String, String> login(String username, String password) {
        return userRepository.findByUsername(username)
            .filter(user -> passwordEncoder.matches(password, user.getPassword()))
            .filter(User::isEnabled)
            .map(user -> jwtTokenProvider.generateToken(toUserDetails(user)))
            .map(Result::<String, String>success)
            .orElse(Result.failure("Credenciales inválidas"));
    }

    @Transactional
    public Result<User, String> register(String username, String email, String password) {
        return Optional.ofNullable(userRepository.findByUsername(username))
            .filter(Optional::isEmpty)
            .map(ignored -> {
                User user = new User(username, email, passwordEncoder.encode(password));
                return Result.<User, String>success(userRepository.save(user));
            })
            .orElse(Result.failure("El usuario ya existe"));
    }

    private UserDetails toUserDetails(User user) {
        return org.springframework.security.core.userdetails.User.builder()
            .username(user.getUsername())
            .password(user.getPassword())
            .authorities(user.getRoles().stream()
                .map(role -> "ROLE_" + role.getName())
                .toArray(String[]::new))
            .disabled(!user.isEnabled())
            .build();
    }
}


package com.shopwise.services.auth;

import com.shopwise.dto.SignUpRequest;
import com.shopwise.dto.UserDto;
import com.shopwise.entity.User;
import com.shopwise.enums.UserRole;
import com.shopwise.repository.UserRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;

    private BCryptPasswordEncoder bCryptPasswordEncoder;

    public UserDto createUser(SignUpRequest signUpRequest) {
        User user = new User();
        user.setEmail(signUpRequest.getEmail());
        user.setUserName(signUpRequest.getName());
        user.setPassword(new BCryptPasswordEncoder().encode(signUpRequest.getPassword()));
        user.setUserRole(UserRole.CUSTOMER);
        User createUser = userRepository.save(user);

        UserDto userDto = new UserDto();
        userDto.setId(createUser.getId());

        return userDto;
    }

    public Boolean hasUserWithEmail(String email) {
        return userRepository.findFirstByEmail(email).isPresent();
    }

    @PostConstruct
    public void createAdminAccount () {
        Optional<User> adminAccount = userRepository.findByUserRole(UserRole.ADMIN);
        if(!adminAccount.isPresent()) {
            User user = new User();
            user.setEmail("admin@gmail.com");
            user.setUserName("admin");
            user.setUserRole(UserRole.ADMIN);
            user.setPassword(new BCryptPasswordEncoder().encode("admin"));
            userRepository.save(user);
        }
    }
}

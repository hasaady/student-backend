package com.demo.student.module.user.service;

import com.demo.student.module.auth.dto.request.RegisterRequest;
import com.demo.student.module.user.entity.User;
import com.demo.student.module.user.repository.UserRepository;
import com.demo.student.module.user.dto.request.ProfileUpdateRequest;
import com.demo.student.module.user.dto.response.ProfileResponse;
import com.fasterxml.jackson.databind.JsonNode;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final RestTemplate restTemplate;
    private final CountryService countryService;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public User registerUser(RegisterRequest request) {
        var country = countryService.getCountryByCode(request.getCountryCode());
        String capital = country.getCapital() != null && !country.getCapital().isEmpty() ? country.getCapital().get(0) : "N/A";

        User user = new User();
        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        user.setUsername(request.getUsername());
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setCountryCode(request.getCountryCode());
        user.setCountryName(country.getName().getCommon());
        user.setCapital(capital);
        user.setRegion(country.getRegion());

        try {
            userRepository.save(user);
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }

        return user;
    }

    public ProfileResponse getUserProfile(long userId) {
        Optional<User> userOpt = userRepository.findById(userId);

        if (userOpt.isEmpty()) {
            throw new RuntimeException("User not found");
        }

        User user = userOpt.get();

        return new ProfileResponse(user.getFirstName(), user.getLastName(), user.getEmail(), user.getCountryName());
    }

    public ProfileResponse updateUserProfile(long userId, ProfileUpdateRequest request) {
        Optional<User> userOpt = userRepository.findById(userId);

        if (userOpt.isEmpty()) {
            throw new RuntimeException("User not found");
        }

        User user = userOpt.get();
        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());

        if (!request.getCountryCode().isEmpty() || !request.getCountryCode().equals(user.getCountryCode())) {
            var country = countryService.getCountryByCode(request.getCountryCode());
            String capital = country.getCapital() != null && !country.getCapital().isEmpty() ? country.getCapital().get(0) : "N/A";
            user.setCountryCode(request.getCountryCode());
            user.setCountryName(country.getName().getCommon());
            user.setCapital(capital);
            user.setRegion(country.getRegion());
        }

        userRepository.save(user);

        return getUserProfile(userId);
    }
}

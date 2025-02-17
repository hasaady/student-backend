package com.demo.student.module.service;

import com.demo.student.module.auth.dto.request.RegisterRequest;
import com.demo.student.module.entity.User;
import com.demo.student.module.repository.UserRepository;
import com.demo.student.module.dto.request.ProfileUpdateRequest;
import com.demo.student.module.dto.response.ProfileResponse;
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
        userRepository.save(user);

        return user;
    }

    public ProfileResponse getUserProfile(String username) {
        Optional<User> userOpt = userRepository.findByEmail(username);

        if (userOpt.isEmpty()) {
            throw new RuntimeException("User not found");
        }

        User user = userOpt.get();
        String countryCode = user.getCountryCode();
        String countryApiUrl = "https://restcountries.com/v3.1/alpha/" + countryCode;

        JsonNode countryResponse = restTemplate.getForObject(countryApiUrl, JsonNode.class);

        String countryName = countryResponse.path(0).path("name").path("common").asText();

        return new ProfileResponse(user.getFirstName(), user.getLastName(), user.getEmail(), countryName);
    }

    public ProfileResponse updateUserProfile(String email, ProfileUpdateRequest request) {
        Optional<User> userOpt = userRepository.findByEmail(email);

        if (userOpt.isEmpty()) {
            throw new RuntimeException("User not found");
        }

        User user = userOpt.get();
        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());

        String countryApiUrl = "https://restcountries.com/v3.1/alpha/" + request.getCountryCode();
        JsonNode countryResponse = restTemplate.getForObject(countryApiUrl, JsonNode.class);

        if (countryResponse.isEmpty()) {
            throw new RuntimeException("Invalid country code");
        }

        user.setCountryCode(request.getCountryCode());
        userRepository.save(user);

        return getUserProfile(email);
    }
}

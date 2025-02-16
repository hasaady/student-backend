package com.demo.student.module.user.service;

import com.demo.student.module.user.entity.User;
import com.demo.student.module.user.repository.UserRepository;
import com.demo.student.module.user.dto.request.ProfileUpdateRequest;
import com.demo.student.module.user.dto.response.ProfileResponse;
import com.fasterxml.jackson.databind.JsonNode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final RestTemplate restTemplate;

    public ProfileResponse getUserProfile(String email) {
        Optional<User> userOpt = userRepository.findByEmail(email);

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

        // Validate new country code
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

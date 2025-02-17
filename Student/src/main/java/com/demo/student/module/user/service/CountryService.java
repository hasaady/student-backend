package com.demo.student.module.user.service;

import com.demo.student.module.user.dto.response.CountryResponse;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class CountryService {
    private final RestTemplate restTemplate = new RestTemplate();

    public CountryResponse getCountryByCode(String countryCode) {
        String url = "https://restcountries.com/v3.1/alpha/" + countryCode;
        CountryResponse[] response = restTemplate.getForObject(url, CountryResponse[].class);

        if (response != null && response.length > 0) {
            return response[0];
        }
        throw new RuntimeException("Invalid country code: " + countryCode);
    }
}

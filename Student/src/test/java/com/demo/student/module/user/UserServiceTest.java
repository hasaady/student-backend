//package com.demo.student.module.user;
//
//import static org.mockito.Mockito.*;
//import static org.junit.jupiter.api.Assertions.*;
//
//import com.demo.student.module.auth.dto.request.RegisterRequest;
//import com.demo.student.module.dto.response.CountryResponse;
//import com.demo.student.module.entity.User;
//import com.demo.student.module.repository.UserRepository;
//import com.demo.student.module.service.CountryService;
//import com.demo.student.module.service.UserService;
//import org.junit.jupiter.api.Test;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.springframework.security.crypto.password.PasswordEncoder;
//
//import java.util.List;
//
//class UserServiceTest {
//
//    @Mock
//    private UserRepository userRepository;
//
//    @Mock
//    private CountryService countryService;
//
//    @Mock
//    private PasswordEncoder passwordEncoder;
//
//    @InjectMocks
//    private UserService userService;
//
//    @Test
//    void testRegisterUser() {
//        RegisterRequest request = new RegisterRequest();
//        request.setUsername("john_doe");
//        request.setEmail("john@example.com");
//        request.setPassword("password");
//        request.setCountryCode("SA");
//
//        CountryResponse country = new CountryResponse();
//        CountryResponse.CountryName name = new CountryResponse.CountryName();
//        name.setCommon("Saudi Arabia");
//        country.setName(name);
//        country.setCapital(List.of("Riyadh"));
//        country.setRegion("Asia");
//
//        when(countryService.getCountryByCode("SA")).thenReturn(country);
//        when(passwordEncoder.encode("password")).thenReturn("hashedPassword");
//        when(userRepository.save(any(User.class))).thenAnswer(invocation -> invocation.getArgument(0));
//
//        User savedUser = userService.registerUser(request);
//
//        assertEquals("john_doe", savedUser.getUsername());
//        assertEquals("Saudi Arabia", savedUser.getCountryName());
//        assertEquals("Riyadh", savedUser.getCapital());
//    }
//}

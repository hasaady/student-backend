package com.demo.student.module.dto.response;

import lombok.Data;
import java.util.List;

@Data
public class CountryResponse {
    private CountryName name;
    private List<String> capital;
    private String region;

    @Data
    public static class CountryName {
        private String common;
        private String official;
    }
}

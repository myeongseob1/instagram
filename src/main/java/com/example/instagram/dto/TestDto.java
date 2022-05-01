package com.example.instagram.dto;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
public class TestDto {
    @NotEmpty
    private String id;
    @NotEmpty
    private String name;
}

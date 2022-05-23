package com.example.instagram.dto;

import io.swagger.annotations.ApiParam;
import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
public class TestDto {
    @NotEmpty
    @ApiParam(value = "test id",required = true)
    private String id;
    @NotEmpty
    @ApiParam(value = "test id",required = true)
    private String name;
}

package com.example.taskuniversity.payload;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.Date;

@Data
public class UniversityDto {

    @NotBlank(message = "University's name cannot be empty")
    private String name;

    private String address;

    @Size(min = 1000,max = 2022)
    private Integer openYear;
}

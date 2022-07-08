package com.example.taskuniversity.payload;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class SubjectDto {

    @NotBlank(message = "Subject's name cannot be empty")
    private String name;
}

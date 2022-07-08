package com.example.taskuniversity.payload;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class StudentDto {

    @NotBlank(message = "Student's name cannot be empty")
    private String name;

    @NotNull(message = "group id cannot be null")
    private Long groupId;
}

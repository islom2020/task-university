package com.example.taskuniversity.payload;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class FacultyDto {

    @NotBlank(message = "Faculty's name cannot be empty")
    private String name;

    @NotNull(message = "university id cannot be null")
    private Long universityId;

}

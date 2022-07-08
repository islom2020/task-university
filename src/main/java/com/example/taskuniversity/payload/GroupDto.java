package com.example.taskuniversity.payload;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class GroupDto {

    @NotBlank(message = "Group's name cannot be empty")
    private String name;

    @NotNull(message = "faculty id cannot be null")
    private Long facultyId;
}

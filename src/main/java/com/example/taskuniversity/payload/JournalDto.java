package com.example.taskuniversity.payload;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

@Data
public class JournalDto {

    @NotBlank(message = "Subject's name cannot be empty")
    private String name;

    @NotNull(message = "group id cannot be null")
    private Long groupId;

    @NotNull(message = "subjects cannot be null")
    List<Long> subjects;
}

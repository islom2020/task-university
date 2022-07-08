package com.example.taskuniversity.payload;

import lombok.Data;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Data
public class MarkDto {

    @Min(value = 0,message = "grade must be at least 0")
    @Max(value = 100, message = "grade must be at last 100")
    private Integer grade;

    @NotNull(message = "student id cannot be null")
    private Long studentId;

    @NotNull(message = "subject id cannot be null")
    private Long subjectId;

}

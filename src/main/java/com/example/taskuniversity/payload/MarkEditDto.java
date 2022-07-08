package com.example.taskuniversity.payload;

import lombok.Data;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
public class MarkEditDto {

    @Min(value = 0,message = "grade must be at least 0")
    @Max(value = 100, message = "grade must be at last 100")
    private Integer grade;
}

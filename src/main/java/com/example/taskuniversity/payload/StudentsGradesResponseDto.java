package com.example.taskuniversity.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class StudentsGradesResponseDto {

    private String name;

    private Double averageGrade;
}

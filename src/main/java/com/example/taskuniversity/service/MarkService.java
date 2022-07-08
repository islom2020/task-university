package com.example.taskuniversity.service;

import com.example.taskuniversity.entity.Mark;
import com.example.taskuniversity.entity.Student;
import com.example.taskuniversity.entity.Subject;
import com.example.taskuniversity.exception.customException.IdNotFoundException;
import com.example.taskuniversity.payload.ApiResponse;
import com.example.taskuniversity.payload.MarkDto;
import com.example.taskuniversity.payload.MarkEditDto;
import com.example.taskuniversity.repository.MarkRepository;
import com.example.taskuniversity.repository.StudentRepository;
import com.example.taskuniversity.repository.SubjectRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MarkService {
    private final MarkRepository markRepository;
    private final StudentRepository studentRepository;
    private final SubjectRepository subjectRepository;

    public ApiResponse add(MarkDto markDto) {
        Long universityId = markDto.getStudentId();
        Optional<Student> optionalStudent = studentRepository.findById(universityId);
        if (optionalStudent.isEmpty()) throw new IdNotFoundException("Student not found");
        Student student = optionalStudent.get();

        Long subjectId = markDto.getSubjectId();
        Optional<Subject> optionalSubject = subjectRepository.findById(subjectId);
        if (optionalSubject.isEmpty()) return new ApiResponse(false,"Subject not found");
        Subject subject = optionalSubject.get();

        boolean b = markRepository.existsByStudentAndSubject(student, subject);
        if (b) return new ApiResponse(false,"This student has grade from this subject already");

        Mark mark = new Mark(markDto.getGrade(), student,subject);

        markRepository.save(mark);
        return new ApiResponse(true, "Mark added successfully");
    }

    public ApiResponse delete(Long id) {
        boolean existsById = markRepository.existsById(id);
        if (!existsById) return new ApiResponse(false, "Mark not found");
        markRepository.deleteById(id);
        return new ApiResponse(true, "Mark deleted successfully");
    }

    public ApiResponse edit(Long id, MarkEditDto markEditDto) {
        Optional<Mark> optionalMark = markRepository.findById(id);
        if (optionalMark.isEmpty()) return new ApiResponse(false, "Mark not found");
        Mark mark = optionalMark.get();
        mark.setGrade(markEditDto.getGrade());
        markRepository.save(mark);
        return new ApiResponse(true, "Mark edited successfully");
    }

    public ApiResponse getById(Long id) {
        Optional<Mark> optionalMark = markRepository.findById(id);
        if (optionalMark.isEmpty())
            return new ApiResponse(false, "Mark not found");
        else
            return new ApiResponse(true, "Mark by id", optionalMark.get());
    }
}

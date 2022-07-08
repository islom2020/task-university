package com.example.taskuniversity.service;

import com.example.taskuniversity.entity.Subject;
import com.example.taskuniversity.exception.customException.IdNotFoundException;
import com.example.taskuniversity.payload.ApiResponse;
import com.example.taskuniversity.payload.SubjectDto;
import com.example.taskuniversity.repository.SubjectRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class SubjectService {
    private final SubjectRepository subjectRepository;

    public ApiResponse add(SubjectDto subjectDto) {
        Subject subject = new Subject();
        subject.setName(subjectDto.getName());
        subjectRepository.save(subject);
        return new ApiResponse(true, "Subject added successfully");
    }

    public ApiResponse delete(Long id) {
        boolean existsById = subjectRepository.existsById(id);
        if (!existsById) throw new IdNotFoundException("Subject not found");
        subjectRepository.deleteById(id);
        return new ApiResponse(true, "Subject deleted successfully");
    }

    public ApiResponse edit(Long id, SubjectDto subjectDto) {
        Optional<Subject> optionalSubject = subjectRepository.findById(id);
        if (optionalSubject.isEmpty()) throw new IdNotFoundException("Subject not found");
        Subject subject = optionalSubject.get();

        subject.setName(subjectDto.getName());
        subjectRepository.save(subject);

        return new ApiResponse(true, "Subject edited successfully");
    }

    public ApiResponse getById(Long id) {
        Optional<Subject> optionalSubject = subjectRepository.findById(id);
        if (optionalSubject.isEmpty())
            throw new IdNotFoundException("Subject not found");
        else
            return new ApiResponse(true, "Subject by id", optionalSubject.get());
    }
}

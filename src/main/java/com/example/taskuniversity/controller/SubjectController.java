package com.example.taskuniversity.controller;

import com.example.taskuniversity.payload.ApiResponse;
import com.example.taskuniversity.payload.SubjectDto;
import com.example.taskuniversity.service.SubjectService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/subject")
@RequiredArgsConstructor
public class SubjectController {

    private final SubjectService subjectService;

    @PostMapping
    public HttpEntity<?> add(@Valid @RequestBody SubjectDto subjectDto) {
        ApiResponse apiResponse = subjectService.add(subjectDto);
        return ResponseEntity
                .status(apiResponse.isSuccess() ? HttpStatus.CREATED : HttpStatus.BAD_REQUEST)
                .body(apiResponse);
    }

    @DeleteMapping("/{id}")
    public HttpEntity<?> delete(@PathVariable Long id) {
        ApiResponse apiResponse = subjectService.delete(id);
        return ResponseEntity
                .status(apiResponse.isSuccess() ? HttpStatus.OK : HttpStatus.BAD_REQUEST)
                .body(apiResponse);
    }

    @PutMapping("/{id}")
    public HttpEntity<?> edit(@PathVariable Long id, @Valid @RequestBody SubjectDto subjectDto) {
        ApiResponse apiResponse = subjectService.edit(id, subjectDto);
        return ResponseEntity
                .status(apiResponse.isSuccess() ? HttpStatus.OK : HttpStatus.BAD_REQUEST)
                .body(apiResponse);
    }

    @GetMapping("/{id}")
    public HttpEntity<?> getById(@PathVariable Long id) {
        ApiResponse apiResponse = subjectService.getById(id);
        return ResponseEntity
                .status(apiResponse.isSuccess() ? HttpStatus.OK : HttpStatus.BAD_REQUEST)
                .body(apiResponse);
    }
}

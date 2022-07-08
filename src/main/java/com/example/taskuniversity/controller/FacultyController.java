package com.example.taskuniversity.controller;

import com.example.taskuniversity.payload.ApiResponse;
import com.example.taskuniversity.payload.FacultyDto;
import com.example.taskuniversity.service.FacultyService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/faculty")
@RequiredArgsConstructor
public class FacultyController {

    private final FacultyService facultyService;

    @PostMapping
    public HttpEntity<?> add(@Valid @RequestBody FacultyDto facultyDto) {
        ApiResponse apiResponse = facultyService.add(facultyDto);
        return ResponseEntity
                .status(apiResponse.isSuccess() ? HttpStatus.CREATED : HttpStatus.BAD_REQUEST)
                .body(apiResponse);
    }

    @DeleteMapping("/{id}")
    public HttpEntity<?> delete(@PathVariable Long id) {
        ApiResponse apiResponse = facultyService.delete(id);
        return ResponseEntity
                .status(apiResponse.isSuccess() ? HttpStatus.OK : HttpStatus.BAD_REQUEST)
                .body(apiResponse);
    }

    @PutMapping("/{id}")
    public HttpEntity<?> edit(@PathVariable Long id, @RequestBody FacultyDto facultyDto) {
        ApiResponse apiResponse = facultyService.edit(id, facultyDto);
        return ResponseEntity
                .status(apiResponse.isSuccess() ? HttpStatus.OK : HttpStatus.BAD_REQUEST)
                .body(apiResponse);
    }

    @GetMapping("/{id}")
    public HttpEntity<?> getById(@PathVariable Long id) {
        ApiResponse apiResponse = facultyService.getById(id);
        return ResponseEntity
                .status(apiResponse.isSuccess() ? HttpStatus.OK : HttpStatus.BAD_REQUEST)
                .body(apiResponse);
    }

    // FakultetId orqali undagi guruhlar va talabalar soni
    @GetMapping("/groups/{id}")
    public HttpEntity<?> getFacultyGroups(@PathVariable Long id){
        ApiResponse apiResponse = facultyService.getFacultyGroups(id);
        return ResponseEntity
                .status(apiResponse.isSuccess() ? HttpStatus.OK : HttpStatus.BAD_REQUEST)
                .body(apiResponse);
    }
}

package com.example.taskuniversity.controller;

import com.example.taskuniversity.payload.ApiResponse;
import com.example.taskuniversity.payload.UniversityDto;
import com.example.taskuniversity.service.UniversityService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/university")
@RequiredArgsConstructor
public class UniversityController {

    private final UniversityService universityService;

    @PostMapping
    public HttpEntity<?> add(@Valid @RequestBody UniversityDto universityDto) {
        ApiResponse apiResponse = universityService.add(universityDto);
        return ResponseEntity
                .status(apiResponse.isSuccess() ? HttpStatus.CREATED : HttpStatus.BAD_REQUEST)
                .body(apiResponse);
    }

    @DeleteMapping("/{id}")
    public HttpEntity<?> delete(@PathVariable Long id) {
        ApiResponse apiResponse = universityService.delete(id);
        return ResponseEntity
                .status(apiResponse.isSuccess() ? HttpStatus.OK : HttpStatus.BAD_REQUEST)
                .body(apiResponse);
    }

    @PutMapping("/{id}")
    public HttpEntity<?> edit(@PathVariable Long id, @RequestBody UniversityDto universityDto) {
        ApiResponse apiResponse = universityService.edit(id, universityDto);
        return ResponseEntity
                .status(apiResponse.isSuccess() ? HttpStatus.OK : HttpStatus.BAD_REQUEST)
                .body(apiResponse);
    }

    @GetMapping("/{id}")
    public HttpEntity<?> getById(@PathVariable Long id) {
        ApiResponse apiResponse = universityService.getById(id);
        return ResponseEntity
                .status(apiResponse.isSuccess() ? HttpStatus.OK : HttpStatus.BAD_REQUEST)
                .body(apiResponse);
    }
}

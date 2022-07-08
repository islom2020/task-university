package com.example.taskuniversity.service;

import com.example.taskuniversity.entity.University;
import com.example.taskuniversity.exception.customException.IdNotFoundException;
import com.example.taskuniversity.payload.ApiResponse;
import com.example.taskuniversity.payload.UniversityDto;
import com.example.taskuniversity.repository.UniversityRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UniversityService {

    private final UniversityRepository universityRepository;

    public ApiResponse add(UniversityDto universityDto) {
        University university = new University();
        university.setName(universityDto.getName());
        university.setAddress(universityDto.getAddress());
        university.setOpenYear(universityDto.getOpenYear());
        universityRepository.save(university);
        return new ApiResponse(true, "University added successfully");
    }

    public ApiResponse delete(Long id) {
        boolean existsById = universityRepository.existsById(id);
        if (!existsById) throw new IdNotFoundException("University not found");
        universityRepository.deleteById(id);
        return new ApiResponse(true, "University deleted successfully");
    }

    public ApiResponse edit(Long id, UniversityDto universityDto) {
        Optional<University> optionalUniversity = universityRepository.findById(id);
        if (optionalUniversity.isEmpty()) throw new IdNotFoundException("University not found");
        University university = optionalUniversity.get();

        String name = universityDto.getName();
        if (!name.isBlank()) university.setName(name);

        String address = universityDto.getAddress();
        if (!address.isBlank()) university.setAddress(address);

        Integer openYear = universityDto.getOpenYear();
        if (openYear != null) university.setOpenYear(openYear);

        universityRepository.save(university);
        return new ApiResponse(true, "University edited successfully");
    }

    public ApiResponse getById(Long id) {
        Optional<University> optionalUniversity = universityRepository.findById(id);
        if (optionalUniversity.isEmpty())
            throw new IdNotFoundException("University not found");
        else
            return new ApiResponse(true, "University by id", optionalUniversity.get());
    }
}

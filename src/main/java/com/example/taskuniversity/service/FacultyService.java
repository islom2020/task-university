package com.example.taskuniversity.service;

import com.example.taskuniversity.entity.Faculty;
import com.example.taskuniversity.entity.Group;
import com.example.taskuniversity.entity.University;
import com.example.taskuniversity.exception.customException.IdNotFoundException;
import com.example.taskuniversity.payload.ApiResponse;
import com.example.taskuniversity.payload.FacultyDto;
import com.example.taskuniversity.payload.GroupsResponseDto;
import com.example.taskuniversity.repository.FacultyRepository;
import com.example.taskuniversity.repository.UniversityRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class FacultyService {

    private final FacultyRepository facultyRepository;
    private final UniversityRepository universityRepository;

    public ApiResponse add(FacultyDto facultyDto) {
        Long universityId = facultyDto.getUniversityId();
        Optional<University> optionalUniversity = universityRepository.findById(universityId);
        if (optionalUniversity.isEmpty()) throw new IdNotFoundException("University not found");

        University university = optionalUniversity.get();
        Faculty faculty = new Faculty();
        faculty.setName(facultyDto.getName());
        faculty.setUniversity(university);
        facultyRepository.save(faculty);
        return new ApiResponse(true, "Faculty added successfully");
    }

    public ApiResponse delete(Long id) {
        boolean existsById = facultyRepository.existsById(id);
        if (!existsById) throw new IdNotFoundException("Faculty not found");
        facultyRepository.deleteById(id);
        return new ApiResponse(true, "Faculty deleted successfully");
    }

    public ApiResponse edit(Long id, FacultyDto facultyDto) {
        Optional<Faculty> optionalFaculty = facultyRepository.findById(id);
        if (optionalFaculty.isEmpty()) throw new IdNotFoundException("Faculty not found");
        Faculty faculty = optionalFaculty.get();

        String name = facultyDto.getName();
        if (!name.isBlank()) faculty.setName(name);

        Long universityId = facultyDto.getUniversityId();
        if (universityId != null) {
            Optional<University> optionalUniversity = universityRepository.findById(universityId);
            if (optionalUniversity.isEmpty())
                throw new IdNotFoundException("University not found");
            faculty.setUniversity(optionalUniversity.get());
        }
        facultyRepository.save(faculty);
        return new ApiResponse(true, "Faculty edited successfully");
    }

    public ApiResponse getById(Long id) {
        Optional<Faculty> optionalFaculty = facultyRepository.findById(id);
        if (optionalFaculty.isEmpty())
            throw new IdNotFoundException("Faculty not found");
        else
            return new ApiResponse(true, "Faculty by id", optionalFaculty.get());
    }

    public ApiResponse getFacultyGroups(Long id) {
        Optional<Faculty> optionalFaculty = facultyRepository.findById(id);
        if (optionalFaculty.isEmpty()) throw new IdNotFoundException("Faculty not found");
        Faculty faculty = optionalFaculty.get();

        List<Group> groups = faculty.getGroups();

        List<GroupsResponseDto> groupsResponseDtoList = new ArrayList<>();
        for (Group group : groups) {
            groupsResponseDtoList
                    .add(
                            new GroupsResponseDto(group.getName(), group.getStudents().size())
                    );
        }
        return new ApiResponse(true,"Faculty groups list", groupsResponseDtoList);
    }
}

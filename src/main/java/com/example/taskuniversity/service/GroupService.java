package com.example.taskuniversity.service;

import com.example.taskuniversity.entity.Faculty;
import com.example.taskuniversity.entity.Group;
import com.example.taskuniversity.entity.Mark;
import com.example.taskuniversity.entity.Student;
import com.example.taskuniversity.exception.customException.IdNotFoundException;
import com.example.taskuniversity.payload.ApiResponse;
import com.example.taskuniversity.payload.GroupDto;
import com.example.taskuniversity.payload.StudentsGradesResponseDto;
import com.example.taskuniversity.repository.FacultyRepository;
import com.example.taskuniversity.repository.GroupRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class GroupService {

    private final GroupRepository groupRepository;
    private final FacultyRepository facultyRepository;

    public ApiResponse add(GroupDto groupDto) {
        Long facultyId = groupDto.getFacultyId();
        Optional<Faculty> optionalFaculty = facultyRepository.findById(facultyId);
        if (optionalFaculty.isEmpty()) throw new IdNotFoundException("Faculty not found");

        Faculty faculty = optionalFaculty.get();
        Group group = new Group();
        group.setName(groupDto.getName());
        group.setFaculty(faculty);
        groupRepository.save(group);
        return new ApiResponse(true, "Faculty added successfully");
    }

    public ApiResponse delete(Long id) {
        boolean existsById = groupRepository.existsById(id);
        if (!existsById) throw new IdNotFoundException("Group not found");
        groupRepository.deleteById(id);
        return new ApiResponse(true, "Group deleted successfully");
    }

    public ApiResponse edit(Long id, GroupDto groupDto) {
        Optional<Group> optionalGroup = groupRepository.findById(id);
        if (optionalGroup.isEmpty()) throw new IdNotFoundException("Group not found");
        Group group = optionalGroup.get();

        String name = groupDto.getName();
        if (!name.isBlank()) group.setName(name);

        Long facultyId = groupDto.getFacultyId();
        if (facultyId != null) {
            Optional<Faculty> optionalFaculty = facultyRepository.findById(facultyId);
            if (optionalFaculty.isEmpty()) throw new IdNotFoundException("Faculty not found");
            group.setFaculty(optionalFaculty.get());
        }
        groupRepository.save(group);

        return new ApiResponse(true, "Group edited successfully");
    }

    public ApiResponse getById(Long id) {
        Optional<Group> optionalGroup = groupRepository.findById(id);
        if (optionalGroup.isEmpty())
            throw new IdNotFoundException("Group not found");
        else
            return new ApiResponse(true, "Group by id", optionalGroup.get());
    }

    public ApiResponse getStudentsWithGrades(Long id) {
        Optional<Group> optionalGroup = groupRepository.findById(id);
        if (optionalGroup.isEmpty()) throw new IdNotFoundException("Group not found");
        Group group = optionalGroup.get();
        List<StudentsGradesResponseDto> students = new ArrayList<>();
        for (Student student : group.getStudents()) {
            students.add(
                    new StudentsGradesResponseDto(
                            student.getName(),
                            student.getMarkList().stream()
                                    .mapToDouble(Mark::getGrade)
                                    .average()
                                    .stream()
                                    .sum()
                    )
            );
        }
        List<StudentsGradesResponseDto> collect = students
                .stream()
                .sorted(
                        Comparator.comparing(StudentsGradesResponseDto::getAverageGrade).reversed()
                ).toList();

        return new ApiResponse(true, "Students of group with average grades", collect);
    }
}

package com.example.taskuniversity.service;

import com.example.taskuniversity.entity.*;
import com.example.taskuniversity.exception.customException.IdNotFoundException;
import com.example.taskuniversity.payload.ApiResponse;
import com.example.taskuniversity.payload.StudentAllDetailsResponse;
import com.example.taskuniversity.payload.StudentDto;
import com.example.taskuniversity.repository.GroupRepository;
import com.example.taskuniversity.repository.StudentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class StudentService {

    private final StudentRepository studentRepository;
    private final GroupRepository groupRepository;

    public ApiResponse add(StudentDto studentDto) {
        Long groupId = studentDto.getGroupId();
        Optional<Group> optionalGroup = groupRepository.findById(groupId);
        if (optionalGroup.isEmpty()) throw new IdNotFoundException("Group not found");

        Group group = optionalGroup.get();
        Student student = new Student();
        student.setName(studentDto.getName());
        student.setGroup(group);
        studentRepository.save(student);
        return new ApiResponse(true, "Student successfully added");
    }

    public ApiResponse delete(Long id) {
        boolean existsById = studentRepository.existsById(id);
        if (!existsById) throw new IdNotFoundException("Student not found");
        studentRepository.deleteById(id);
        return new ApiResponse(true, "Student deleted successfully");
    }

    public ApiResponse edit(Long id, StudentDto studentDto) {
        Optional<Student> optionalStudent = studentRepository.findById(id);
        if (optionalStudent.isEmpty()) throw new IdNotFoundException("Student not found");
        Student student = optionalStudent.get();

        String name = studentDto.getName();
        if (!name.isBlank()) student.setName(name);

        Long groupId = studentDto.getGroupId();
        if (groupId != null) {
            Optional<Group> optionalGroup = groupRepository.findById(id);
            if (optionalGroup.isEmpty())
                throw new IdNotFoundException("Group not found");
            student.setGroup(optionalGroup.get());
        }
        studentRepository.save(student);

        return new ApiResponse(true, "Student edited successfully");
    }

    public ApiResponse getById(Long id) {
        Optional<Student> optionalStudent = studentRepository.findById(id);
        if (optionalStudent.isEmpty())
            throw new IdNotFoundException("Student not found");
        else
            return new ApiResponse(true, "Student by id", optionalStudent.get());
    }

    public ApiResponse getByGroupId(Long groupId) {
        boolean existsById = groupRepository.existsById(groupId);
        if (!existsById) throw new IdNotFoundException("Group not found");

        List<Student> allByGroupId = studentRepository.findAllByGroup_Id(groupId);
        return new ApiResponse(true, "Students by group id", allByGroupId);
    }

    public ApiResponse findStudentByName(String name) {
        List<Student> students = studentRepository.findAllByNameContainingIgnoreCase(name);
        if (students.isEmpty()) return new ApiResponse(false, "Student not found");

        List<StudentAllDetailsResponse> responses = new ArrayList<>();

        for (Student student : students) {
            StudentAllDetailsResponse response = new StudentAllDetailsResponse();
            response.setName(student.getName());

            Group group = student.getGroup();
            response.setGroup(group.getName());

            Faculty faculty = group.getFaculty();
            response.setFaculty(faculty.getName());

            University university = faculty.getUniversity();
            response.setUniversity(university.getName());
            responses.add(response);
        }

        return new ApiResponse(true, "All students by name like that", responses);
    }

    public ApiResponse getStudentSubjects(Long id) {
        Optional<Student> optionalStudent = studentRepository.findById(id);
        if (optionalStudent.isEmpty()) throw new IdNotFoundException("Student not found");
        Student student = optionalStudent.get();

        Group group = student.getGroup();
        Journal journal = group.getJournal();
        Set<Subject> subjects = journal.getSubjects();

        return new ApiResponse(true, "Student's subjects", subjects);
    }
}

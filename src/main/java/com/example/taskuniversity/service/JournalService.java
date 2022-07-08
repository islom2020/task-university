package com.example.taskuniversity.service;

import com.example.taskuniversity.entity.Group;
import com.example.taskuniversity.entity.Journal;
import com.example.taskuniversity.entity.Subject;
import com.example.taskuniversity.exception.customException.IdNotFoundException;
import com.example.taskuniversity.payload.ApiResponse;
import com.example.taskuniversity.payload.JournalDto;
import com.example.taskuniversity.repository.GroupRepository;
import com.example.taskuniversity.repository.JournalRepository;
import com.example.taskuniversity.repository.SubjectRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
public class JournalService {
    private final JournalRepository journalRepository;
    private final GroupRepository groupRepository;
    private final SubjectRepository subjectRepository;

    public ApiResponse add(JournalDto journalDto) {
        Long groupId = journalDto.getGroupId();
        Optional<Group> optionalGroup = groupRepository.findById(groupId);
        if (optionalGroup.isEmpty()) throw new IdNotFoundException("Group not found");
        Group group = optionalGroup.get();

        Journal journal = new Journal();
        journal.setGroup(group);
        journal.setName(journalDto.getName());

        Set<Subject> subjectsFromIds = getSubjectsFromIds(journalDto.getSubjects());
        if (subjectsFromIds == null) return new ApiResponse(false,"subjects id not valid");

        journal.setSubjects(subjectsFromIds);
        journalRepository.save(journal);
        return new ApiResponse(true, "Journal added successfully");
    }

    public ApiResponse delete(Long id) {
        boolean existsById = journalRepository.existsById(id);
        if (!existsById) throw new IdNotFoundException("Journal not found");
        journalRepository.deleteById(id);
        return new ApiResponse(true, "Journal deleted successfully");
    }

    public ApiResponse edit(Long id, JournalDto journalDto) {
        Optional<Journal> optionalJournal = journalRepository.findById(id);
        if (optionalJournal.isEmpty()) throw new IdNotFoundException("Journal not found");
        Journal journal = optionalJournal.get();

        String name = journalDto.getName();
        if (!name.isBlank()) journal.setName(name);

        Long groupId = journalDto.getGroupId();
        if (groupId != null){
            Optional<Group> optionalGroup = groupRepository.findById(groupId);
            if (optionalGroup.isEmpty())
                throw new IdNotFoundException("Group not found");
            journal.setGroup(optionalGroup.get());
        }

        List<Long> subjects = journalDto.getSubjects();
        if (!subjects.isEmpty()) {
            Set<Subject> subjectsFromIds = getSubjectsFromIds(subjects);
            if (subjectsFromIds == null) return new ApiResponse(false, "Subjects id not valid");
            journal.setSubjects(subjectsFromIds);
        }

        journalRepository.save(journal);
        return new ApiResponse(true, "Journal edited successfully");
    }

    public ApiResponse getById(Long id) {
        Optional<Journal> optionalJournal = journalRepository.findById(id);
        if (optionalJournal.isEmpty())
            throw new IdNotFoundException("Journal not found");
        else
            return new ApiResponse(true, "Journal by id", optionalJournal.get());
    }

    private Set<Subject> getSubjectsFromIds(List<Long> idList){
        Set<Subject> subjectList = new HashSet<>();
        for(Long id : idList){
            Optional<Subject> optionalSubject = subjectRepository.findById(id);
            if(optionalSubject.isEmpty()) return null;
            subjectList.add(optionalSubject.get());
        }
        return subjectList;
    }
}

package com.example.taskuniversity.repository;

import com.example.taskuniversity.entity.Mark;
import com.example.taskuniversity.entity.Student;
import com.example.taskuniversity.entity.Subject;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MarkRepository extends JpaRepository<Mark,Long> {
    boolean existsByStudentAndSubject(Student student, Subject subject);
}

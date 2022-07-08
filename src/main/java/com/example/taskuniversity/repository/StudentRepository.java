package com.example.taskuniversity.repository;

import com.example.taskuniversity.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StudentRepository extends JpaRepository<Student,Long> {

    List<Student> findAllByGroup_Id(Long group_id);
    List<Student> findAllByNameContainingIgnoreCase(String name);

}

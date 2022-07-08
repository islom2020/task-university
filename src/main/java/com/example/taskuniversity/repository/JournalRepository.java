package com.example.taskuniversity.repository;

import com.example.taskuniversity.entity.Journal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JournalRepository extends JpaRepository<Journal,Long> {
}

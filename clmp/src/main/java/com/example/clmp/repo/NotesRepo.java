package com.example.clmp.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.clmp.entity.Notes;

@Repository
public interface NotesRepo extends JpaRepository<Notes, Long>{
    
}

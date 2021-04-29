package com.example.daytwo.repo;

import com.example.daytwo.domain.EntryTag;

import org.springframework.data.jpa.repository.JpaRepository;

public interface EntryTagRepository extends JpaRepository<EntryTag, String> {
    
}

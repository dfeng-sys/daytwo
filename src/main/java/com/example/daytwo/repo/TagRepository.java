package com.example.daytwo.repo;

import com.example.daytwo.domain.Tag;

import org.springframework.data.jpa.repository.JpaRepository;

public interface TagRepository extends JpaRepository<Tag, String> {
    
}

package com.example.daytwo.repo;

import java.util.List;

import com.example.daytwo.domain.Tag;

import org.springframework.data.jpa.repository.JpaRepository;

public interface TagRepository extends JpaRepository<Tag, Integer> {

    public List<Tag> findByTagNameIs(String tagName);
    
}

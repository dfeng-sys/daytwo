package com.example.daytwo.repo;

import java.util.List;

import com.example.daytwo.domain.Entry;

import org.springframework.data.jpa.repository.JpaRepository;

public interface EntryRepository extends JpaRepository<Entry, String> {
    
    List<Entry> findByTagStringContaining(String tag);
}

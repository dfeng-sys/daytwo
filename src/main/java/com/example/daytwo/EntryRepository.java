package com.example.daytwo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface EntryRepository extends JpaRepository<Entry, String> {
    
    List<Entry> findByTagStringContaining(String tag);
}

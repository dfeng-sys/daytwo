package com.example.daytwo.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "entrytags")
@Data
@AllArgsConstructor
@NoArgsConstructor

public class EntryTag {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;

    String entryId;
    
    int tagId;

    String tagName;
}

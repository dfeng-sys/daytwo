package com.example.daytwo;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/day2")
public class DayTwoController {

    @Autowired
    DayTwoService journal;
    
    @PostMapping("/replaceTag")
    public ResponseEntity<List<Entry>> replaceTag(@RequestBody DayTwoForm form) {
        return ResponseEntity.ok(
            journal.replaceTag(
                form.getTagToReplace(),
                form.getTagToReplaceWith()
            )
        );
    }

    @GetMapping("/tagged/{tag}")
    public ResponseEntity<List<Entry>> getTagged(@PathVariable(value = "tag") String tag) {
        return ResponseEntity.ok(journal.getTagged(tag));
    }

    @PostMapping("/new")
    public ResponseEntity<Entry> newEntry(@RequestBody Entry requestEntry) {
        return ResponseEntity.ok(journal.save(requestEntry));
    }

    @PostMapping("/news")
    public ResponseEntity<List<Entry>> newEntries(@RequestBody List<Entry> requestEntries) {
        return ResponseEntity.ok(journal.saveAll(requestEntries));
    }
}

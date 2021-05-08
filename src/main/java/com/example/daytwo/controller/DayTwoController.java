package com.example.daytwo.controller;

import java.util.Arrays;
import java.util.List;
import java.util.Set;

import com.example.daytwo.DayTwoForm;
import com.example.daytwo.domain.Entry;
import com.example.daytwo.service.DayTwoService;

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
    
    /**
     * renames a tag, updating all entries with that tag with new tag name
     */
    @PostMapping("/replaceTag")
    public ResponseEntity<List<Entry>> replaceTag(@RequestBody DayTwoForm.TagReplace form) {
        return ResponseEntity.ok(
            journal.replaceTag(
                form.getTagToReplace(),
                form.getTagToReplaceWith()
            )
        );
    }

    /**
     * get all entries with the given tag
     */
    @GetMapping("/tagged/{tag}")
    public ResponseEntity<List<Entry>> getTagged(@PathVariable(value = "tag") String tag) {
        return ResponseEntity.ok(journal.getTagged(tag));
    }

    @GetMapping("/getAllTags")
    public ResponseEntity<Set<String>> getAllTags() {
        return ResponseEntity.ok(journal.getAllTags());
    }

    /**
     * save only those entries with one or more of the given tags
     */
    @PostMapping("/saveTagged")
    public ResponseEntity<List<Entry>> saveTagged(@RequestBody DayTwoForm form) {
        return ResponseEntity.ok(journal.saveTagged(Arrays.asList(form.getEntries()), Arrays.asList(form.getTags())));
    }

    /**
     * saves a new entry
     */
    @PostMapping("/new")
    public ResponseEntity<Entry> newEntry(@RequestBody Entry requestEntry) {
        return ResponseEntity.ok(journal.save(requestEntry));
    }

    /**
     * saves new entries
     */
    @PostMapping("/news")
    public ResponseEntity<List<Entry>> newEntries(@RequestBody List<Entry> requestEntries) {
        return ResponseEntity.ok(journal.saveAll(requestEntries));
    }

    /**
     * updates an entry specified by the given uuid with new information
     */
    @PostMapping("/updateEntry/{uuid}")
    public ResponseEntity<Entry> updateEntry(@PathVariable(value="uuid") String uuid, @RequestBody Entry requestEntry) {
        return ResponseEntity.ok(journal.update(uuid, requestEntry));
    }
}

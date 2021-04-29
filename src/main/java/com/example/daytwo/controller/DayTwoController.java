package com.example.daytwo.controller;

import java.util.Arrays;
import java.util.List;

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

    @PostMapping("/saveTagged")
    public ResponseEntity<List<Entry>> saveTagged(@RequestBody DayTwoForm form) {
        return ResponseEntity.ok(journal.saveTagged(Arrays.asList(form.getEntries()), Arrays.asList(form.getTags())));
    }

    @PostMapping("/new")
    public ResponseEntity<Entry> newEntry(@RequestBody Entry requestEntry) {
        return ResponseEntity.ok(journal.save(requestEntry));
    }

    @PostMapping("/news")
    public ResponseEntity<List<Entry>> newEntries(@RequestBody List<Entry> requestEntries) {
        return ResponseEntity.ok(journal.saveAll(requestEntries));
    }

    @PostMapping("/updateEntry/{uuid}")
    public ResponseEntity<Entry> updateEntry(@PathVariable(value="uuid") String uuid, @RequestBody Entry requestEntry) {
        return ResponseEntity.ok(journal.update(uuid, requestEntry));
    }

    @PostMapping("/v2/new")
    public ResponseEntity<Entry> newEntryV2(@RequestBody Entry requestEntry) {
        return ResponseEntity.ok(journal.save(requestEntry));
    }
}

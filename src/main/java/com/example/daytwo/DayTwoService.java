package com.example.daytwo;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DayTwoService {

    @Autowired
    private EntryRepository repo;

    public Entry save(Entry e) {
        return repo.save(e.stringifyTags()).listifyTags();
    }

    public List<Entry> saveAll(List<Entry> es) {
        return repo.saveAll(es);
    }

    public Entry get(String uuid) {
        return repo.findById(uuid).get();
    }

    public void delete(String uuid) {
        repo.deleteById(uuid);
    }

    public List<Entry> getTagged(String tag) {
        // Entry entryExample = new Entry();
        // entryExample.setTagString(tag);
        // Example<Entry> example = Example.of(
        //     entryExample,
        //     ExampleMatcher.matchingAll().withMatcher(
        //         "tagString",
        //         ExampleMatcher.GenericPropertyMatchers.contains().caseSensitive()
        //     )
        // );
        // List<Entry> entries = repo.findAll(example);
        List<Entry> entries = repo.findByTagStringContaining(tag);
        for (Entry entry : entries) entry.listifyTags();
        return entries;
    }

    public List<Entry> replaceTag(String tag, String with) {
        List<Entry> taggedEntries = getTagged(tag);
        for (Entry entry : taggedEntries) {
            if (entry.replaceTag(tag, with)) {
                entry.stringifyTags();
                repo.save(entry);
            }
        }
        return taggedEntries;
    }
}

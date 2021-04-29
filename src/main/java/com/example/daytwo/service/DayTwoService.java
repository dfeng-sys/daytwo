package com.example.daytwo.service;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import com.example.daytwo.domain.Entry;
import com.example.daytwo.repo.EntryRepository;

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
        for (Entry entry : es) entry.stringifyTags();
        return repo.saveAll(es);
    }

    public List<Entry> saveTagged(List<Entry> es, List<String> tags) {
        int r = 0;
        List<Entry> taggedEntries = new ArrayList<>(es);
        for (Entry e : es) {
            for (String tag : tags) {
                if (!e.getTags().contains(tag)) {
                    r++;
                    taggedEntries.remove(e);
                    break;
                }
            }
        }
        System.out.println("skipped " + r + " entries from request");
        for (Entry entry : taggedEntries) entry.stringifyTags();
        return repo.saveAll(taggedEntries);
    }

    public Entry update(String uuid, Entry e) {
        try {
            Entry d = repo.findById(uuid).get();
            e.setUuid(d.getUuid());
            return repo.save(e.stringifyTags()).listifyTags();
        } catch(NoSuchElementException exception) {
            return null;
        }
    }

    public Entry get(String uuid) {
        // need to listify tags
        return repo.findById(uuid).orElseThrow();
    }

    public void delete(String uuid) {
        repo.deleteById(uuid);
    }

    public List<Entry> getTagged(String tag) {
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
        // need to listify tags
        return taggedEntries;
    }
}

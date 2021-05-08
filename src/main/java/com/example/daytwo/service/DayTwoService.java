package com.example.daytwo.service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Set;

import com.example.daytwo.domain.Entry;
import com.example.daytwo.repo.EntryRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DayTwoService {

    @Autowired
    private EntryRepository repo;

    // keep running set of tags
    Set<String> allTags;

    Map<String, String> tagEntryMap;

    public Entry save(Entry e) {
        // need to add any new tags the entry may have to the runtime set
        allTags.addAll(e.getTags());
        return repo.save(e.stringifyTags()).listifyTags();
    }

    public List<Entry> saveAll(List<Entry> es) {
        for(Entry e : es) allTags.addAll(e.getTags());
        return repo.saveAll(Entry.stringifyTags(es));
    }

    public List<Entry> saveTagged(List<Entry> es, List<String> tags) {
        int r = 0;
        int n = 0;
        List<Entry> taggedEntries = new ArrayList<>(es);
        for (Entry e : es) {
            for (String tag : tags) {
                List<String> eTags = e.getTags();
                // entry has no tags
                if (eTags == null) {
                    n++;
                    taggedEntries.remove(e);
                    break;
                }
                // entry does not contain any tag in tags
                if (!eTags.contains(tag)) {
                    r++;
                    taggedEntries.remove(e);
                    break;
                }
                // if there are entries to be added,
                // add any new tags they may have to the runtime set
                allTags.addAll(eTags);
                System.out.println("processing entry " + e.getUuid());
            }
        }
        System.out.println("skipped " + r + " + " + n + " entries from request");
        System.out.println("processing " + taggedEntries.size()+ " of them");
        // for (Entry entry : taggedEntries) {
        //     System.out.println("processing entry " + entry.getUuid());
        //     entry.stringifyTags();
        // }
        return saveAll(taggedEntries);
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
        return repo.findById(uuid).orElseThrow().listifyTags();
    }

    public void delete(String uuid) {
        repo.deleteById(uuid);
    }

    public Set<String> getAllTags() {
        if (allTags == null) {
            allTags = new HashSet<>();
            List<Entry> entries = repo.findAll();
            for(Entry entry : entries) {
                allTags.addAll(entry.listifyTags().getTags());
            }
        }
        return allTags;
    }

    public List<Entry> getTagged(String tag) {
        List<Entry> entries = repo.findByTagStringContaining("|" + tag + "|");
        entries.addAll(repo.findByTagStringStartingWith(tag + "|"));
        entries.addAll(repo.findByTagStringEndingWith("|" + tag));
        return Entry.listifyTags(entries);
    }

    public List<Entry> replaceTag(String tag, String with) {
        List<Entry> taggedEntries = getTagged(tag);
        for (Entry entry : taggedEntries) {
            if (entry.replaceTag(tag, with)) {
                entry.stringifyTags();
                repo.save(entry);
            }
        }
        if (allTags.remove(tag)) allTags.add(with);
        return Entry.listifyTags(taggedEntries);
    }
}

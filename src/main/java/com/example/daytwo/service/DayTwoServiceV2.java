package com.example.daytwo.service;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import com.example.daytwo.domain.Entry;
import com.example.daytwo.domain.EntryTag;
import com.example.daytwo.domain.Tag;
import com.example.daytwo.repo.EntryRepository;
import com.example.daytwo.repo.EntryTagRepository;
import com.example.daytwo.repo.TagRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DayTwoServiceV2 {

    @Autowired
    private EntryRepository entryRepo;

    @Autowired
    private TagRepository tagRepo;

    @Autowired
    private EntryTagRepository entryTagRepo;

    public Entry save(Entry e) {
        saveTags(e);
        saveEntryTags(e);
        return entryRepo.save(e);
    }

    private void saveTags(Entry e) {
        for (String tagString : e.getTags()) {
            Tag tag = new Tag();
            tag.setTagName(tagString);
            tagRepo.save(tag);
        }
    }

    private void saveEntryTags(Entry e) {
        for (String tagString : e.getTags()) {
            List<Tag> tags = tagRepo.findByTagNameIs(tagString);
            Tag tag = tags.get(0);
            EntryTag entryTag = new EntryTag();
            entryTag.setEntryId(e.getUuid());
            entryTag.setTagId(tag.getTagId());
            entryTag.setTagName(tagString);
            entryTagRepo.save(entryTag);
        }
    }

    public List<Entry> saveAll(List<Entry> es) {
        for (Entry entry : es) {
            saveTags(entry);
            saveEntryTags(entry);
        }
        return entryRepo.saveAll(es);
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
        return entryRepo.saveAll(taggedEntries);
    }

    public Entry update(String uuid, Entry e) {
        try {
            Entry d = entryRepo.findById(uuid).get();
            e.setUuid(d.getUuid());
            return entryRepo.save(e.stringifyTags()).listifyTags();
        } catch(NoSuchElementException exception) {
            return null;
        }
    }

    public Entry get(String uuid) {
        return entryRepo.findById(uuid).orElseThrow();
    }

    public void delete(String uuid) {
        entryRepo.deleteById(uuid);
    }

    public List<Entry> renameTag(String tag) {
        return null;
    }
}

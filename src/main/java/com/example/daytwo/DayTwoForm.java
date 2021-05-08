package com.example.daytwo;

import com.example.daytwo.domain.Entry;

import lombok.Data;

@Data
public class DayTwoForm {

    Entry[] entries;
    String[] tags;

    @Data
    public static class TagReplace {
        String tagToReplace;
        String tagToReplaceWith;
    }
}

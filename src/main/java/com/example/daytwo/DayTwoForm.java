package com.example.daytwo;

import com.example.daytwo.domain.Entry;

import lombok.Data;

@Data
public class DayTwoForm {
    String tagToReplace;
    String tagToReplaceWith;
    Entry[] entries;
    String[] tags;
}

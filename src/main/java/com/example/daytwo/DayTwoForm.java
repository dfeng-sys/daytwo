package com.example.daytwo;

import lombok.Data;

@Data
public class DayTwoForm {
    String tagToReplace;
    String tagToReplaceWith;
    Entry[] entries;
    String[] tags;
}

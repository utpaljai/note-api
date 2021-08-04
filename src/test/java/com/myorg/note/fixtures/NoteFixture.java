package com.myorg.note.fixtures;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.myorg.note.entity.Note;

public class NoteFixture {

    public static Note getNote1() {
        Note note = new Note();
        note.setNoteId(1);
        note.setNoteText("note 1");
        note.setCreatedBy("created_by");
        note.setModifiedBy("updated_by");

        return note;
    }

    public static Note getNote2() {
        Note note = new Note();
        note.setNoteId(2);
        note.setNoteText("note 2");
        note.setCreatedBy("created_by");
        note.setModifiedBy("updated_by");

        return note;
    }

    public static List<Note> getMultipleNotes() {
        return Stream.of(getNote1(), getNote2()).collect(Collectors.toList());
    }

    public static List<Note> getNotes() {
        return Stream.of(getNote1()).collect(Collectors.toList());
    }
}

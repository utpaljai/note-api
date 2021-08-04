package com.myorg.note.fixtures;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.myorg.note.models.NoteResponse;

public class NoteResponseFixture {

    public static NoteResponse getNewNoteResponse() {
        NoteResponse noteResponse = new NoteResponse();
        noteResponse.setNoteId(1);
        noteResponse.setNoteText("note 1");
        noteResponse.setCreatedByUser("created_user");
        noteResponse.setUpdatedByUser("created_user");
        return noteResponse;
    }

    public static NoteResponse getUpdatedNoteResponse() {
        NoteResponse noteResponse = new NoteResponse();
        noteResponse.setNoteId(1);
        noteResponse.setNoteText("note updated");
        noteResponse.setCreatedByUser("created_user");
        noteResponse.setUpdatedByUser("updated_user");
        return noteResponse;
    }

    public static List<NoteResponse> getNoteResponseList() {
        return Stream.of(getNewNoteResponse()).collect(Collectors.toList());
    }
}

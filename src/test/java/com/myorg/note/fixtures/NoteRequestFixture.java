package com.myorg.note.fixtures;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.myorg.note.models.NoteRequest;

public class NoteRequestFixture {

    public static NoteRequest getNoteRequest() {
        NoteRequest noteRequest = new NoteRequest();
        noteRequest.setNoteId(1);
        noteRequest.setNoteText("note 1");

        return noteRequest;
    }

    public static NoteRequest getUpdatedNoteRequest() {
        NoteRequest noteRequest = new NoteRequest();
        noteRequest.setNoteId(1);
        noteRequest.setNoteText("note updated");

        return noteRequest;
    }

    public static List<NoteRequest> getNoteRequestList() {
        return Stream.of(getNoteRequest()).collect(Collectors.toList());
    }
}

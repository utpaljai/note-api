package com.myorg.note.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.myorg.note.models.NoteRequest;
import com.myorg.note.models.NoteResponse;
import com.myorg.note.service.NoteService;

@RestController
@RequestMapping("/notes")
public class NoteController {
    private NoteService noteService;

    @Autowired
    public NoteController(final NoteService noteService) {
        this.noteService = noteService;
    }

    @PostMapping()
    public ResponseEntity<List<NoteResponse>> createNotes(@RequestBody List<NoteRequest> noteRequests,
            @RequestHeader("user") String user) {
        List<NoteResponse> noteResponseList = noteService.createNotes(noteRequests, user);
        return ResponseEntity.ok().body(noteResponseList);
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<NoteResponse> updateNoteById(@PathVariable("id") Integer noteId,
            @RequestBody NoteRequest noteRequest, @RequestHeader("user") String user) {
        NoteResponse noteResponse = noteService.updateNoteByNoteId(noteId, noteRequest, user);
        return ResponseEntity.ok().body(noteResponse);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> deleteNoteById(@PathVariable("id") Integer noteId) {
        noteService.deleteNoteByNoteId(noteId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping()
    public ResponseEntity<List<NoteResponse>> deleteNotes(@RequestParam("ids") List<Integer> noteIds) {
        noteService.deleteNotes(noteIds);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping()
    public ResponseEntity<List<NoteResponse>> getAllNotes() {
        List<NoteResponse> noteResponseList = noteService.getAllNotes();
        return ResponseEntity.ok().body(noteResponseList);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<NoteResponse> getNoteByNoteId(@PathVariable("id") Integer noteId) {
        NoteResponse noteResponse = noteService.getNoteByNoteId(noteId);
        return ResponseEntity.ok().body(noteResponse);
    }

    @GetMapping(params = "createdBy")
    public ResponseEntity<List<NoteResponse>> getNotesByCreatedBy(@RequestParam("createdBy") String user) {
        List<NoteResponse> noteResponseList = noteService.getAllNotesByCreatedUser(user);
        return ResponseEntity.ok().body(noteResponseList);
    }

    @GetMapping(params = "updatedBy")
    public ResponseEntity<List<NoteResponse>> getNotesByUpdatedBy(@RequestParam("updatedBy") String user) {
        List<NoteResponse> noteResponseList = noteService.getAllNotesByUpdatedUser(user);
        return ResponseEntity.ok().body(noteResponseList);
    }

}

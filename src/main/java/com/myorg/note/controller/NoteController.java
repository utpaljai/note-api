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

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.media.Schema;

@RestController
@RequestMapping("/notes")
public class NoteController {
    private NoteService noteService;

    @Autowired
    public NoteController(final NoteService noteService) {
        this.noteService = noteService;
    }

    @Operation(summary = "Create one or more notes")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Created notes successfully", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = NoteRequest.class)) }),
            @ApiResponse(responseCode = "400", description = "Invalid Request", content = @Content) })
    @PostMapping()
    public ResponseEntity<List<NoteResponse>> createNotes(@RequestBody List<NoteRequest> noteRequests,
            @RequestHeader("user") String user) {
        List<NoteResponse> noteResponseList = noteService.createNotes(noteRequests, user);
        return ResponseEntity.ok().body(noteResponseList);
    }

    @Operation(summary = "Update a note by note id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Updated note successfully", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = NoteRequest.class)) }),
            @ApiResponse(responseCode = "400", description = "Invalid Request", content = @Content),
            @ApiResponse(responseCode = "404", description = "Note not found", content = @Content) })
    @PutMapping(value = "/{id}")
    public ResponseEntity<NoteResponse> updateNoteById(@PathVariable("id") Integer noteId,
            @RequestBody NoteRequest noteRequest, @RequestHeader("user") String user) {
        NoteResponse noteResponse = noteService.updateNoteByNoteId(noteId, noteRequest, user);
        return ResponseEntity.ok().body(noteResponse);
    }

    @Operation(summary = "Delete a note by note id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Note deleted successfully", content = {
                    @Content(mediaType = "application/json") }),
            @ApiResponse(responseCode = "400", description = "Invalid Request", content = @Content) })
    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> deleteNoteById(@PathVariable("id") Integer noteId) {
        noteService.deleteNoteByNoteId(noteId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @Operation(summary = "Delete one or more notes. Provide comma seperated note Ids to delete")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Note deleted successfully", content = {
                    @Content(mediaType = "application/json") }),
            @ApiResponse(responseCode = "400", description = "Invalid Request", content = @Content) })
    @DeleteMapping()
    public ResponseEntity<List<NoteResponse>> deleteNotes(@RequestParam("ids") List<Integer> noteIds) {
        noteService.deleteNotes(noteIds);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @Operation(summary = "Get all notes")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "List of notes retrieved successfully", content = {
                    @Content(mediaType = "application/json") }),
            @ApiResponse(responseCode = "400", description = "Invalid Request", content = @Content) })
    @GetMapping()
    public ResponseEntity<List<NoteResponse>> getAllNotes() {
        List<NoteResponse> noteResponseList = noteService.getAllNotes();
        return ResponseEntity.ok().body(noteResponseList);
    }

    @Operation(summary = "Get note by note id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Note retrieved successfully", content = {
                    @Content(mediaType = "application/json") }),
            @ApiResponse(responseCode = "404", description = "Note not found", content = @Content) })
    @GetMapping(value = "/{id}")
    public ResponseEntity<NoteResponse> getNoteByNoteId(@PathVariable("id") Integer noteId) {
        NoteResponse noteResponse = noteService.getNoteByNoteId(noteId);
        return ResponseEntity.ok().body(noteResponse);
    }

    @Operation(summary = "Search notes by note text and/or created/updated by user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Note retrieved successfully", content = {
                    @Content(mediaType = "application/json") }),
            @ApiResponse(responseCode = "400", description = "Bad Request", content = @Content) })
    @GetMapping(value = "/search")
    public ResponseEntity<List<NoteResponse>> searchNotes(
            @RequestParam(value = "createdBy", required = true) String createdBy,
            @RequestParam(value = "updatedBy", required = true) String updatedBy,
            @RequestParam(value = "text", required = false) String text) {
        List<NoteResponse> noteResponseList = noteService.searchNotes(createdBy, updatedBy, text);
        return ResponseEntity.ok().body(noteResponseList);
    }

}

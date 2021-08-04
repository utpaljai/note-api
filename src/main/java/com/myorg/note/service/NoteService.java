package com.myorg.note.service;

import java.util.List;

import com.myorg.note.models.NoteRequest;
import com.myorg.note.models.NoteResponse;

public interface NoteService {
    /**
     * Creates one or more notes
     * 
     * @param noteRequests
     *            list of note requests
     * @param user
     *            logged in user(passed in request header). Ideally, should be passed as JWT token
     * 
     * @return
     */
    List<NoteResponse> createNotes(List<NoteRequest> noteRequests, String user);

    /**
     * Returns all notes
     * 
     * @return List of NoteResponse
     */
    List<NoteResponse> getAllNotes();

    /**
     * Search notes by created by or updated by or note text
     * 
     * @param createdBy
     * @param updatedBy
     * @param text
     * 
     * @return List of NoteResponse
     */
    List<NoteResponse> searchNotes(String createdBy, String updatedBy, String text);

    /**
     * Deletes one or more notes.
     * 
     * @param noteIds
     *            List of note ids to delete
     */
    void deleteNotes(List<Integer> noteIds);

    /**
     * Deletes note by note id
     * 
     * @param noteId
     *            Note Id to delete
     */
    void deleteNoteByNoteId(Integer noteId);

    /**
     * Returns note by given note id
     * 
     * @param noteId
     *            Note id
     * 
     * @return
     */
    NoteResponse getNoteByNoteId(Integer noteId);

    /**
     * Edits a given note by note id
     * 
     * @param noteId
     *            Note Id to update(in this case, we can only update note text)
     * @param noteRequest
     *            Updated note request
     * @param user
     *            logged in user passed in request header
     * 
     * @return updated note response
     */
    NoteResponse updateNoteByNoteId(Integer noteId, NoteRequest noteRequest, String user);

}

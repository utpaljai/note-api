package com.myorg.note.service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.myorg.note.converter.NoteRequestToNoteConverter;
import com.myorg.note.converter.NoteToNoteResponseConverter;
import com.myorg.note.entity.Note;
import com.myorg.note.exception.NoteNotFoundException;
import com.myorg.note.models.NoteRequest;
import com.myorg.note.models.NoteResponse;
import com.myorg.note.repository.NoteRepository;

@Service
public class NoteServiceImpl implements NoteService {

    private NoteRepository noteRepository;

    private NoteRequestToNoteConverter noteRequestToNoteConverter;

    private NoteToNoteResponseConverter noteToNoteResponseConverter;

    @Autowired
    public NoteServiceImpl(final NoteRepository noteRepository,
            final NoteRequestToNoteConverter noteRequestToNoteConverter,
            final NoteToNoteResponseConverter noteToNoteResponseConverter) {
        this.noteRepository = noteRepository;
        this.noteRequestToNoteConverter = noteRequestToNoteConverter;
        this.noteToNoteResponseConverter = noteToNoteResponseConverter;
    }

    @Override
    @Transactional
    public List<NoteResponse> createNotes(List<NoteRequest> noteRequests, String user) {
        List<Note> newNotes = new ArrayList<>();
        noteRequests.forEach(noteRequest -> {
            newNotes.add(noteRequestToNoteConverter.convert(noteRequest, null, user));
        });

        List<Note> persistedNotes = noteRepository.saveAll(newNotes);

        return persistedNotes.stream().map(noteToNoteResponseConverter::convert).collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<NoteResponse> getAllNotes() {
        return noteRepository.findAll().stream().map(noteToNoteResponseConverter::convert).collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<NoteResponse> getAllNotesByCreatedUser(String user) {
        return noteRepository.findByCreatedBy(user).stream().map(noteToNoteResponseConverter::convert)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<NoteResponse> getAllNotesByUpdatedUser(String user) {
        return noteRepository.findByModifiedBy(user).stream().map(noteToNoteResponseConverter::convert)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public void deleteNotes(List<Integer> noteIds) {
        noteRepository.deleteAll(noteRepository.findAllById(noteIds));
    }

    @Override
    @Transactional
    public void deleteNoteByNoteId(Integer noteId) {
        noteRepository.deleteById(noteId);
    }

    @Override
    @Transactional(readOnly = true)
    public NoteResponse getNoteByNoteId(Integer noteId) {
        return noteRepository.findById(noteId).map(noteToNoteResponseConverter::convert)
                .orElseThrow(NoteNotFoundException::new);
    }

    @Override
    @Transactional
    public NoteResponse updateNoteByNoteId(Integer noteId, NoteRequest noteRequest, String user) {
        Note existingNote = noteRepository.findById(noteId).orElseThrow(NoteNotFoundException::new);
        Note updatedNote = noteRepository.save(noteRequestToNoteConverter.convert(noteRequest, existingNote, user));
        return noteToNoteResponseConverter.convert(updatedNote);
    }

}

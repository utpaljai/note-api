package com.myorg.note.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import com.myorg.note.converter.NoteRequestToNoteConverter;
import com.myorg.note.converter.NoteToNoteResponseConverter;
import com.myorg.note.exception.NoteNotFoundException;
import com.myorg.note.fixtures.NoteFixture;
import com.myorg.note.fixtures.NoteRequestFixture;
import com.myorg.note.models.NoteResponse;
import com.myorg.note.repository.NoteRepository;

@ExtendWith(MockitoExtension.class)
public class NoteServiceTest {
    @InjectMocks
    private NoteServiceImpl service;

    @Mock
    private NoteRepository repository;

    @Mock
    private NoteRequestToNoteConverter noteRequestToNoteConverter;

    @Mock
    private NoteToNoteResponseConverter noteToNoteResponseConverter;

    @Test
    public void testGetAllNotes() {
        Mockito.when(repository.findAll()).thenReturn(NoteFixture.getMultipleNotes());

        List<NoteResponse> noteResponseList = service.getAllNotes();
        assertEquals(2, noteResponseList.size());
    }

    @Test
    public void testGetNoteById() {
        Mockito.when(repository.findById(1)).thenReturn(Optional.empty());

        assertThrows(NoteNotFoundException.class, () -> {
            service.getNoteByNoteId(1);
        });
    }

    @Test
    public void testCreateNotes() {
        Mockito.when(repository.saveAll(any())).thenReturn(NoteFixture.getNotes());

        List<NoteResponse> noteResponseList = service.createNotes(NoteRequestFixture.getNoteRequestList(),
                "created_user");
        assertEquals(1, noteResponseList.size());
    }

    @Test
    public void testDeleteNote() {
        Mockito.doNothing().when(repository).deleteById(any());

        service.deleteNoteByNoteId(1);
        Mockito.verify(repository, Mockito.times(1)).deleteById(any());

    }

    @Test
    public void testUpdateNote() {
        Mockito.when(repository.findById(any())).thenReturn(Optional.of(NoteFixture.getNote2()));

        Mockito.when(repository.save(any())).thenReturn(NoteFixture.getNote2());

        service.updateNoteByNoteId(2, NoteRequestFixture.getUpdatedNoteRequest(), "updated_user");

        Mockito.verify(repository, Mockito.times(1)).findById(any());
        Mockito.verify(repository, Mockito.times(1)).save(any());
    }
}

package com.myorg.note.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.mockito.ArgumentMatchers.any;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.myorg.note.fixtures.NoteRequestFixture;
import com.myorg.note.fixtures.NoteResponseFixture;
import com.myorg.note.service.NoteService;

@ExtendWith(SpringExtension.class)
@WebMvcTest(NoteController.class)
public class NoteControllerTest {
    @MockBean
    private NoteService noteService;

    @Autowired
    private MockMvc mockMvc;

    private ObjectMapper objectMapper = new ObjectMapper();

    @Test
    public void testCreateNotes() throws Exception {
        Mockito.when(noteService.createNotes(any(), any())).thenReturn(NoteResponseFixture.getNoteResponseList());

        mockMvc.perform(post("/notes").header("user", "created_user")
                .content(objectMapper.writeValueAsString(NoteRequestFixture.getNoteRequestList()))
                .contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
                .andExpect(jsonPath("$[0].noteId").value("1")).andExpect(jsonPath("$[0].noteText").value("note 1"))
                .andExpect(jsonPath("$[0].createdByUser").value("created_user"));
    }

    @Test
    public void testGetAllNotes() throws Exception {
        Mockito.when(noteService.getAllNotes()).thenReturn(NoteResponseFixture.getNoteResponseList());

        mockMvc.perform(get("/notes").contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
                .andExpect(jsonPath("$[0].noteId").value("1")).andExpect(jsonPath("$[0].noteText").value("note 1"))
                .andExpect(jsonPath("$[0].createdByUser").value("created_user"))
                .andExpect(jsonPath("$[0].updatedByUser").value("created_user"));

    }

    @Test
    public void testUpdateNoteById() throws Exception {
        Mockito.when(noteService.updateNoteByNoteId(any(), any(), any()))
                .thenReturn(NoteResponseFixture.getUpdatedNoteResponse());

        mockMvc.perform(put("/notes/1").header("user", "updated_user")
                .content(objectMapper.writeValueAsString(NoteRequestFixture.getUpdatedNoteRequest()))
                .contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
                .andExpect(jsonPath("$.noteId").value("1")).andExpect(jsonPath("$.noteText").value("note updated"))
                .andExpect(jsonPath("$.createdByUser").value("created_user"))
                .andExpect(jsonPath("$.updatedByUser").value("updated_user"));

    }

    @Test
    public void testDeleteNoteById() throws Exception {
        Mockito.doNothing().when(noteService).deleteNoteByNoteId(any());

        mockMvc.perform(delete("/notes/1").contentType(MediaType.APPLICATION_JSON)).andExpect(status().isNoContent());
    }
}

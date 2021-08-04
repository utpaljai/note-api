package com.myorg.note.converter;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import com.myorg.note.entity.Note;
import com.myorg.note.models.NoteResponse;

@Component
public class NoteToNoteResponseConverter implements Converter<Note, NoteResponse> {

    @Override
    public NoteResponse convert(Note source) {
        NoteResponse response = new NoteResponse();
        response.setNoteId(source.getNoteId());
        response.setNoteText(source.getNoteText());
        response.setCreatedByUser(source.getCreatedBy());
        response.setUpdatedByUser(source.getModifiedBy());
        response.setCreatedDate(source.getCreatedDate().toLocalDateTime().toLocalDate());
        response.setUpdatedDate(source.getModifiedDate().toLocalDateTime().toLocalDate());
        return response;
    }

}

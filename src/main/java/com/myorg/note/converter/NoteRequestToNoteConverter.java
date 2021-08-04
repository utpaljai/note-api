package com.myorg.note.converter;

import static java.util.Objects.isNull;

import java.sql.Timestamp;
import org.springframework.stereotype.Component;

import com.myorg.note.entity.Note;
import com.myorg.note.models.NoteRequest;

@Component
public class NoteRequestToNoteConverter {
    public Note convert(NoteRequest source, Note note, String user) {
        if (isNull(note)) {
            note = new Note();
            note.setCreatedBy(user);
            note.setModifiedBy(user);
            note.setCreatedDate(new Timestamp(System.currentTimeMillis()));
            note.setModifiedDate(new Timestamp(System.currentTimeMillis()));
        } else {
            note.setModifiedBy(user);
            note.setModifiedDate(new Timestamp(System.currentTimeMillis()));
        }

        note.setNoteText(source.getNoteText());

        return note;
    }

}

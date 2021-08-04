package com.myorg.note.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.myorg.note.entity.Note;

@Repository
public interface NoteRepository extends JpaRepository<Note, Integer> {

    List<Note> findByCreatedByAndModifiedBy(String createdBy, String updatedBy);

    List<Note> findByCreatedByAndModifiedByAndNoteText(String createdBy, String updatedBy, String text);

}

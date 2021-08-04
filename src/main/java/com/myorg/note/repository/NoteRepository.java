package com.myorg.note.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.myorg.note.entity.Note;

@Repository
public interface NoteRepository extends JpaRepository<Note, Integer> {

    List<Note> findByCreatedBy(String user);

    List<Note> findByModifiedBy(String user);

}

package com.interview.noteservice.service;

import com.interview.noteservice.exception.NoEntityFound;
import com.interview.noteservice.model.Action;
import com.interview.noteservice.model.entities.Note;
import com.interview.noteservice.repository.NoteRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
// The interface can be created for the service for complying SOLID principles
// but as that is obviously only one implementation will be present for avoiding writing ...Impl
// decided to create service class without interface.
public class NoteService {
    private final NoteRepository noteRepository;

    public List<Note> getAllNotes() {
        // The notes can be sorted only by id, but it is bad practice
        return noteRepository.findAll(Sort.by("created_at", "id").descending());
    }

    public Note getNote(String id) {
        return noteRepository.findById(id)
                .orElse(null); // if it is needed the exception can be thrown here and handled for error response
    }

    public Note createNote(Note note) {
        if (note == null) {
            return null;
        }
        if (note.getCreatedAt() == null) {
            note.setCreatedAt(LocalDateTime.now());
        }
        return noteRepository.save(note);
    }

    // As we can use DTO for notes better to set values from passed entity to existing entity to DB and save
    public Note updateNote(String id, Note note) {
        Note existingNote = noteRepository.findById(id)
                .orElseThrow(() -> new NoEntityFound("There is no note in DB by id '%s'".formatted(id)));
        existingNote.setValue(note.getValue());
        existingNote.setLikes(note.getLikes());
        existingNote.setCreatedAt(note.getCreatedAt() != null ? note.getCreatedAt() : LocalDateTime.now());
        return noteRepository.save(existingNote);
    }

    public void deleteNote(String id) {
        noteRepository.deleteById(id);
    }

    public Note likeDislikeNote(String id, Action action) {
        Note note = noteRepository.findById(id)
                .orElseThrow(() -> new NoEntityFound("There is no note in DB by id '%s'".formatted(id)));
        switch (action) {
            case LIKE -> note.setLikes(note.getLikes() + 1);
            case DISLIKE -> note.setLikes(note.getLikes() - 1);
            default -> throw new NoEntityFound("The action '%s' is not available here".formatted(action));
        }
        noteRepository.save(note);
        return note;
    }

}

package com.interview.noteservice.controller;

import com.interview.noteservice.model.entities.Note;
import com.interview.noteservice.service.NoteService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/notes")
@RequiredArgsConstructor
public class NoteController {
    private final NoteService noteService;

    @PostMapping
    public Note createNote(@RequestBody Note note) {
        log.trace("Creating a note {}", note);
        return noteService.createNote(note);
    }

    @GetMapping
    public List<Note> getAllNotes() {
        log.info("Getting all notes");
        return noteService.getAllNotes();
    }

    @GetMapping("/{id}")
    public Note getNote(@PathVariable String id) {
        log.info("Getting a note by id='{}'", id);
        return noteService.getNote(id);
    }

    @PutMapping("/{id}")
    public Note updateNote(@PathVariable String id, @RequestBody Note note) {
        log.trace("Updating a note with id='{}' with new note: {}", id, note);
        return noteService.updateNote(id, note);
    }

    @DeleteMapping("/{id}")
    public void deleteNote(@PathVariable String id) {
        noteService.deleteNote(id);
    }

    // These endpoints can be combined to '/{id}/likes' with control parameter.
    // In this case REST principles are not violated but the code and the function are not "clear"
    // PATCH is used because the entity is partially updated
    // Also need to clarify that authenticated user can like endless number of times
    @PatchMapping("/{id}/like")
    public ResponseEntity<Note> likeNote(
            @PathVariable("id") String id) {
        log.info("Liking the note with id='{}'", id);
        return ResponseEntity.ok(noteService.likeDislikeNote(id, note -> note.getLikes() + 1));
    }

    @PatchMapping("/{id}/dislike")
    public ResponseEntity<Note> dislikeNote(
            @PathVariable("id") String id) {
        log.info("Disliking the note with id='{}'", id);
        return ResponseEntity.ok(noteService.likeDislikeNote(id, note -> note.getLikes() - 1));
    }
}
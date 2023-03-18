package com.interview.noteservice.controller;

import com.interview.noteservice.model.Action;
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
        log.trace("Updating a note with id='{}'m with new note: {}", id, note);
        return noteService.updateNote(id, note);
    }

    @DeleteMapping("/{id}")
    public void deleteNote(@PathVariable String id) {
        noteService.deleteNote(id);
    }

    // This endpoint can be divided to 'like' and 'dislike' but REST principles are violated in this case
    // PATCH is used because we partially change the entity
    @PatchMapping("/{id}/likes")
    public ResponseEntity<Note> likeDislikeNote(
            @PathVariable("id") String id,
            @RequestParam("action") Action action) {
        log.info("Executing action='{}' for the note with id='{}'", action, id);
        return ResponseEntity.ok(noteService.likeDislikeNote(id, action));
    }
}
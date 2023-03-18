package com.interview.noteservice;

import com.interview.noteservice.exception.NoEntityFound;
import com.interview.noteservice.model.Action;
import com.interview.noteservice.model.entities.Note;
import com.interview.noteservice.repository.NoteRepository;
import com.interview.noteservice.service.NoteService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
public class NoteServiceTests {
    @InjectMocks
    private NoteService noteService;

    @Mock
    private NoteRepository noteRepository;



    @Test
    void testUpdateNote() {
        Note existingNote = new Note("Note 1", 1);
        Note updatedNote = new Note("Note 2", 1);

        when(noteRepository.findById("1")).thenReturn(Optional.of(existingNote));
        when(noteRepository.save(updatedNote)).thenReturn(updatedNote);

        Note result = noteService.updateNote("1", updatedNote);

        assertThat(result).isEqualTo(updatedNote);
    }


    @Test
    void testUpdateNoteNotFound() {
        Note updatedNote = new Note("Note 1", 1);
        when(noteRepository.findById("1")).thenReturn(Optional.empty());

        assertThatThrownBy(() -> noteService.updateNote("1", updatedNote)).isInstanceOf(NoEntityFound.class);
    }

    @Test
    void testDeleteNote() {
        Note note = new Note("Note 1", 1);
        when(noteRepository.findById("1")).thenReturn(Optional.of(note));
        noteService.deleteNote("1");

        verify(noteRepository, times(1)).deleteById("1");
    }

    @Test
    void testLikeNote() {
        Note note = new Note("Note 1", 1);

        when(noteRepository.findById("1")).thenReturn(Optional.of(note));

        Note result = noteService.likeDislikeNote("1", Action.LIKE);

        assertThat(result.getLikes()).isEqualTo(2);
    }

    @Test
    void testDislikeNote() {
        Note note = new Note("Note 1", 2);

        when(noteRepository.findById("1")).thenReturn(Optional.of(note));

        Note result = noteService.likeDislikeNote("1", Action.DISLIKE);

        assertThat(result.getLikes()).isEqualTo(1);
    }

    @Test
    void testNonExistentAction() {
        Note note = new Note("Note 1", 2);

        when(noteRepository.findById("1")).thenReturn(Optional.of(note));

        assertThatThrownBy(() -> noteService.likeDislikeNote("1", Action.CREATE))
                .isInstanceOf(NoEntityFound.class);
    }
}

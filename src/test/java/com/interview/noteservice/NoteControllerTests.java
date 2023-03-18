package com.interview.noteservice;

import com.interview.noteservice.controller.NoteController;
import com.interview.noteservice.model.entities.Note;
import com.interview.noteservice.service.NoteService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@ExtendWith(SpringExtension.class)
@WebMvcTest(controllers = NoteController.class)
class NoteControllerTests {

    private MockMvc mockMvc;

    @MockBean
    private NoteService noteService;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @BeforeEach()
    public void setup() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @Test
    void testGetAllNotes() throws Exception {
        Note note1 = new Note("Note 1", 5);
        Note note2 = new Note("Note 2", 4);
        List<Note> notes = List.of(note1, note2);
        when(noteService.getAllNotes()).thenReturn(notes);

        mockMvc.perform(get("/api/notes"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].value").value("Note 1"))
                .andExpect(jsonPath("$[1].value").value("Note 2"));
    }

    @Test
    void testCreateNote() throws Exception {
        Note note = new Note();
        note.setValue("New note");
        String noteJson = "{\"title\":\"New note\"}";
        when(noteService.createNote(any(Note.class))).thenReturn(note);

        mockMvc.perform(post("/api/notes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(noteJson))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.value").value("New note"));
    }

    @Test
    void testGetNoteById() throws Exception {
        Note note = new Note("Note 1", 1);
        note.setId("1");
        when(noteService.getNote("1")).thenReturn(note);

        mockMvc.perform(get("/api/notes/1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.value").value("Note 1"));
    }

}

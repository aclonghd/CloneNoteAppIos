package com.example.demo.controller;

import com.example.demo.dto.NoteDto;
import com.example.demo.module.Note;
import com.example.demo.service.NoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin("*")
@RestController
@RequestMapping(path = "api/v1/notes")
public class NoteController {
    @Autowired
    private final NoteService noteService;

    @Autowired
    public NoteController(NoteService noteService) {
        this.noteService = noteService;
    }

    @GetMapping(path = "{token}")
    public ResponseEntity<List<NoteDto>> getAllNotes(@PathVariable("token") String token){
        return new ResponseEntity<>(this.noteService.getAllNotes(token), HttpStatus.OK);
    }

    @GetMapping(path = "{token}/{noteId}")
    public ResponseEntity<NoteDto> getNoteById(@PathVariable("token") String token,@PathVariable("noteId") Long id){
        return new ResponseEntity<>(this.noteService.getNoteById(token, id), HttpStatus.OK);
    }

    @PostMapping(path = "{token}")
    public HttpStatus addNewNote(@PathVariable("token") String token, @RequestBody Note newNote){
        String status = this.noteService.addNewNote(token, newNote);
        if(status.equals("200")) return HttpStatus.OK;
        return HttpStatus.BAD_REQUEST;
    }

    @PutMapping(path = "{token}/{noteId}")
    public HttpStatus editNodeById(@PathVariable("token") String token,@PathVariable("noteId") Long id, @RequestBody NoteDto noteEdited){
        String status = noteService.editNoteById(token, id, noteEdited);
        if(status.equals("200")) return HttpStatus.OK;
        return HttpStatus.BAD_REQUEST;
    }
    @DeleteMapping(path = "{token}/{noteId}")
    public HttpStatus deleteNodeById(@PathVariable("token") String token, @PathVariable("noteId") Long id){
        String status = this.noteService.deleteNoteById(token, id);
        if(status.equals("200")) return HttpStatus.OK;
        return HttpStatus.BAD_REQUEST;
    }
}

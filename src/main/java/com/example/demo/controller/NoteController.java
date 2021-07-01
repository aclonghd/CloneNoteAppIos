package com.example.demo.controller;

import com.example.demo.module.Note;
import com.example.demo.service.NoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@CrossOrigin("*")
@RestController
@RequestMapping(path = "api/v1/notes")
public class NoteController {
    private final NoteService noteService;

    @Autowired
    public NoteController(NoteService noteService) {
        this.noteService = noteService;
    }

    @GetMapping
    public List<Note> getAllNodes(){
        return noteService.getAllNodes();
    }

    @GetMapping(path = "{noteIds}")
    public List<Note> getNodeByIDs(@PathVariable("noteIds") List<Long> ids){
        return noteService.getNodeByIDs(ids);
    }

    @PostMapping
    public void addNewNode(@RequestBody Note newNote){
        noteService.addNewNode(newNote);
    }

    @PutMapping(path = "{noteId}")
    public String editNodeById(@PathVariable("noteId") Long id, @RequestBody Note noteEdited){
        return noteService.editNodeById(id, noteEdited);
    }
    @DeleteMapping(path = "{noteIds}")
    public String deleteNodeByIDs(@PathVariable("noteIds") List<Long> ids){
        return noteService.deleteNodeByIDs(ids);
    }
}

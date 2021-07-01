package com.example.demo.service;

import com.example.demo.module.Note;
import com.example.demo.respository.NoteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.List;

@Service
public class NoteService {
    private final NoteRepository noteRepository;

    @Autowired
    public NoteService(NoteRepository noteRepository) {
        this.noteRepository = noteRepository;
    }

    public List<Note> getAllNodes(){
        return noteRepository.findAll();
    }
    public List<Note> getNodeByIDs(List<Long> ids){
        return noteRepository.findAllById(ids);
    }
    public void addNewNode(Note newNote){
        noteRepository.save(newNote);
    }
    public String editNodeById(Long id, Note noteEdited){
        if(!noteRepository.existsById(id)) return "Error";
        noteEdited.setId(id);
        noteRepository.save(noteEdited);
        return "Success";
    }
    public String deleteNodeByIDs(List<Long> ids){
        for (Long id : ids) {
            if (!noteRepository.existsById(id)) return "Error";
        }
        noteRepository.deleteAllById(ids);
        return "Success";
    }
}

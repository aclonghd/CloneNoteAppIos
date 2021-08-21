package com.example.demo.service;

import com.example.demo.dto.NoteDto;
import com.example.demo.mapper.NoteMapper;
import com.example.demo.module.Note;
import com.example.demo.module.User;
import com.example.demo.respository.NoteRepository;
import com.example.demo.respository.UserRepository;
import com.example.demo.security.TokenStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.LinkedList;
import java.util.List;

@Service
public class NoteService {
    private final NoteRepository noteRepository;
    private final UserRepository userRepository;
    private final NoteMapper noteMapper;

    @Autowired
    public NoteService(NoteRepository noteRepository, UserRepository userRepository, NoteMapper noteMapper) {
        this.noteRepository = noteRepository;
        this.userRepository = userRepository;
        this.noteMapper = noteMapper;
    }

    public List<NoteDto> getAllNotes(String token){
        String username = TokenStore.getInstance().getUsername(token);
        if(username == null || !this.userRepository.findByUsername(username).isPresent()){
            throw new IllegalStateException("Some thing wrong");
        }
        User user = this.userRepository.findByUsername(username).get();
        List<Note> listNoteEntity = noteRepository.findAllByUserId(user.getId());
        List<NoteDto> listNoteDto = new LinkedList<>();
        for (Note noteEntity:listNoteEntity) {
            NoteDto noteResponseDto = this.noteMapper.entityToDto(noteEntity);
            listNoteDto.add(noteResponseDto);
        }
        return listNoteDto;
    }
    public String addNewNote(String token, Note newNote){
        String username = TokenStore.getInstance().getUsername(token);
        if(username == null || !this.userRepository.findByUsername(username).isPresent()){
            throw new IllegalStateException("Some thing wrong");
        }
        User user = this.userRepository.findByUsername(username).get();
        newNote.setUser(user);
        this.noteRepository.save(newNote);
        return "200";
    }
    public String editNoteById(String token, Long id, NoteDto noteDtoEdit){
        String username = TokenStore.getInstance().getUsername(token);
        if(username == null || !this.noteRepository.existsById(id) || !this.userRepository.findByUsername(username).isPresent()){
            return "500";
        }
        noteDtoEdit.setId(id);
        Note noteEdited = this.noteMapper.dtoToEntity(noteDtoEdit);
        User userEdit = this.userRepository.findByUsername(username).get();
        noteEdited.setUser(userEdit);
        this.noteRepository.save(noteEdited);
        return "200";
    }
    public String deleteNoteById(String token, Long id){
        if(TokenStore.getInstance().getUsername(token) == null || !this.noteRepository.existsById(id)){
            return "500";
        }
        this.noteRepository.deleteById(id);
        return "200";
    }

    public NoteDto getNoteById(String token, Long id) {
        if(TokenStore.getInstance().getUsername(token) == null || !this.noteRepository.existsById(id)){
            throw new IllegalStateException("Some thing wrong");
        }
        return this.noteMapper.entityToDto(this.noteRepository.getById(id));
    }
}

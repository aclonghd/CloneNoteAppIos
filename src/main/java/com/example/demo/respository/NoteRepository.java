package com.example.demo.respository;

import com.example.demo.module.Note;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface NoteRepository extends JpaRepository<Note, Long> {

    List<Note> findAllByUserIdOrderById(Long id);
}

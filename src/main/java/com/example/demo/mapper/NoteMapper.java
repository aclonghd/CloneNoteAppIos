package com.example.demo.mapper;

import com.example.demo.dto.NoteDto;
import com.example.demo.module.Note;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface NoteMapper extends BaseMapper<NoteDto, Note> {

}

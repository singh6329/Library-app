package com.example.LibraryManagementSystem.services;

import com.example.LibraryManagementSystem.dtos.AuthorDto;

import java.util.List;

public interface AuthorService {

    List<AuthorDto> getAllAuthors();

    List<AuthorDto> getAuthorByName(String name);

    AuthorDto getAuthorById(Long id);

    AuthorDto createAuthor(AuthorDto authorDto);

    void deleteAuthor(Long id);

    AuthorDto updateAuthorById(Long id,AuthorDto authorDto);
}

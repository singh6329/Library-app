package com.example.LibraryManagementSystem.services.Impl;

import com.example.LibraryManagementSystem.custExceptions.ResourceNotFoundException;
import com.example.LibraryManagementSystem.dtos.AuthorDto;
import com.example.LibraryManagementSystem.entites.Author;
import com.example.LibraryManagementSystem.repositories.AuthorRepository;
import com.example.LibraryManagementSystem.services.AuthorService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthorServiceImpl implements AuthorService {
    private final ModelMapper modelMapper;
    private final AuthorRepository authorRepository;

    @Override
    public List<AuthorDto> getAllAuthors() {
        List<Author> authors = authorRepository.findAll();
        log.info("Successfully fetched all Authors!");
        return authors.stream().map(author -> modelMapper.map(author,AuthorDto.class)).toList();
    }

    @Override
    public List<AuthorDto> getAuthorByName(String name) {
        List<Author> authors = authorRepository.findByName(name);
        log.info("Successfully fetched all Authors with respect to given name!");
        return authors.stream().map(author -> modelMapper.map(author,AuthorDto.class)).toList();
    }

    @Override
    public AuthorDto getAuthorById(Long id) {
        Author author = authorRepository.findById(id)
                .orElseThrow(()->{
                    log.info("Author with id: "+id+" doesn't exists!");
                    throw new ResourceNotFoundException("Author with id: "+id+" doesn't exists!");});

        log.info("Fetched Author with id details successfully!");
        return modelMapper.map(author,AuthorDto.class);
    }

    @Override
    public AuthorDto createAuthor(AuthorDto authorDto) {
        Author author = modelMapper.map(authorDto,Author.class);
        Author savedAuthor = authorRepository.save(author);
        log.info("Author created successfully!");
        return modelMapper.map(savedAuthor,AuthorDto.class);
    }

    @Override
    public void deleteAuthor(Long id) {
        Author author = authorRepository.findById(id)
                .orElseThrow(()-> {
                    log.info("Author doesn't exists with id: "+id);
                    throw new RuntimeException("Author doesn't exists with id: "+id);});

        authorRepository.delete(author);
        log.info("Author Deleted Successfully!");
    }

    @Override
    public AuthorDto updateAuthorById(Long id, AuthorDto authorDto) {
        Author author = modelMapper.map(authorDto,Author.class);
        author.setId(id);
        Author savedAuthor = authorRepository.save(author);
        log.info("Author updated Successfully!");
        return modelMapper.map(savedAuthor,AuthorDto.class);
    }
}

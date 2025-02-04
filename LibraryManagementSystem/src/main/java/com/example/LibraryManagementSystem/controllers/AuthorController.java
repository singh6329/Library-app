package com.example.LibraryManagementSystem.controllers;

import com.example.LibraryManagementSystem.dtos.AuthorDto;
import com.example.LibraryManagementSystem.services.AuthorService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/authors")
@RequiredArgsConstructor
public class AuthorController {
    private final AuthorService authorService;

    @GetMapping("/")
    public ResponseEntity<List<AuthorDto>> getAllAuthors()
    {
       return ResponseEntity.ok(authorService.getAllAuthors());
    }

    @GetMapping("/{id}")
    public ResponseEntity<AuthorDto> getAuthorById(@PathVariable Long id)
    {
        return ResponseEntity.ok(authorService.getAuthorById(id));
    }

    @GetMapping("/getAuthorByName/{name}")
    public ResponseEntity<List<AuthorDto>> getAuthorByName(@PathVariable String name)
    {
        return new ResponseEntity<>(authorService.getAuthorByName(name),HttpStatus.OK);
    }

    @PostMapping("/")
    public ResponseEntity<AuthorDto> addAuthor(@RequestBody AuthorDto authorDto)
    {
        return new ResponseEntity<>(authorService.createAuthor(authorDto), HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAuthor(@PathVariable Long id)
    {
        authorService.deleteAuthor(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<AuthorDto> updateAuthorById(@PathVariable Long id,
                                                      @RequestBody AuthorDto authorDto)
    {
        return new ResponseEntity<>(authorService.updateAuthorById(id, authorDto),HttpStatus.OK);
    }

}

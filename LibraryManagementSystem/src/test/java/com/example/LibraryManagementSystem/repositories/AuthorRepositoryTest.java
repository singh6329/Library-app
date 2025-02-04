package com.example.LibraryManagementSystem.repositories;

import com.example.LibraryManagementSystem.entites.Author;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;


class AuthorRepositoryTest extends RepositoryTest{

    @Autowired
    private AuthorRepository authorRepository;

    private Author author;

    @BeforeEach
    void setUp()
    {
     author = Author.builder().name("Jack").build();
     authorRepository.deleteAll();
    }

    @Test
    void testFindByName_whenNameIsPresent_thenReturnAuthorList()
    {
        //arrange
        String name = "Jack";
        authorRepository.save(author);

        //act
        List<Author> newAuthors = authorRepository.findByName(name);

        //assert
        assertThat(newAuthors).hasSize(1);
        assertThat(newAuthors.getFirst().getName()).isEqualTo(name);
    }

    @Test
    void testFindByName_whenNameIsNotPresent_thenReturnEmptyList()
    {
        //arrange
        authorRepository.save(author);

        //act
        List<Author> authors = authorRepository.findByName("Jagjeet");

        //assert
        assertThat(authors).hasSize(0);
        assertThat(authors).isEmpty();
    }


}
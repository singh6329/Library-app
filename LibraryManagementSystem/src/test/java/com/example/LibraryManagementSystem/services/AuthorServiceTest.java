package com.example.LibraryManagementSystem.services;

import com.example.LibraryManagementSystem.custExceptions.ResourceNotFoundException;
import com.example.LibraryManagementSystem.dtos.AuthorDto;
import com.example.LibraryManagementSystem.entites.Author;
import com.example.LibraryManagementSystem.repositories.AuthorRepository;
import com.example.LibraryManagementSystem.services.Impl.AuthorServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

class AuthorServiceTest extends ServiceTest{

    @Mock
    AuthorRepository authorRepository;

    Author author;
    AuthorDto authorDto;

    @InjectMocks
    AuthorServiceImpl authorService;

    @BeforeEach
    void setUp()
    {
        author = Author.builder().id(1L)
                .name("Jagjeet Singh")
                .build();
        authorDto = modelMapper.map(author,AuthorDto.class);
    }

    @Test
    void testGetAllAuthors_whenAuthorsArePresent_thenReturnListOfAuthors()
    {
        //arrange
        when(authorRepository.findAll()).thenReturn(List.of(author));
       // when(modelMapper.map(author,AuthorDto.class)).thenReturn(authorDto);

        //act
        List<AuthorDto> authors = authorService.getAllAuthors();

        //assert
        assertThat(authors).hasSize(1);
        assertThat(authors.getFirst().getName()).isEqualTo(author.getName());
        verify(authorRepository,atLeastOnce()).findAll();
    }

    @Test
    void testGetAllAuthors_whenNoAuthorsPresent_thenReturnEmptyList()
    {
        //arrange
        when(authorRepository.findAll()).thenReturn(List.of());

        //act
        List<AuthorDto> authors = authorService.getAllAuthors();

        //assert
        assertThat(authors).isEmpty();
        verify(authorRepository,atLeastOnce()).findAll();
        verify(modelMapper,never()).map(any(Author.class),any(AuthorDto.class));
        assertThat(authors).hasSize(0);
    }

    @Test
    void testGetAuthorByName_whenAuthorsArePresent_thenReturnAuthors()
    {
        //arrange
        when(authorRepository.findByName("Jagjeet Singh")).thenReturn(List.of(author));

        //act
        List<AuthorDto> authors = authorService.getAuthorByName("Jagjeet Singh");

        //assert
        assertThat(authors).isNotEmpty();
        assertThat(authors).hasSize(1);
        verify(authorRepository,atLeastOnce()).findByName("Jagjeet Singh");
        assertThat(authors.getFirst().getName()).isEqualTo(author.getName());
    }

    @Test
    void testGetAuthorById_whenAuthorIsPresent_thenReturnAuthor()
    {
        //arrange
        when(authorRepository.findById(anyLong())).thenReturn(Optional.of(author));

        //act
        AuthorDto retrivedAuthorDto = authorService.getAuthorById(author.getId());

        //assert
        assertThat(retrivedAuthorDto).isNotNull();
        assertThat(retrivedAuthorDto.getName()).isEqualTo(authorDto.getName());
    }

    @Test
    void testGetAuthorById_whenAuthorIsNotPresent_thenReturnException()
    {
        //arrange
        when(authorRepository.findById(anyLong())).thenReturn(Optional.empty());

        //act & assert
        assertThatThrownBy(()->authorService.getAuthorById(author.getId()))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessage("Author with id: "+author.getId()+" doesn't exists!");
    }

    @Test
    void testCreateAuthor_whenAuthorIsCorrect_thenReturnAuthorDto()
    {
        when(authorRepository.save(any(Author.class))).thenReturn(author);

        AuthorDto savedAuthorDto = authorService.createAuthor(authorDto);

        assertThat(savedAuthorDto).isNotNull();
        assertThat(savedAuthorDto.getName()).isEqualTo(authorDto.getName());
        verify(authorRepository).save(any(Author.class));
        ArgumentCaptor<Author> captor = ArgumentCaptor.forClass(Author.class);
        verify(authorRepository).save(captor.capture());
        Author captureAuthor = captor.getValue();
        assertThat(captureAuthor.getName()).isEqualTo(author.getName());
    }

    @Test
    void testDeleteAuthor_whenAuthorIsPresent_thenDeleteAuthor()
    {
        when(authorRepository.findById(anyLong())).thenReturn(Optional.of(author));

        authorService.deleteAuthor(author.getId());

        verify(authorRepository,atLeastOnce()).delete(author);
        ArgumentCaptor<Author> captor = ArgumentCaptor.forClass(Author.class);
        verify(authorRepository).delete(captor.capture());
        Author capturedAuthor = captor.getValue();
        assertThat(capturedAuthor.getId()).isEqualTo(author.getId());

    }

    @Test
    void testDeleteAuthor_whenAuthorIsNotPresent_thenThrowException()
    {
        when(authorRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThatThrownBy(()->authorService.deleteAuthor(author.getId()))
                .hasMessage("Author doesn't exists with id: "+author.getId())
                .isInstanceOf(RuntimeException.class);

    }

    @Test
    void testUpdateAuthorById_whenAuthorIsPresent_thenReturnUpdatedAuthorDto()
    {
        author.setId(2L);
        when(authorRepository.save(any(Author.class))).thenReturn(author);

        AuthorDto authorDto1 = authorService.updateAuthorById(2L,authorDto);

        assertThat(authorDto1).isNotNull();
        verify(authorRepository, atLeast(1)).save(any(Author.class));

    }

}
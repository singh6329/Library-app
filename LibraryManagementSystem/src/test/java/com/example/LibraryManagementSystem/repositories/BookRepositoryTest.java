package com.example.LibraryManagementSystem.repositories;

import com.example.LibraryManagementSystem.entites.Author;
import com.example.LibraryManagementSystem.entites.Book;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class BookRepositoryTest extends RepositoryTest{

    @Autowired
    BookRepository bookRepository;

    @Autowired
    AuthorRepository authorRepository;

    Book book;

    Author author = Author.builder().name("Jagjeet").build();
    @BeforeEach
    void setUp(){
        book = Book.builder().title("The James Bond")
                .price(345D).publishedDate(LocalDate.of(2024,10,30))
                .build();
        bookRepository.deleteAll();
    }

    @Test
    void testFindByAuthor_whenAuthorIsPresent_thenReturnListOfBooks()
    {
        //arrange
       Author savedAuthor =  authorRepository.save(author);
       book.setAuthor(savedAuthor);
       bookRepository.save(book);

        //act
        List<Book> retrievedBooks = bookRepository.findByAuthor(author);

        //assert
        assertThat(retrievedBooks).isNotEmpty();
        assertThat(retrievedBooks).hasSize(1);
        assertThat(retrievedBooks.getFirst().getAuthor().getName()).isEqualTo(author.getName());
        assertThat(retrievedBooks.getFirst().getAuthor().getId()).isEqualTo(author.getId());
    }

    @Test
    void testFindByAuthor_whenAuthorIsNotPresent_ThenReturnEmptyList()
    {
        //arrange
        book.setAuthor(null);
        bookRepository.save(book);
        Author author1 = Author.builder().id(1L).name("Satish").build();

        //act
        List<Book> books = bookRepository.findByAuthor(author1);

        //assert
        assertThat(books).isEmpty();
        assertThat(books).hasSize(0);
    }

    @Test
    void testFindByTitle_whenBookIsPresentForGivenTitle_thenReturnListOfBooks()
    {
        //arrange
        bookRepository.save(book);

        //act
        List<Book> books = bookRepository.findByTitle("The James Bond");

        //assert
        assertThat(books).isNotEmpty();
        assertThat(books).hasSize(1);
        assertThat(books.getFirst().getTitle()).isEqualTo(book.getTitle());
    }

    @Test
    void testFindByTitle_whenBookIsNotPresentForGivenTitle_thenReturnEmptyBooks()
    {
        //arrange
        bookRepository.save(book);

        //act
        List<Book> books = bookRepository.findByTitle("The Roadies Crunches");

        //assert
        assertThat(books).isEmpty();
        assertThat(books).hasSize(0);

    }

    @Test
    void testFindByPublishedDateAfter_whenBookIsPresentForDate_thenReturnListOfBooks()
    {
        //arrange
        bookRepository.save(book);

        //act
        List<Book> books = bookRepository.findByPublishedDateAfter(LocalDate.of(2024,8,30));

        //assert
        assertThat(books).isNotEmpty();
        assertThat(books).hasSize(1);
        assertThat(books.getFirst().getTitle()).isEqualTo(book.getTitle());
    }

    @Test
    void testFindByPublishedDateAfter_whenBookIsNotPresentForGivenDate_thenReturnEmptyList()
    {
        //arrange
        bookRepository.save(book);

        //act
       List<Book> books = bookRepository.findByPublishedDateAfter(LocalDate.of(2025,1,22));

        //assert
        assertThat(books).isEmpty();
        assertThat(books).hasSize(0);
    }


}
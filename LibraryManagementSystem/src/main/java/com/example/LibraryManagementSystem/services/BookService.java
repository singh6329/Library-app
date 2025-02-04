package com.example.LibraryManagementSystem.services;

import com.example.LibraryManagementSystem.dtos.AuthorDto;
import com.example.LibraryManagementSystem.dtos.BookDto;
import com.example.LibraryManagementSystem.entites.Book;

import java.time.LocalDate;
import java.util.List;

public interface BookService {

    List<BookDto> getAllBooks();

    BookDto getBookById(Long id);

    List<BookDto> getAllBooksByAuthor(Long id);

    BookDto createBook(BookDto bookDto);

    List<BookDto> findBookByTitle(String title);

    void deleteBookById(Long id);

    BookDto updateBookById(Long id,BookDto bookDto);

    List<BookDto> getBooksPublishedAfter(LocalDate publishedDate);

    BookDto assignAuthorToBook(Long bookId, Long authorId);

}

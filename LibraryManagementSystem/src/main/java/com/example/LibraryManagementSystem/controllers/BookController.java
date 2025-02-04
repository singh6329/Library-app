package com.example.LibraryManagementSystem.controllers;

import com.example.LibraryManagementSystem.dtos.BookDto;
import com.example.LibraryManagementSystem.entites.Book;
import com.example.LibraryManagementSystem.services.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/books")
@RequiredArgsConstructor
public class BookController {
    private final BookService bookService;

    @GetMapping("/")
    public ResponseEntity<List<BookDto>> getAllBooks()
    {
        return new ResponseEntity<>(bookService.getAllBooks(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<BookDto> getBookById(@PathVariable Long id)
    {
        return new ResponseEntity<>(bookService.getBookById(id), HttpStatus.OK);
    }

    @GetMapping("/getBooksByAuthor/{id}")
    public ResponseEntity<List<BookDto>> getAllBooksByAuthor(@PathVariable Long id)
    {
        return new ResponseEntity<>(bookService.getAllBooksByAuthor(id),HttpStatus.OK);
    }

    @GetMapping("/findBookByTitle/{title}")
    public ResponseEntity<List<BookDto>> findBookByTitle(@PathVariable String title)
    {
        return new ResponseEntity<>(bookService.findBookByTitle(title),HttpStatus.OK);
    }

    @GetMapping("/getBooksPublishedAfter/{publishedDate}")
    public ResponseEntity<List<BookDto>> getBooksPublishedAfter(@PathVariable LocalDate publishedDate)
    {
        return new ResponseEntity<>(bookService.getBooksPublishedAfter(publishedDate),HttpStatus.OK);
    }

    @PostMapping("/")
    public ResponseEntity<BookDto> addBook(@RequestBody BookDto bookDto)
    {
        return new ResponseEntity<>(bookService.createBook(bookDto),HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBook(@PathVariable Long id)
    {
        bookService.deleteBookById(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/updateBook/{id}")
    public ResponseEntity<BookDto> updateBookById(@PathVariable Long id,
                                                  @RequestBody BookDto bookDto)
    {
        return new ResponseEntity<>(bookService.updateBookById(id,bookDto),HttpStatus.OK);
    }

    @PatchMapping("/assignAuthor/{authorId}/toBook/{bookId}")
    public ResponseEntity<BookDto> assignAuthorToBook(@PathVariable Long authorId,
                                                      @PathVariable Long bookId)
    {
        return new ResponseEntity<>(bookService.assignAuthorToBook(bookId,authorId),HttpStatus.OK);
    }

}

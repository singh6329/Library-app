package com.example.LibraryManagementSystem.services.Impl;

import com.example.LibraryManagementSystem.custExceptions.ResourceNotFoundException;
import com.example.LibraryManagementSystem.dtos.AuthorDto;
import com.example.LibraryManagementSystem.dtos.BookDto;
import com.example.LibraryManagementSystem.entites.Author;
import com.example.LibraryManagementSystem.entites.Book;
import com.example.LibraryManagementSystem.repositories.BookRepository;
import com.example.LibraryManagementSystem.services.AuthorService;
import com.example.LibraryManagementSystem.services.BookService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class BookServiceImpl implements BookService {

    private final ModelMapper modelMapper;
    private final BookRepository bookRepository;
    private final AuthorService authorService;

    @Override
    public List<BookDto> getAllBooks() {
        List<Book> books = bookRepository.findAll();
        log.info("Fetched all books successfully!");
        return books.stream().map(book -> modelMapper.map(book,BookDto.class)).collect(Collectors.toList());
    }

    @Override
    public BookDto getBookById(Long id) {

        Book book = bookRepository.findById(id)
                .orElseThrow(()->
                {
                    log.error("Book with id: "+id+" doesn't exists!");
                    return new ResourceNotFoundException("Book with id: "+id+" doesn't exists!");
                });
        log.info("Fetched book with id: "+id+" successfully!");
        return modelMapper.map(book,BookDto.class);

    }

    @Override
    public List<BookDto> getAllBooksByAuthor(Long id) {
        AuthorDto authorDto = authorService.getAuthorById(id);
        Author author = modelMapper.map(authorDto,Author.class);
        List<Book> books = bookRepository.findByAuthor(author);
        log.info("Fetched all books with respect to Author successfully!");
        return books.stream().map(book -> modelMapper.map(book,BookDto.class)).collect(Collectors.toList());
    }

    @Override
    public BookDto createBook(BookDto bookDto) {
        Book book = modelMapper.map(bookDto,Book.class);
        Book savedBook = bookRepository.save(book);
        log.info("Book saved successfully!");
        return modelMapper.map(savedBook,BookDto.class);
    }

    @Override
    public List<BookDto> findBookByTitle(String title) {
        List<Book> books = bookRepository.findByTitle(title);
        log.info("Fetched all books with respect to title successfully!");
        return books.stream().map(book -> modelMapper.map(book,BookDto.class)).toList();
    }

    @Override
    public void deleteBookById(Long id) {
        Book book = bookRepository.findById(id).orElseThrow(()->{
                log.info("Cannot find book with id: "+id);
            return new RuntimeException("Cannot find book with id: " + id);
        });
        bookRepository.delete(book);
        log.info("Book deleted Successfully!");
    }

    @Override
    public BookDto updateBookById(Long id, BookDto bookDto) {
        Book book = modelMapper.map(bookDto,Book.class);
        book.setId(id);
        Book savedBook = bookRepository.save(book);
        log.info("Book updated Successfully!");
        return modelMapper.map(savedBook,BookDto.class);
    }

    @Override
    public List<BookDto> getBooksPublishedAfter(LocalDate publishedDate) {
        List<Book> books = bookRepository.findByPublishedDateAfter(publishedDate);
        log.info("Fetched all books published after given date!");
        return books.stream().map(book -> modelMapper.map(book,BookDto.class)).toList();
    }

    @Override
    public BookDto assignAuthorToBook(Long bookId, Long authorId) {
        AuthorDto authorDto = authorService.getAuthorById(authorId);
        Author author = modelMapper.map(authorDto,Author.class);
        Book book = bookRepository.findById(bookId).
                orElseThrow(()->new RuntimeException("Unable to find Book with id: "+ bookId));
        book.setAuthor(author);
        Book updatedBook = bookRepository.save(book);
        log.info("Author assigned to Book successfully!");
        BookDto bookDto= modelMapper.map(updatedBook,BookDto.class);
        bookDto.setAuthorDto(authorDto);
        return bookDto;
    }
}

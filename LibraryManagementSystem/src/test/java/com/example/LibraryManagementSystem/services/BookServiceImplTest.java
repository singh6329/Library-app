package com.example.LibraryManagementSystem.services;

import com.example.LibraryManagementSystem.custExceptions.ResourceNotFoundException;
import com.example.LibraryManagementSystem.dtos.AuthorDto;
import com.example.LibraryManagementSystem.dtos.BookDto;
import com.example.LibraryManagementSystem.entites.Author;
import com.example.LibraryManagementSystem.entites.Book;
import com.example.LibraryManagementSystem.repositories.AuthorRepository;
import com.example.LibraryManagementSystem.repositories.BookRepository;
import com.example.LibraryManagementSystem.services.Impl.AuthorServiceImpl;
import com.example.LibraryManagementSystem.services.Impl.BookServiceImpl;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

class BookServiceImplTest extends ServiceTest{

    @Mock
    BookRepository bookRepository;

    @Mock
    AuthorRepository authorRepository;

    @Mock
    AuthorServiceImpl authorService;

    @InjectMocks
    BookServiceImpl bookService;

    Book book;

    BookDto bookDto;

    Author author = Author.builder().id(1L).name("Kipper").build();
    AuthorDto authorDto ;

    @BeforeEach
    void setUp()
    {
        book = Book.builder().id(1L)
                .price(3000D)
                .title("Jitt de Nishan")
                .publishedDate(LocalDate.of(2023,11,22))
                .author(author)
                .build();
        bookDto = modelMapper.map(book,BookDto.class);
        authorDto = modelMapper.map(author,AuthorDto.class);
    }

    @Test
    void testGetAllBooks_whenBooksArePresent_thenReturnBooks()
    {
        when(bookRepository.findAll()).thenReturn(List.of(book));

        List<BookDto> books = bookService.getAllBooks();

        assertThat(books).isNotNull();
        verify(bookRepository,atLeastOnce()).findAll();
        assertThat(books.getFirst().getId()).isEqualTo(book.getId());
    }

    @Test
    void testGetAllBooks_whenBooksAreNotPresent_thenReturnEmptyList()
    {
        when(bookRepository.findAll()).thenReturn(List.of());

        List<BookDto> books = bookService.getAllBooks();

        assertThat(books).isEmpty();
        verify(bookRepository,atLeastOnce()).findAll();
        assertThat(books).hasSize(0);
    }

    @Test
    void testGetBookById_whenBookIsPresent_thenReturnBookDto()
    {
        when(bookRepository.findById(anyLong())).thenReturn(Optional.of(book));

        BookDto bookDto1 = bookService.getBookById(book.getId());

        assertThat(bookDto).isNotNull();
        assertThat(bookDto1.getId()).isEqualTo(book.getId());
        verify(bookRepository,atLeastOnce()).findById(any());
    }

    @Test
    void testGetBookById_whenBookIsNotPresent_thenThrowException()
    {
        when(bookRepository.findById(anyLong())).thenReturn(Optional.empty());

       assertThatThrownBy(()->bookService.getBookById(book.getId()))
               .isInstanceOf(ResourceNotFoundException.class)
               .hasMessage("Book with id: "+book.getId()+" doesn't exists!");

    }

    @Test
    void testGetAllBooksByAuthor_whenAuthorIsPresentWithBooks_thenReturnBooks()
    {
        when(authorService.getAuthorById(anyLong())).thenReturn(authorDto);
        when(bookRepository.findByAuthor(any(Author.class))).thenReturn(List.of(book));

        List<BookDto> books = bookService.getAllBooksByAuthor(1L);

        assertThat(books).isNotEmpty();
        verify(bookRepository).findByAuthor(any(Author.class));
        ArgumentCaptor<Author> captor = ArgumentCaptor.forClass(Author.class);
        verify(bookRepository).findByAuthor(captor.capture());
        Author capturedAuthor = captor.getValue();
        assertThat(capturedAuthor.getId()).isEqualTo(author.getId());
    }

    @Test
    void testCreateBook_whenBookDtoIsCorrect_thenReturnSavedBookDto()
    {
        when(bookRepository.save(any(Book.class))).thenReturn(book);

        BookDto savedBook = bookService.createBook(bookDto);

        assertThat(savedBook).isNotNull();
        assertThat(savedBook.getId()).isEqualTo(book.getId());
        verify(bookRepository,atLeastOnce()).save(any(Book.class));
        ArgumentCaptor<Book> captor = ArgumentCaptor.forClass(Book.class);
        verify(bookRepository).save(captor.capture());
        Book book1 = captor.getValue();
        assertThat(book1.getId()).isEqualTo(book.getId());
    }

    @Test
    void testFindBookByTitle_whenBookTitleIsPresent_thenReturnListOfBookDto()
    {
        when(bookRepository.findByTitle(anyString())).thenReturn(List.of(book));

        List<BookDto> books = bookService.findBookByTitle(book.getTitle());

        assertThat(books).isNotEmpty();
        assertThat(books.getFirst().getTitle()).isEqualTo(book.getTitle());
        verify(bookRepository,atLeast(1)).findByTitle(anyString());
    }

    @Test
    void testDeleteBookById_whenBookIsPresent_thenDeleteBook()
    {
        when(bookRepository.findById(anyLong())).thenReturn(Optional.of(book));

        bookService.deleteBookById(book.getId());

        verify(bookRepository,atLeastOnce()).delete(any(Book.class));
        ArgumentCaptor<Book> captor = ArgumentCaptor.forClass(Book.class);
        verify(bookRepository).delete(captor.capture());
        Book capturedBook = captor.getValue();
        assertThat(capturedBook.getId()).isEqualTo(book.getId());

    }

    @Test
    void testDeleteBookById_whenBookIsNotPresent_thenThrowException()
    {
        when(bookRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThatThrownBy(()->bookService.deleteBookById(book.getId()))
                .isInstanceOf(RuntimeException.class)
                .hasMessage("Cannot find book with id: "+book.getId());

    }

    @Test
    void testUpdateBookById_whenBookIsPresent_thenReturnUpdatedBookDto()
    {
        bookDto.setId(2L);
        when(bookRepository.save(any(Book.class))).thenReturn(book);

        BookDto updatedBookDto = bookService.updateBookById(1L,bookDto);

        assertThat(updatedBookDto).isNotNull();
        assertThat(updatedBookDto.getId()).isEqualTo(book.getId());
        verify(bookRepository,atLeastOnce()).save(any(Book.class));

    }

    @Test
    void testGetBooksPublishedAfter_whenBooksArePresent_thenReturnListOfBooks()
    {
        when(bookRepository.findByPublishedDateAfter(any())).thenReturn(List.of(book));

        List<BookDto> bookDtos = bookService.getBooksPublishedAfter(LocalDate.now());

        assertThat(bookDtos).isNotNull();
        assertThat(bookDtos.getFirst().getId()).isEqualTo(book.getId());

    }

    @Test
    void testAssignAuthorToBook_whenAuthorAndBookIsPresent_thenReturnUpdatedBookDto()
    {
        when(authorService.getAuthorById(anyLong())).thenReturn(authorDto);
        when(bookRepository.findById(anyLong())).thenReturn(Optional.of(book));
        when(bookRepository.save(any())).thenReturn(book);

        BookDto bookDto1 = bookService.assignAuthorToBook(1L,1L);

        assertThat(bookDto1).isNotNull();
        assertThat(bookDto1.getAuthorDto().getId()).isEqualTo(author.getId());
        verify(bookRepository,atLeastOnce()).save(book);
        ArgumentCaptor<Book> captor = ArgumentCaptor.forClass(Book.class);
        verify(bookRepository).save(captor.capture());
        Book capturedValue = captor.getValue();
        assertThat(capturedValue.getId()).isEqualTo(book.getId());

    }

    @Test
    void testAssignAuthorToBook_whenBookIsNotPresent_thenThrowException()
    {
        when(authorService.getAuthorById(anyLong())).thenReturn(authorDto);
        when(bookRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThatThrownBy(()->bookService.assignAuthorToBook(1L,1L))
                .isInstanceOf(RuntimeException.class)
                .hasMessage("Unable to find Book with id: "+1);
        verify(bookRepository,never()).save(any(Book.class));

    }



}
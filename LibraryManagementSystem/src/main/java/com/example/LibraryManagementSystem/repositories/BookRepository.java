package com.example.LibraryManagementSystem.repositories;

import com.example.LibraryManagementSystem.entites.Author;
import com.example.LibraryManagementSystem.entites.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface BookRepository extends JpaRepository<Book,Long> {

    List<Book> findByAuthor(Author author);

    List<Book> findByTitle(String title);

    List<Book> findByPublishedDateAfter(LocalDate publishedDate);
}

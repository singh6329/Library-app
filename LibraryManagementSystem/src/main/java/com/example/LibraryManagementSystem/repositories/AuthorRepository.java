package com.example.LibraryManagementSystem.repositories;

import com.example.LibraryManagementSystem.entites.Author;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AuthorRepository extends JpaRepository<Author,Long> {

    List<Author> findByName(String name);
}

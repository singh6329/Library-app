package com.example.LibraryManagementSystem.dtos;

import lombok.Data;

import java.time.LocalDate;

@Data
public class BookDto {
    private Long id;

    private String title;

    private Double price;

    private LocalDate publishedDate;

    private AuthorDto authorDto;
}

package com.example.LibraryManagementSystem.dtos;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Objects;

@Data
public class AuthorDto {
    private Long id;

    private String name;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof AuthorDto authorDto)) return false;
        return Objects.equals(id, authorDto.id) && Objects.equals(name, authorDto.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }
}

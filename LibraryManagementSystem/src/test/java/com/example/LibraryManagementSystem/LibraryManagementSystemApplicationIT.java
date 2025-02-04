package com.example.LibraryManagementSystem;

import com.example.LibraryManagementSystem.dtos.AuthorDto;
import com.example.LibraryManagementSystem.entites.Author;
import com.example.LibraryManagementSystem.repositories.AuthorRepository;
import com.example.LibraryManagementSystem.services.AuthorService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.client.AutoConfigureWebClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.web.reactive.server.WebTestClient;

import java.util.List;

@Import(TestContainerConfiguration.class)
@AutoConfigureWebClient
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class LibraryManagementSystemApplicationIT {

	@Autowired
	WebTestClient webTestClient;
	@Autowired
	AuthorService authorService;

	@Autowired
	ModelMapper modelMapper;

	@Autowired
	AuthorRepository authorRepository;
	Author author;
	AuthorDto authorDto;

	@BeforeEach
	void setUp() {
		author = Author.builder().name("James Gosling").build();
		authorDto = modelMapper.map(author,AuthorDto.class);
		authorRepository.deleteAll();
	}

	@Test
	void testGetAllAuthors_success() {
		Author savedAuthor = authorRepository.save(author);
		authorDto = modelMapper.map(savedAuthor, AuthorDto.class);
		webTestClient.get()
				.uri("/authors/")
				.exchange()
				.expectStatus().isOk()
				.expectBody(List.class).value(authorDto ->
						Assertions.assertThat(authorDto).isNotNull());
	}

	@Test
	void testGetAuthorById_success()
	{
		Author savedAuthor = authorRepository.save(author);

		webTestClient.get()
				.uri("/authors/{id}",savedAuthor.getId())
				.exchange()
				.expectStatus().isOk()
				.expectBody(AuthorDto.class)
				.value(authorDto1 ->
						Assertions.assertThat(authorDto1.getName()).isEqualTo(author.getName()));
	}

	@Test
	void testGetAuthorById_failure()
	{
		webTestClient.get()
				.uri("/authors/{id}",1)
				.exchange()
				.expectStatus().isNotFound();
	}

	@Test
	void testGetAuthorByName_success()
	{
		Author savedAuthor = authorRepository.save(author);

		webTestClient.get()
				.uri("/authors/getAuthorByName/{name}","James Gosling")
				.exchange()
				.expectStatus().isOk()
				.expectBody(List.class)
				.value(authorDto-> Assertions.assertThat(authorDto.getFirst()).isNotNull());
	}

	@Test
	void testAddAuthor_success() {
		webTestClient.post()
				.uri("/authors/")
				.bodyValue(authorDto)
				.exchange()
				.expectStatus().isCreated()
				.expectBody()
				.jsonPath("$.name")
				.isEqualTo(authorDto.getName());

	}

	@Test
	void testDeleteAuthor_success()
	{
		Author savedAuthor = authorRepository.save(author);
		webTestClient.delete()
				.uri("/authors/{id}",savedAuthor.getId())
				.exchange()
				.expectStatus().isNoContent();
	}

	@Test
	void testDeleteAuthor_failure()
	{
		webTestClient.delete()
				.uri("/authors/{id}",1)
				.exchange()
				.expectStatus().is5xxServerError();
	}

	@Test
	void testUpdateAuthorById_success()
	{
		Author savedAuthor = authorRepository.save(author);
		authorDto.setName("Srty");
		webTestClient.put()
				.uri("/authors/{id}",savedAuthor.getId())
				.bodyValue(authorDto)
				.exchange()
				.expectStatus().isOk()
				.expectBody()
				.jsonPath("$.name")
				.isEqualTo(authorDto.getName());

	}
}

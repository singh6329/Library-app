package com.example.LibraryManagementSystem;

import com.example.LibraryManagementSystem.services.DataService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@RequiredArgsConstructor
public class LibraryManagementSystemApplication {

//	private final DataService dataService;

	public static void main(String[] args) {

		SpringApplication.run(LibraryManagementSystemApplication.class, args);
	}

//	@Override
//	public void run(String... args) throws Exception {
//		System.out.println("Data is:"+dataService.getData());
//	}
}

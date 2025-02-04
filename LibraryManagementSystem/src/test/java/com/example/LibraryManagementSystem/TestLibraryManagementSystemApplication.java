package com.example.LibraryManagementSystem;

import org.springframework.boot.SpringApplication;

public class TestLibraryManagementSystemApplication {

	public static void main(String[] args) {
		SpringApplication.from(LibraryManagementSystemApplication::main).with(TestContainerConfiguration.class).run(args);
	}

}

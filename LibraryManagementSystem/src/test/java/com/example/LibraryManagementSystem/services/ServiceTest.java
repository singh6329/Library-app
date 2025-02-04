package com.example.LibraryManagementSystem.services;

import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Import;
import org.testcontainers.utility.TestcontainersConfiguration;

@ExtendWith(MockitoExtension.class)
//@Import(TestcontainersConfiguration.class)
public abstract class ServiceTest {
    @Spy
    ModelMapper modelMapper;
}

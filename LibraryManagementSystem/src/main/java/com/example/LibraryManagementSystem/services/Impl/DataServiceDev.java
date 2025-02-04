package com.example.LibraryManagementSystem.services.Impl;

import com.example.LibraryManagementSystem.services.DataService;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

@Service
@Profile("dev")
public class DataServiceDev implements DataService {
    @Override
    public String getData() {
        return "Dev data";
    }
}

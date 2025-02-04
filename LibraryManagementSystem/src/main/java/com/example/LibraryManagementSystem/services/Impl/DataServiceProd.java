package com.example.LibraryManagementSystem.services.Impl;

import com.example.LibraryManagementSystem.services.DataService;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

@Service
@Profile("prod")
public class DataServiceProd implements DataService {
    @Override
    public String getData() {
        return "Prod data";
    }
}

package com.example.di;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
public class MyDatabaseService {
    public final CrudRepository simpleRepository;
    public final CrudRepository complexRepository;

    @Autowired
    public MyDatabaseService(
            @Qualifier("simpleCrudRepositoryImpl") CrudRepository simpleRepository,
            @Qualifier("complexCrudRepositoryImpl") CrudRepository complexRepository) {
        this.simpleRepository = simpleRepository;
        this.complexRepository = complexRepository;
    }
}


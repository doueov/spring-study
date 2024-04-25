package com.example.di;

import org.springframework.stereotype.Repository;

@Repository
public class ComplexCrudRepositoryImpl implements CrudRepository {
    @Override
    public Object[] getAll(Object o) {
        return new Object[0];
    }

    @Override
    public void save(Object o) {

    }

    @Override
    public void delete(int id) {

    }

    @Override
    public void update(Object o) {

    }
}

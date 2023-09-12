package com.diatoz.repository;

import com.diatoz.Model.Marks;

import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MarksRepository extends CrudRepository<Marks, String> {





}

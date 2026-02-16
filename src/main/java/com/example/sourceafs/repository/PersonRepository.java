package com.example.sourceafs.repository;

import com.example.sourceafs.entity.Person;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PersonRepository extends JpaRepository<Person, Long> {
}

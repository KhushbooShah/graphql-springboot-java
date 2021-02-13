package com.springboot.demo.graphql.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.springboot.demo.graphql.entity.Book;

public interface BookRepository extends JpaRepository<Book, String> {

}

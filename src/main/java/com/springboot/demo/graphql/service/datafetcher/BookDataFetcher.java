package com.springboot.demo.graphql.service.datafetcher;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.springboot.demo.graphql.entity.Book;
import com.springboot.demo.graphql.repository.BookRepository;

import graphql.schema.DataFetcher;
import graphql.schema.DataFetchingEnvironment;

@Component
public class BookDataFetcher implements DataFetcher<Book>{
	
	@Autowired
	BookRepository bookRepository;

	@Override
	public Book get(DataFetchingEnvironment dataFetchingEnvironment) {
		String isn = dataFetchingEnvironment.getArgument("id");
		return bookRepository.findById(isn).orElse(null);
	}

}

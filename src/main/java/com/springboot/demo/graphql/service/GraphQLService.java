package com.springboot.demo.graphql.service;



import java.io.File;
import java.io.IOException;
import java.util.stream.Stream;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import com.springboot.demo.graphql.entity.Book;
import com.springboot.demo.graphql.repository.BookRepository;
import com.springboot.demo.graphql.service.datafetcher.AllBooksDataFetcher;
import com.springboot.demo.graphql.service.datafetcher.BookDataFetcher;

import graphql.GraphQL;
import graphql.schema.GraphQLSchema;
import graphql.schema.idl.RuntimeWiring;
import graphql.schema.idl.SchemaGenerator;
import graphql.schema.idl.SchemaParser;
import graphql.schema.idl.TypeDefinitionRegistry;

@Service
public class GraphQLService {

	private static Logger logger = LoggerFactory.getLogger(GraphQLService.class);
	
	@Autowired
	private BookRepository bookRepository;
	
	@Autowired
	private AllBooksDataFetcher allBooksDataFetcher;
	
	@Autowired
	private BookDataFetcher bookDataFetcher;
	
	private GraphQL graphQL;
	
	@Value("classpath:books.graphqls")
    Resource resource;
	
	@PostConstruct
	private void loadSchema() throws IOException{
		logger.info("Entered in loadSchema method of GraphQLService");
		//method to load data in db
		loadDataInHSQL();
		
		//Get graphql schema file
		File file = resource.getFile();
		
		//Parse schema
		TypeDefinitionRegistry definitionRegistry = new SchemaParser().parse(file);
		RuntimeWiring runtimeWiring = buildRunTimeWiring();
		GraphQLSchema graphQLSchema = new SchemaGenerator()
				.makeExecutableSchema(definitionRegistry, runtimeWiring);
		graphQL = GraphQL.newGraphQL(graphQLSchema).build();
		
	}
	
	private RuntimeWiring buildRunTimeWiring(){
		return RuntimeWiring.newRuntimeWiring()
				.type("Query", typeWiring -> typeWiring
				.dataFetcher("allBooks", allBooksDataFetcher)
				.dataFetcher("book", bookDataFetcher))
				.build();
	}
	
	private void loadDataInHSQL(){
		logger.info("Loading data in in-memory db");
		Stream.of(
                new Book("1001", "The C Programming Language", "PHI Learning", "1978",
                        new String[] {
                                "Brian W. Kernighan (Contributor)",
                                "Dennis M. Ritchie"
                }),
                new Book("1002","Your Guide To Scrivener", "MakeUseOf.com", " April 21st 2013",
                        new String[] {
                                "Nicole Dionisio (Goodreads Author)"
                        }),
                new Book("1003","Beyond the Inbox: The Power User Guide to Gmail", " Kindle Edition", "November 19th 2012",
                        new String[] {
                                "Shay Shaked"
                                ,  "Justin Pot"
                                , "Angela Randall (Goodreads Author)"
                        }),
                new Book("1004","Scratch 2.0 Programming", "Smashwords Edition", "February 5th 2015",
                        new String[] {
                                "Denis Golikov (Goodreads Author)"
                        }),
                new Book("1005","Pro Git", "by Apress (first published 2009)", "2014",
                        new String[] {
                                "Scott Chacon"
                        })
        ).forEach(book -> {
            bookRepository.save(book);
        });
	}
	
	public GraphQL getGraphql(){
		return graphQL;
	}
}

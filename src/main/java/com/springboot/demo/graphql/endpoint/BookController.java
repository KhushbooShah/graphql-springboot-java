package com.springboot.demo.graphql.endpoint;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.springboot.demo.graphql.service.GraphQLService;

import graphql.ExecutionResult;

@RestController
@RequestMapping("/rest/books")
public class BookController {
	private static Logger logger = LoggerFactory.getLogger(BookController.class);
	
	@Autowired
	GraphQLService graphqlService;
	
	@PostMapping
	public ResponseEntity<Object> getAllBooks(@RequestBody String query){
		logger.info("Inside getAllBooks API");
		ExecutionResult result = graphqlService.getGraphql().execute(query);
		return new ResponseEntity<>(result, HttpStatus.OK);
	}
}

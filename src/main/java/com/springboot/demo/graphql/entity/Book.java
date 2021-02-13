package com.springboot.demo.graphql.entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table
@NoArgsConstructor
@Data
@AllArgsConstructor
public class Book {
	
	  @Id
	    private String isn;
	    private String title;
	    private String publisher;
	    private String publishedDate;
	    private String[] author;

}

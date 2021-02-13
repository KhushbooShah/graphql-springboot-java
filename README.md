#Demo springboot application to use graphql  
#To run the query:   
	1. Import the project in any IDE  
	2. Do mvn install  
	3. Start the application (It starts on default port 8080)  
	4. Open postman and hit following GET endpoint "http://localhost:8080/rest/books" with following request body:  
			```
			
			{
			book(id:"1001"){
				title,
				isn
				}
			}
			
			```

package com.library.Library;



import java.io.Reader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;

import org.json.JSONObject;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.io.File;


@Configuration
class LoadDatabase {
  @Bean
  CommandLineRunner initDatabase(UserLibRepository user_repository, BookRepository book_repository, LoanRepository loan_repository, 
		  PenaltyRepository penalty_repository, LibraryPassRepository librarypass_repository) {
    return args -> {
    	try {	
    			System.out.println("Loading data ...");
    			String file_resource = "src/main/java/com/library/Library/resources/";
    			Gson gson = new GsonBuilder().create();
    			
    			// Data from UserLibs
    	        Path path = new File(file_resource+"users.json").toPath();
    	        Reader reader = Files.newBufferedReader(path, StandardCharsets.UTF_8);
    	        List<UserLib> list_user =  Arrays.asList(gson.fromJson(reader, UserLib[].class));
    	        for(UserLib userr: list_user) {
    	        		user_repository.save(userr);
    	        	}
    	        
    	        // Data from Loans
    	        path = new File(file_resource+"loans.json").toPath();
    	        reader = Files.newBufferedReader(path, StandardCharsets.UTF_8);
    	        List<Loan> list_loan =  Arrays.asList(gson.fromJson(reader, Loan[].class));
        	    for(Loan loan: list_loan) {
        	    	loan_repository.save(loan);
        	    }
        	    
        	    // Data from Penalties
    	        path = new File(file_resource+"penalties.json").toPath();
    	        reader = Files.newBufferedReader(path, StandardCharsets.UTF_8);
    	        List<Penalty> list_penalties =  Arrays.asList(gson.fromJson(reader, Penalty[].class));
    	        for(Penalty penalty: list_penalties) {
        	    	penalty_repository.save(penalty);
        	    }
    	        
    	        // Data from Penalties
    	        path = new File(file_resource+"librarypass.json").toPath();
    	        reader = Files.newBufferedReader(path, StandardCharsets.UTF_8);
    	        List<LibraryPass> list_librarypass =  Arrays.asList(gson.fromJson(reader, LibraryPass[].class));
    	        for(LibraryPass libpass: list_librarypass) {
    	        	librarypass_repository.save(libpass);
        	    }
    	      
    	        // Data from Books
    	        path = new File(file_resource+"books.json").toPath();
    	        reader = Files.newBufferedReader(path, StandardCharsets.UTF_8);
    	        List<Book> list_books =  Arrays.asList(gson.fromJson(reader, Book[].class));
    	        /*
    	        for(Book book: list_books) {
        	    	book_repository.save(book);
        	    }
    	        	*/
    	        // Data from books
    	    	for(Book book: list_books) {
    	    		WebClient webClient = WebClient.create();
    	    		String response = webClient.get()
    	    		                               .uri("https://www.googleapis.com/books/v1/volumes?q=isbn:"+book.getIsbn())
    	    		                               .retrieve()
    	    		                               .bodyToMono(String.class)
    	    		                               .block();
    	    		JSONObject json_response = new JSONObject(response);
    	    		JSONObject json_items = json_response.getJSONArray("items").getJSONObject(0);
    	    		JSONObject json_volumeInfo = json_items.getJSONObject("volumeInfo");
    	    		JSONObject json_saleInfo = json_items.getJSONObject("saleInfo");
    	    		JSONObject aux; 
    	    		// Get values title, author and publisher
    	    		if(null!=json_volumeInfo.getString("title")) {
    	    			book.setTitle(json_volumeInfo.getString("title"));
    	    		}
    	    		if(null!=json_volumeInfo.getString("authors")) {
    	    			String authors = json_volumeInfo.getJSONArray("authors").get(0).toString();
    	    			if (authors.length() > 200) {
    	    				int posicion = authors.indexOf(",", 150);
    	    				String short_author = authors.substring(0, posicion);
    	    				book.setAuthor(short_author);
    	    			}
    	    			else {
    	    				book.setAuthor(authors);
    	    			}
    	    			
    	    		}
    	    		if(null!=json_volumeInfo.getString("description")) {
    	    			String description = json_volumeInfo.getString("description").replaceAll("\"\"", " ") ;
    	    			if (description.length() > 200) {
    	    				int posicion = description.indexOf(" ", 200);
    	    				String short_description = description.substring(0, posicion);
    	    				book.setDescription(short_description);
    	    			}
    	    			else {
    	    				book.setDescription(description);
    	    			}
    	    
    	    		}
    	    		if(null!=json_volumeInfo.getString("publisher")) {
    	    			book.setPublisher(json_volumeInfo.getString("publisher"));
    	    		}
    	    		
    	    		if(null!=json_volumeInfo.getString("publishedDate")) {
    	    			book.setPublishedDate(json_volumeInfo.getString("publishedDate"));
    	    		}
    	    		
    	    	   	    		
    	    		//Get values imageLinks
    	    		aux = json_volumeInfo.getJSONObject("imageLinks");
    	    		if(null!=aux.getString("thumbnail")) {
    	    			book.setImageLinks(aux.getString("thumbnail"));
    	    		}

    	    		//Get values buyLink
    	    		if(0 == json_saleInfo.getString("saleability").compareTo("FOR_SALE")) {
    	    			book.setBuyLink(json_saleInfo.getString("buyLink"));
    	    		
    	    	}
    	    		book_repository.save(book);
    	    		
    	   }
    	    	
    	    	System.out.println("Data has been loaded ...");
    	} catch (Exception e) {
    	    e.printStackTrace();
    	}	  
    };
  }
}

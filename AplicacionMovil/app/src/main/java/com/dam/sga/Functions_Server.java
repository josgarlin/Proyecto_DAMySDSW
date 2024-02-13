package com.dam.sga;



import java.util.HashMap;
import java.util.Map;
import java.util.List;

import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import org.springframework.http.*;
import org.springframework.core.*;
import java.util.Arrays;
import org.springframework.web.util.UriComponents;
import org.springframework.http.converter.json.MappingJacksonHttpMessageConverter;
import org.springframework.http.client.CommonsClientHttpRequestFactory;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import java.util.Date;
import java.time.LocalDate;

import java.time.format.DateTimeFormatter;
public class Functions_Server {

    private static final String URL_BASE = "http://192.168.0.33:8080/";
    
     private static final String URL_CHECK_PASSWORD = "user/check?";
      private static final String URL_GET_USER = "user?";

      private static final String URL_GET_BOOKS = "books";
      private static final String URL_GET_BOOK = "book?";
    private static final String URL_CHECK_LIBRARY = "return/check";
      
       private static final String URL_GET_LOAN = "loan?";
      private static final String URL_POST_LOAN = "loan";
       private static final String URL_DELETE_LOAN = "loan";
       
      private static final String URL_GET_PENALTY = "penalty?";
        private static final String URL_POST_PENALTY = "penalty";
          private static final String URL_DELETE_PENALTY = "penalty";
         
   public static final RestTemplate restTemplate = new RestTemplate();

    //*******USER
    public static boolean check_password(String dni, String password){
        boolean result = false;
        try {

            UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(URL_BASE + URL_CHECK_PASSWORD)
                    .queryParam("dni", dni)
                    .queryParam("password", password);
            UriComponents component = builder.build().encode();
            restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());

            if (restTemplate.getForObject(component.toUriString(), boolean.class)) {
                result = true;
             }
    } catch (Exception e) {
        System.out.println("Error: " + e);
    }
        return result;
    }
    public static UserLib getUserLib(String dni){

        UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(URL_BASE + URL_GET_USER)
        	.queryParam("dni", dni);
        UriComponents component = builder.build().encode();
        restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
        UserLib result = restTemplate.getForObject(component.toUriString(), UserLib.class);
 		 	return result;
    }

    //*******BOOK

    
    
  	public static List <Book>  getBooks(String isbn, String author, String title){
    		UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(URL_BASE + URL_GET_BOOK)
    		.queryParam("isbn",isbn)
			.queryParam("author",author)
			.queryParam("title",title);
    		UriComponents component = builder.build().encode();
        restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
			ResponseEntity<Book[]> response = restTemplate.getForEntity(component.toUri(), Book[].class);
			List<Book> aux =  Arrays.asList(response.getBody());
 		 	return aux;

    }

    public static boolean checkLibraryPass(String university, String password){
        UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(URL_BASE + URL_CHECK_LIBRARY)
                .queryParam("university", university)
                .queryParam("password", password);
        UriComponents component = builder.build().encode();
        restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
        boolean aux = restTemplate.getForObject(component.toUriString(),boolean.class);
        return aux;
    }
    
    //*******LOAN
     public static List <Loan>  getLoan(String dni){
    		UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(URL_BASE + URL_GET_LOAN)
    		.queryParam("dni", dni);
    		UriComponents component = builder.build().encode();
         restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
			ResponseEntity<Loan[]> response = restTemplate.getForEntity(component.toUri(), Loan[].class);
			List<Loan> aux =  Arrays.asList(response.getBody());  	
 		 	return aux;
    }
      
    public static boolean postLoan(String dni, String isbn, String location){
        boolean result = false;
        UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(URL_BASE + URL_POST_LOAN)
        	.queryParam("dni", dni)
        	.queryParam("isbn", isbn)
        	.queryParam("location", location);
            UriComponents component = builder.build().encode();
        restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
        if (restTemplate.postForObject(component.toUriString(), true ,boolean.class)){
            result = true;
            System.out.println("Préstamo añadido");
        }
        return result;
    }
    
    public static void deleteLoan(String dni, String isbn){

        UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(URL_BASE + URL_DELETE_LOAN)
        	.queryParam("dni", dni)
        	.queryParam("isbn", isbn);
        UriComponents component = builder.build().encode();
        restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
        restTemplate.delete(component.toUriString());
    }
      
      //*******PENALTY
    public static Penalty getPenalty(String dni){
    		UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(URL_BASE + URL_GET_PENALTY)
        .queryParam("dni", dni);
        UriComponents component = builder.build().encode();
        restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
 		 	Penalty aux = restTemplate.getForObject(component.toUriString(),Penalty.class);
 		 	return aux;
    }


}
   
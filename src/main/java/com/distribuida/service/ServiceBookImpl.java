package com.distribuida.service;


import com.distribuida.dto.Book;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.ws.rs.client.Client;
import jakarta.ws.rs.client.ClientBuilder;
import jakarta.ws.rs.client.Entity;
import jakarta.ws.rs.core.MediaType;

import java.util.Arrays;
import java.util.List;

@ApplicationScoped
public class ServiceBookImpl implements ServiceBook{
    @Override
    public List<Book> findAll() {
        String url = "http://traefik/books/";
        Client client = ClientBuilder.newClient();
        Book[] books= client.target(url)
                .request()
                .header("Accept","application/json")
                .get(Book[].class);

        return Arrays.asList(books);
    }



    @Override
    public Book findById(int id) {
        String url = "http://traefik/books/"+id;
        Client client = ClientBuilder.newClient();
        Book book = client.target(url)
                .request(MediaType.APPLICATION_JSON)
                .get(Book.class);

        return book;
    }

    @Override
    public void deleteBook(int id) {
        String url = "http://traefik/books/"+id;
        Client client = ClientBuilder.newClient();
        client.target(url).request().delete();
    }

    @Override
    public void updateBook(int id, Book book) {
        String url = "http://traefik/books/"+id;
        Client client = ClientBuilder.newClient();
        Entity<Book> entity = Entity.entity(book, MediaType.APPLICATION_JSON);
        client.target(url).request().put(entity);

    }

    @Override
    public void createBook(Book book) {
        String url = "http://traefik/books/";
        Client client = ClientBuilder.newClient();
        Entity<Book> entity = Entity.entity(book, MediaType.APPLICATION_JSON);
        client.target(url).request().post(entity);
    }


}

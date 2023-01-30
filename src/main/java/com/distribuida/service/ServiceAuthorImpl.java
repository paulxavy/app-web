package com.distribuida.service;


import com.distribuida.dto.Author;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.ws.rs.client.Client;
import jakarta.ws.rs.client.ClientBuilder;
import jakarta.ws.rs.client.Entity;
import jakarta.ws.rs.core.MediaType;

import java.util.Arrays;
import java.util.List;

@ApplicationScoped
public class ServiceAuthorImpl implements ServiceAuthor{

    @Override
    public List<Author> findAll() {
        String url = "http://traefik/authors/";
        Client client = ClientBuilder.newClient();
        Author[] authors= client.target(url)
                .request()
                .header("Accept","application/json")
                .get(Author[].class);

        return Arrays.asList(authors);
    }


    @Override
    public Author findById(Long id) {
        String url = "http://traefik/authors/"+id;
        Client client = ClientBuilder.newClient();
        Author author = client.target(url)
                .request(MediaType.APPLICATION_JSON)
                .get(Author.class);

        return author;
    }

    @Override
    public void deleteAuthor(Long id) {
        String url = "http://traefik/authors/"+id;
        Client client = ClientBuilder.newClient();
        client.target(url).request().delete();
    }

    @Override
    public void updateAuthor(Long id, Author album) {
        String url = "http://traefik/authors/"+id;
        Client client = ClientBuilder.newClient();
        Entity<Author> entity = Entity.entity(album, MediaType.APPLICATION_JSON);
        client.target(url).request().put(entity);

    }

    @Override
    public void createAuthor(Author author) {
        String url = "http://traefik/authors/";
        Client client = ClientBuilder.newClient();
        Entity<Author> entity = Entity.entity(author, MediaType.APPLICATION_JSON);
        client.target(url).request().post(entity);
    }

}

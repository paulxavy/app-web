package com.distribuida.service;

import com.distribuida.dto.Author;
import com.distribuida.dto.Book;

import java.util.List;

public interface ServiceBook {
    List<Book> findAll();

    Book findById(int id);
    void deleteBook(int id);
    void updateBook(int id, Book book);
    void createBook( Book book);


}

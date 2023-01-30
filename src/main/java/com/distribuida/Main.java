package com.distribuida;

import com.distribuida.dto.Author;
import com.distribuida.dto.Book;
import com.distribuida.service.ServiceAuthor;
import com.distribuida.service.ServiceBook;
import jakarta.enterprise.inject.Instance;
import jakarta.enterprise.inject.se.SeContainer;
import jakarta.enterprise.inject.se.SeContainerInitializer;
import spark.ModelAndView;
import spark.template.thymeleaf.ThymeleafTemplateEngine;


import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static spark.Spark.*;

public class Main {

    static SeContainer container;

    public static void main(String[] args) {
        container = SeContainerInitializer.newInstance().initialize();

        rutearBook();
        rutearAuthor();

    }

    public static void rutearAuthor(){
        ThymeleafTemplateEngine engine = new ThymeleafTemplateEngine();
        Instance<ServiceAuthor> obje = container.select(ServiceAuthor.class);
        ServiceAuthor servicioAuthor = obje.get();



        get("/authors", (req, res) -> {
                    List<Author> authors = servicioAuthor.findAll();
                    Map<String, Object> model = new HashMap<>();
                    model.put("authors", authors);
                    return engine.render(new ModelAndView(model, "Authors"));
                }
        );

        get("/author/delete", (req, res) -> {
                    Long id = Long.parseLong(req.queryParams("id"));
                    servicioAuthor.deleteAuthor(id);
                    res.redirect("/authors");
                    return null;
                }
        );

        get("/author/formularioInsertar", (req, res) -> {
                    Map<String, Object> model = new HashMap<>();
                    return engine.render(new ModelAndView(model, "FormInsertarAuthor"));
                }
        );

        post("/author/add", (req, res) -> {
                    Author author = new Author();

                    String body = req.body();
                    System.out.println(body);
                    String[] cadena = body.split("&");

                    String[] datos = cadena[0].split("=");
                    author.setFirst_name(datos[1]);

                    datos = cadena[1].split("=");
                    author.setLast_name(datos[1]);
                    author.setId(null);
                    servicioAuthor.createAuthor(author);

                    res.redirect("/authors");
                    return null;
                }
        );

        get("/author/formularioModificar", (req, res) -> {
                    Author author = new Author();
                    Long id = Long.parseLong(req.queryParams("id"));
                    author = servicioAuthor.findById(id);
                    Map<String, Object> model = new HashMap<>();
                    model.put("author", author);
                    return engine.render(new ModelAndView(model, "FormModificarAuthor"));
                }
        );


        post("/author/modificar", (req, res) -> {
                    Author author = new Author();

                    String body = req.body();
                    System.out.println(body);
                    String[] cadena = body.split("&");


                    String[] datos = cadena[0].split("=");
                    author.setId(Long.parseLong(datos[1]));

                    datos = cadena[1].split("=");
                    author.setFirst_name(datos[1]);

                    datos = cadena[2].split("=");
                    author.setLast_name(datos[1]);

                    servicioAuthor.updateAuthor(author.getId(),author);
                    res.redirect("/authors");
                    return null;
                }
        );


    }
    public static void rutearBook() {

        ThymeleafTemplateEngine engine = new ThymeleafTemplateEngine();
        Instance<ServiceBook> obj = container.select(ServiceBook.class);
        ServiceBook serviceBook = obj.get();
        get("/books", (req, res) -> {
                    List<Book> books = serviceBook.findAll();
                    Map<String, Object> model = new HashMap<>();
                    model.put("books", books);
                    return engine.render(new ModelAndView(model, "Books"));
                }
        );

        get("/book/borrar", (req, res) -> {
                    int id = Integer.parseInt(req.queryParams("id"));
            serviceBook.deleteBook(id);
                    res.redirect("/books");
                    return null;
                }
        );

        get("/book/formularioInsertar", (req, res) -> {
                    Map<String, Object> model = new HashMap<>();
                    return engine.render(new ModelAndView(model, "FormInsertarBook"));
                }
        );


        post("/book/add", (req, res) -> {
                    Book book = new Book();
                    int id;
                    BigDecimal price;
                    String body = req.body();
                    System.out.println(body);
                    String[] cadena = body.split("&");


                    String[] datos = cadena[0].split("=");

                    id = Integer.parseInt(datos[1]);
                    book.setAuthor_id(id);

                    datos = cadena[1].split("=");
                    book.setIsbn(datos[1]);

                    datos = cadena[2].split("=");
                    book.setTitle(datos[1]);

                    datos = cadena[3].split("=");
                    price = BigDecimal.valueOf(Double.valueOf((datos[1])));
                    book.setPrice(price);


                    serviceBook.createBook(book);
                    res.redirect("/books");
                    return null;
                }
        );

        get("/book/formularioModificar", (req, res) -> {
                    Book book ;
                    int id = Integer.parseInt(req.queryParams("id"));
                    book = serviceBook.findById(id);
                    Map<String, Object> model = new HashMap<>();
                    model.put("book", book);
                    return engine.render(new ModelAndView(model, "FormModificarBook"));
                }
        );


        post("book/modificar", (req, res) -> {
                    Book book = new Book();
                    int id;
                    BigDecimal price;
                    String body = req.body();
                    System.out.println(body);
                    String[] cadena = body.split("&");


                    String[] datos = cadena[0].split("=");
                    id = Integer.parseInt(datos[1]);
                    book.setId(id);

                    datos = cadena[1].split("=");
                    book.setAuthor_id(Integer.parseInt(datos[1]));

                    datos = cadena[2].split("=");
                    book.setIsbn(datos[1]);

                    datos = cadena[3].split("=");
                    book.setTitle(datos[1]);

                    datos = cadena[4].split("=");
                    price = BigDecimal.valueOf(Double.valueOf((datos[1])));
                    book.setPrice(price);

                    serviceBook.updateBook(book.getId(), book);
                    res.redirect("/books");
                    return null;
                }
        );
    }

}

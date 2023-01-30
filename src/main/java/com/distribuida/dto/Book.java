package com.distribuida.dto;

import jakarta.ws.rs.Consumes;
import jakarta.xml.bind.annotation.XmlRootElement;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Consumes("application/json")
public class Book {
    private int id;
    private int author_id;
    private String isbn;
    private String title;
    private BigDecimal price;
}

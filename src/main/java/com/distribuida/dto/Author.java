package com.distribuida.dto;

import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.core.MediaType;
import jakarta.xml.bind.annotation.XmlRootElement;
import lombok.Data;

@Data
@Consumes("application/json")
public class Author {
    private Long id;
    private String first_name;
    private String last_name;
}

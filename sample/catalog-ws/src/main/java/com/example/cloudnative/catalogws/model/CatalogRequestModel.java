package com.example.cloudnative.catalogws.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.util.Date;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CatalogRequestModel {
    private String productId;
    private String productName;
    private Integer stock;
    private Integer unitPrice;
}

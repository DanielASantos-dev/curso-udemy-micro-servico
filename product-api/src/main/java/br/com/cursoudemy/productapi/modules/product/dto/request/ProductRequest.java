package br.com.cursoudemy.productapi.modules.product.dto.request;

import br.com.cursoudemy.productapi.modules.category.dto.response.CategoryResponse;
import br.com.cursoudemy.productapi.modules.supplier.dto.response.SupplierResponse;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ProductRequest {

    private String name;

    @JsonProperty("quantity_available")
    private Integer quantityAvailable;

    private Integer supplierId;

    private Integer categoryId;

}

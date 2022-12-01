package br.com.cursoudemy.productapi.modules.product.controller;

import br.com.cursoudemy.productapi.modules.product.dto.request.ProductRequest;
import br.com.cursoudemy.productapi.modules.product.dto.response.ProductResponse;
import br.com.cursoudemy.productapi.modules.product.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/product")
public class ProductController {

    @Autowired
    ProductService productService;

    @PostMapping
    public ProductResponse save(@RequestBody ProductRequest request){
        return  productService.save(request);
    }

    @PutMapping
    public ProductResponse update(@PathVariable Integer id, @RequestBody ProductRequest request){
        return  productService.update(request, id);
    }

    @GetMapping
    public List<ProductResponse> findAll(){
        return productService.findAll();
    }

    @GetMapping("/name/{name}")
    public List<ProductResponse> findName(@PathVariable String name){
        return productService.findByName(name);
    }

    @GetMapping("/supplier/{idSupplier}")
    public List<ProductResponse> findBySupplierId(@PathVariable Integer idSupplier){
        return productService.findBySupplierId(idSupplier);
    }

    @GetMapping("/category/{idCategory}")
    public List<ProductResponse> findByCategoryId(@PathVariable Integer idCategory){
        return productService.findByCategoryId(idCategory);
    }

    @GetMapping("/{id}")
    public ProductResponse findById(@PathVariable Integer id){
        return productService.findByIdResponse(id);
    }
}

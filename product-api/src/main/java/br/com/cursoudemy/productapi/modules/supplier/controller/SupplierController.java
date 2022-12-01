package br.com.cursoudemy.productapi.modules.supplier.controller;

import br.com.cursoudemy.productapi.modules.supplier.dto.request.SupplierRequest;
import br.com.cursoudemy.productapi.modules.supplier.dto.response.SupplierResponse;
import br.com.cursoudemy.productapi.modules.supplier.service.SupplierService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/supplier")
public class SupplierController {

    @Autowired
    private SupplierService supplierService;

    @PostMapping
    public SupplierResponse save(@RequestBody SupplierRequest request){
        return supplierService.save(request);
    }

    @PostMapping("{id}")
    public SupplierResponse update(@PathVariable Integer id, @RequestBody SupplierRequest request){
        return supplierService.update(request, id);
    }

    @GetMapping("/{id}")
    public SupplierResponse findById(@PathVariable Integer id){
        return supplierService.findByIdResponse(id);
    }

    @GetMapping("/name/{name}")
    public List<SupplierResponse> findByName(@PathVariable String name){
        return supplierService.findByName(name);
    }

    @GetMapping
    public List<SupplierResponse> findAll(){
        return supplierService.findAll();
    }
}

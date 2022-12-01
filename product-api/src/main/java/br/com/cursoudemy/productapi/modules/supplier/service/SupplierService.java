package br.com.cursoudemy.productapi.modules.supplier.service;

import br.com.cursoudemy.productapi.config.exception.SuccessResponse;
import br.com.cursoudemy.productapi.config.exception.ValidationException;
import br.com.cursoudemy.productapi.modules.product.service.ProductService;
import br.com.cursoudemy.productapi.modules.supplier.dto.request.SupplierRequest;
import br.com.cursoudemy.productapi.modules.supplier.dto.response.SupplierResponse;
import br.com.cursoudemy.productapi.modules.supplier.model.Supplier;
import br.com.cursoudemy.productapi.modules.supplier.repository.SupplierRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.util.ObjectUtils.isEmpty;

@Service
public class SupplierService {


    @Autowired
    private SupplierRepository supplierRepository;

    @Autowired
    private ProductService productService;

    public Supplier findById(Integer id){
        validateInformedId(id);
        return supplierRepository
                .findById(id)
                .orElseThrow(() -> new ValidationException("There's no supplier for the given ID."));
    }

    public SupplierResponse save(SupplierRequest request){
        validateSupplierNameInformed(request);
        Supplier supplier = supplierRepository.save(Supplier.of(request));
        return  SupplierResponse.of(supplier);
    }

    public SupplierResponse update(SupplierRequest request, Integer id){
        validateInformedId(id);
        validateSupplierNameInformed(request);
        Supplier supplier = Supplier.of(request);
        supplier.setId(id);
        supplierRepository.save(supplier);
        return  SupplierResponse.of(supplier);
    }

    private void validateSupplierNameInformed(SupplierRequest request){
        if(isEmpty(request.getName())){
            throw  new ValidationException("The supplier name was not informed");
        }
    }

    public List<SupplierResponse> findByName(String name){
        return supplierRepository
                .findByNameIgnoreCaseContaining(name)
                .stream()
                .map(SupplierResponse::of)
                .collect(Collectors.toList());
    }

    public List<SupplierResponse> findAll(){
        return supplierRepository
                .findAll()
                .stream()
                .map(SupplierResponse::of)
                .collect(Collectors.toList());
    }

    public SupplierResponse findByIdResponse(Integer id){
        return SupplierResponse.of(findById(id));
    }

    public SuccessResponse delete(Integer id){
        validateInformedId(id);
        if(productService.existsBySupplierId(id)){
            throw  new ValidationException("You cannot this supplier because it's already defined by a product.");
        }
        supplierRepository.deleteById(id);
        return SuccessResponse.create("The supplier was deleted.");
    }

    private void validateInformedId(Integer id){
        if (isEmpty(id)) {
            throw  new ValidationException("The supplier ID must be informed");
        }
    }


}

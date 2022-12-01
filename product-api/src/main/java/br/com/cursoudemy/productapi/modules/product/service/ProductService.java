package br.com.cursoudemy.productapi.modules.product.service;

import br.com.cursoudemy.productapi.config.exception.SuccessResponse;
import br.com.cursoudemy.productapi.config.exception.ValidationException;
import br.com.cursoudemy.productapi.modules.category.service.CategoryService;
import br.com.cursoudemy.productapi.modules.product.dto.request.ProductRequest;
import br.com.cursoudemy.productapi.modules.product.dto.response.ProductResponse;
import br.com.cursoudemy.productapi.modules.product.model.Product;
import br.com.cursoudemy.productapi.modules.product.repository.ProductRepository;
import br.com.cursoudemy.productapi.modules.supplier.service.SupplierService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.util.ObjectUtils.isEmpty;

@Service
public class ProductService {


    final private static Integer ZERO = 0;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private SupplierService supplierService;

    @Autowired
    private CategoryService categoryService;


    public ProductResponse save(ProductRequest request){
        validateProductDateInformed(request);
        validateCategoryAndSupplierInformed(request);

        var category = categoryService.findById(request.getCategoryId());
        var supplier = supplierService.findById(request.getSupplierId());
        var product = productRepository.save(Product.of(request, supplier, category));
        return  ProductResponse.of(product);
    }

    public ProductResponse update(ProductRequest request, Integer id){
        validateInformedId(id);
        validateProductDateInformed(request);
        validateCategoryAndSupplierInformed(request);

        var category = categoryService.findById(request.getCategoryId());
        var supplier = supplierService.findById(request.getSupplierId());
        var product = Product.of(request, supplier, category);
        product.setId(id);
        productRepository.save(product);
        return  ProductResponse.of(product);
    }

    private void validateProductDateInformed(ProductRequest request){
        if(isEmpty(request.getName())){
            throw  new ValidationException("The product name was not informed");
        }

        if(isEmpty(request.getQuantityAvailable())){
            throw  new ValidationException("The product quantity was not informed");
        }

        if(request.getQuantityAvailable() <= ZERO){
            throw  new ValidationException("The quantity should not be less or equal to zero");
        }

    }

    private void validateCategoryAndSupplierInformed(ProductRequest request){
        if(isEmpty(request.getCategoryId())){
            throw  new ValidationException("The Category ID was not informed");
        }

        if(isEmpty(request.getSupplierId())){
            throw  new ValidationException("The Supplier ID was not informed");
        }
    }

    public List<ProductResponse> findAll(){
        return productRepository.findAll()
                .stream()
                .map(ProductResponse::of)
                .collect(Collectors.toList());
    }

    public List<ProductResponse> findByName(String name){
        return productRepository.findByNameIgnoreCaseContaining(name)
                .stream()
                .map(ProductResponse::of)
                .collect(Collectors.toList());
    }

    public List<ProductResponse> findByCategoryId(Integer idCategory){
        return productRepository.findByCategoryId(idCategory)
                .stream()
                .map(ProductResponse::of)
                .collect(Collectors.toList());
    }

    public List<ProductResponse> findBySupplierId(Integer idSupplier){
        return productRepository.findBySupplierId(idSupplier)
                .stream()
                .map(ProductResponse::of)
                .collect(Collectors.toList());
    }

    public ProductResponse findByIdResponse(Integer id){
        return ProductResponse.of(findById(id));
    }

    public Product findById(Integer id){
        validateInformedId(id);
        return productRepository.findById(id)
                .orElseThrow(()-> new ValidationException("There's no product for the given ID."));
    }

    public Boolean existsByCategoryId(Integer idCategory){
        return productRepository.existsByCategoryId(idCategory);
    }

    public Boolean existsBySupplierId(Integer idSupplier){
        return productRepository.existsByCategoryId(idSupplier);
    }

    public SuccessResponse delete(Integer id){
        validateInformedId(id);
        productRepository.deleteById(id);
        return SuccessResponse.create("The supplier was deleted.");
    }

    private void validateInformedId(Integer id){
        if (isEmpty(id)) {
            throw  new ValidationException("The product ID must be informed");
        }
    }

}

package br.unipar.frameworks.mapper;

import br.unipar.frameworks.dto.ProductRequest;
import br.unipar.frameworks.dto.ProductResponse;
import br.unipar.frameworks.model.Product;

public class ProductMapper {

    public static Product toEntity(ProductRequest request) {
        Product product = new Product();
        product.setName(request.name());
        product.setDescription(request.description());
        product.setPrice(request.price());
        return product;
    }

    public static ProductResponse toResponse(Product product) {
        return new ProductResponse(
                product.getId(),
                product.getName(),
                product.getDescription(),
                product.getPrice()
        );
    }

    public static void updateEntity(Product product, ProductRequest request) {
        product.setName(request.name());
        product.setDescription(request.description());
        product.setPrice(request.price());
    }
}
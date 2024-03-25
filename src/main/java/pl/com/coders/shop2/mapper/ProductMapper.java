package pl.com.coders.shop2.mapper;

import pl.com.coders.shop2.domain.CategoryType;
import pl.com.coders.shop2.domain.Product;
import pl.com.coders.shop2.dto.ProductDTO;

public class ProductMapper implements IMapper<Product, ProductDTO>{
    private final static ProductMapper instance = new ProductMapper();

    private ProductMapper(){}

    public static ProductMapper getInstance(){
        return instance;
    }

    @Override
    public Product toEntity(ProductDTO productDTO) {
        return Product.builder()
                .price(productDTO.getPrice())
                .quantity(productDTO.getQuantity())
                .description(productDTO.getDescription())
                .name(productDTO.getName())
                .build();
    }

    @Override
    public ProductDTO toDTO(Product product) {
        return ProductDTO.builder()
                .categoryType(CategoryType.valueOf(product.getCategory().getName()))
                .created(product.getCreated())
                .description(product.getDescription())
                .price(product.getPrice())
                .quantity(product.getQuantity())
                .updated(product.getUpdated())
                .name(product.getName())
                .build();
    }
}

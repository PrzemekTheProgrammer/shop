package pl.com.coders.shop2.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.com.coders.shop2.domain.CategoryType;
import pl.com.coders.shop2.domain.Product;
import pl.com.coders.shop2.dto.ProductDTO;
import pl.com.coders.shop2.mapper.ProductMapper;
import pl.com.coders.shop2.repository.CategoryRepository;
import pl.com.coders.shop2.repository.ProductRepository;

import java.util.List;

@Service
@AllArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;

    @Transactional
    public ProductDTO create(ProductDTO productDTO) {
        Product productToAdd = ProductMapper.getInstance().toEntity(productDTO);
        productToAdd.setCategory(categoryRepository.getByName(productDTO.getCategoryType().name()));
        return ProductMapper.getInstance().toDTO(productRepository.add(productToAdd));
    }

    @Transactional
    public ProductDTO get(Long id) {
        return ProductMapper.getInstance().toDTO(productRepository.get(id));
    }

    @Transactional
    public boolean delete(Long id) {
        return productRepository.delete(id);
    }

    @Transactional
    public ProductDTO update(ProductDTO productDTO, Long id) {
        Product productToUpdate = ProductMapper.getInstance().toEntity(productDTO);
        return ProductMapper.getInstance().toDTO(productRepository.update(productToUpdate,id));
    }

    @Transactional
    public List<ProductDTO> findAllProductsByCategory(CategoryType categoryType) {
        return productRepository.findAllProductsByCategory(categoryType).stream().map(product -> ProductMapper.getInstance().toDTO(product)).toList();
    }

}

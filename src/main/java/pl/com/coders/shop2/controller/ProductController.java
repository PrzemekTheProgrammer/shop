package pl.com.coders.shop2.controller;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import pl.com.coders.shop2.domain.CategoryType;
import pl.com.coders.shop2.dto.ProductDTO;
import pl.com.coders.shop2.service.ProductService;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/product")
public class ProductController {

    private final ProductService productService;

    @PostMapping
    public ProductDTO create(@RequestBody ProductDTO productDTO) {
        return productService.create(productDTO);
    }

    @GetMapping("/{id}")
    public ProductDTO get(@PathVariable Long id) {
        return productService.get(id);
    }

    @DeleteMapping
    public boolean delete(@RequestParam Long id) {
        return productService.delete(id);
    }

    @PutMapping("/{id}")
    public ProductDTO update(@RequestBody ProductDTO productDTO, @PathVariable Long id) {
        return productService.update(productDTO, id);
    }

    @GetMapping("/findByCategory/{category}")
    public List<ProductDTO> findAllProductsByCategory(@PathVariable String category) {
        CategoryType categoryType = CategoryType.valueOf(category);
        return productService.findAllProductsByCategory(categoryType);
    }

}

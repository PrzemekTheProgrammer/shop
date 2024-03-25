package pl.com.coders.shop2.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.com.coders.shop2.dto.CategoryDTO;
import pl.com.coders.shop2.mapper.CategoryMapper;
import pl.com.coders.shop2.repository.CategoryRepository;

import java.util.List;

@Service
@AllArgsConstructor
public class CategoryService {

    private final CategoryRepository categoryRepository;


    @Transactional
    public List<CategoryDTO> getCategories() {

        return categoryRepository.getCategories()
                .stream()
                .map(category -> CategoryMapper.getInstance().toDTO(category))
                .toList();
    }
}

package pl.com.coders.shop2.mapper;

import pl.com.coders.shop2.domain.Category;
import pl.com.coders.shop2.dto.CategoryDTO;

public class CategoryMapper implements IMapper<Category, CategoryDTO> {
    private static CategoryMapper instance = new CategoryMapper();

    private CategoryMapper() {
    }

    public static CategoryMapper getInstance() {
        return instance;
    }

    @Override
    public Category toEntity(CategoryDTO categoryDTO) {
        return null;
    }

    @Override
    public CategoryDTO toDTO(Category category) {
        return CategoryDTO.builder()
                .name(category.getName())
                .build();
    }
}

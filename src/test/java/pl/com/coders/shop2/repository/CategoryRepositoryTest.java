package pl.com.coders.shop2.repository;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import pl.com.coders.shop2.domain.Category;
import pl.com.coders.shop2.domain.CategoryType;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@DataJpaTest
@ComponentScan(basePackages = {"pl.com.coders.shop2.repository"})
public class CategoryRepositoryTest {

    @Autowired
    private CategoryRepository categoryRepository;

    @Test
    void addTest() {
        //given
        Category category = Category.builder()
                .name(CategoryType.ELEKTRONIKA.name())
                .build();
        //when
        category = categoryRepository.add(category);
        //then
        assertNotNull(category.getId());
        assertNotNull(category.getCreated());
        assertNotNull(category.getUpdated());
    }

    @Test
    void getByNameTest() {
        //given
        Category category = Category.builder()
                .name(CategoryType.ELEKTRONIKA.name())
                .build();
        categoryRepository.add(category);
        Category category2 = Category.builder()
                .name(CategoryType.MOTORYZACJA.name())
                .build();
        categoryRepository.add(category2);
        //when
        Category receivedCategory = categoryRepository.getByName(CategoryType.MOTORYZACJA.name());
        //then
        assertEquals(CategoryType.MOTORYZACJA.name(), receivedCategory.getName());
    }

    @Test
    void getCategoriesTest() {
        //given
        Category category = Category.builder()
                .name(CategoryType.ELEKTRONIKA.name())
                .build();
        categoryRepository.add(category);
        Category category2 = Category.builder()
                .name(CategoryType.MOTORYZACJA.name())
                .build();
        categoryRepository.add(category2);
        //when
        List<Category> categories = categoryRepository.getCategories();
        //then
        assertEquals(2, categories.size());
    }

}

package pl.com.coders.shop2.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.mockito.internal.verification.VerificationModeFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import pl.com.coders.shop2.domain.Category;
import pl.com.coders.shop2.domain.CategoryType;
import pl.com.coders.shop2.dto.CategoryDTO;
import pl.com.coders.shop2.dto.ProductDTO;
import pl.com.coders.shop2.mapper.CategoryMapper;
import pl.com.coders.shop2.service.CategoryService;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@AutoConfigureMockMvc
public class CategoryControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private CategoryService categoryService;

    @Test
    void getCategoriesTest() throws Exception {
        //given
        Category category1 = Category.builder()
                .name(CategoryType.ELEKTRONIKA.name())
                .build();
        Category category2 = Category.builder()
                .name(CategoryType.MOTORYZACJA.name())
                .build();
        List<CategoryDTO> categoryDTOs = new ArrayList<>();
        categoryDTOs.add(CategoryMapper.getInstance().toDTO(category1));
        categoryDTOs.add(CategoryMapper.getInstance().toDTO(category2));
        //when
        Mockito.when(categoryService.getCategories()).thenReturn(categoryDTOs);
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/category/all"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andReturn();
        String jsonResponse = mvcResult.getResponse().getContentAsString();
        List<CategoryDTO> responseCategoryDTOs = objectMapper.readValue(jsonResponse, new TypeReference<>() {
        });
        //then
        assertEquals(categoryDTOs,responseCategoryDTOs);
        assertEquals(2,responseCategoryDTOs.size());
        Mockito.verify(categoryService, VerificationModeFactory.times(1)).getCategories();
    }

}

package pl.com.coders.shop2.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
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
import pl.com.coders.shop2.domain.Product;
import pl.com.coders.shop2.dto.ProductDTO;
import pl.com.coders.shop2.mapper.ProductMapper;
import pl.com.coders.shop2.service.ProductService;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@AutoConfigureMockMvc
public class ProductControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private ProductService productService;

    @Test
    void createTest() throws Exception {
        //given
        Product product = createSampleProduct();
        ProductDTO productDTO = ProductMapper.getInstance().toDTO(product);
        //when
        Mockito.when(productService.create(ArgumentMatchers.any())).thenReturn(productDTO);
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/product")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(productDTO)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();
        String responseContent = result.getResponse().getContentAsString();
        ProductDTO responseProductDTO = objectMapper.readValue(responseContent, ProductDTO.class);
        //then
        assertEquals(productDTO, responseProductDTO);
        Mockito.verify(productService, VerificationModeFactory.times(1)).create(ArgumentMatchers.any());
    }

    @Test
    void getTest() throws Exception {
        //given
        Product product = createSampleProduct();
        ProductDTO productDTO = ProductMapper.getInstance().toDTO(product);
        //when
        Mockito.when(productService.get(ArgumentMatchers.any())).thenReturn(productDTO);
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/product/{id}", 1))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andReturn();
        String jsonResponse = result.getResponse().getContentAsString();
        ProductDTO responseProductDTO = objectMapper.readValue(jsonResponse, ProductDTO.class);
        //then
        assertEquals(productDTO, responseProductDTO);
    }

    @Test
    void deleteTest() throws Exception {
        //given
        Product product = createSampleProduct();
        ProductDTO productDTO = ProductMapper.getInstance().toDTO(product);
        //when
        Mockito.when(productService.delete(ArgumentMatchers.any())).thenReturn(Boolean.TRUE);
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.delete("/product")
                        .param("id", "1"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();
        String response = mvcResult.getResponse().getContentAsString();
        Boolean booleanResponse = objectMapper.readValue(response, Boolean.class);
        //then
        Mockito.verify(productService, VerificationModeFactory.times(1)).delete(ArgumentMatchers.any());
        assertTrue(booleanResponse);
    }

    @Test
    void updateTest() throws Exception {
        //given
        Product product = createSampleProduct();
        ProductDTO productDTO = ProductMapper.getInstance().toDTO(product);
        String requestJSON = objectMapper.writeValueAsString(productDTO);
        //when
        Mockito.when(productService.update(ArgumentMatchers.any(), ArgumentMatchers.any()))
                .thenReturn(productDTO);
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.put("/product/{id}", 1)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestJSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();
        String responseJSON = mvcResult.getResponse().getContentAsString();
        ProductDTO responseProductDTO = objectMapper.readValue(responseJSON,ProductDTO.class);
        //then
        assertEquals(productDTO,responseProductDTO);
    }

    @Test
    void findAllProductsByCategoryTest() throws Exception{
        //given
        Product product1 = createSampleProduct();
        Product product2 = createSampleProduct();
        List<ProductDTO> productDTOList = new ArrayList<>();
        productDTOList.add(ProductMapper.getInstance().toDTO(product1));
        productDTOList.add(ProductMapper.getInstance().toDTO(product2));
        //when
        Mockito.when(productService.findAllProductsByCategory(ArgumentMatchers.any())).thenReturn(productDTOList);
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/product/findByCategory/{category}","ELEKTRONIKA"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andReturn();
        String jsonResponse = result.getResponse().getContentAsString();
        List<ProductDTO> responseProductDTOList = objectMapper.readValue(jsonResponse, new TypeReference<>() {
        });
        //then
        assertEquals(2,responseProductDTOList.size());
    }

    private Product createSampleProduct() {
        return Product.builder()
                .name("Sample Product")
                .description("Sample Description")
                .price(BigDecimal.valueOf(19.99))
                .quantity(10)
                .category(createSampleCategory())
                .build();
    }

    private Category createSampleCategory() {
        return Category.builder()
                .name("ELEKTRONIKA")
                .build();
    }

}

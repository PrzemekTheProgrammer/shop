package pl.com.coders.shop2.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.mockito.internal.verification.VerificationModeFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import pl.com.coders.shop2.domain.Cart;
import pl.com.coders.shop2.domain.User;
import pl.com.coders.shop2.dto.CartDTO;
import pl.com.coders.shop2.mapper.CartMapper;
import pl.com.coders.shop2.service.CartService;

import java.math.BigDecimal;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@AutoConfigureMockMvc
public class CartControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    CartService cartService;

    @Test
    void addToCartTest() throws Exception {
        //given
        User user = createSampleUser("testowe", "testowy", "pass");
        CartDTO cartDTO = createSampleCartDTO(user);
        //when
        Mockito.when(cartService.addToCart(ArgumentMatchers.any(), ArgumentMatchers.anyInt(), ArgumentMatchers.any()))
                .thenReturn(cartDTO);
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post("/cart/{productTitle}/{amount}/addToCart", "produkt", 5)
                        .param("userEmail", "testowy"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();
        String resultContent = mvcResult.getResponse().getContentAsString();
        CartDTO responseCartDTO = objectMapper.readValue(resultContent, CartDTO.class);
        //then
        assertEquals(cartDTO, responseCartDTO);
        Mockito.verify(cartService, VerificationModeFactory.times(1)).addToCart(ArgumentMatchers.any(), ArgumentMatchers.anyInt(), ArgumentMatchers.any());
    }

    @Test
    void deleteCartItemByIndexTest() throws Exception{
        //given
        //when
        Mockito.when(cartService.deleteCartItemByIndex(ArgumentMatchers.any(),ArgumentMatchers.any())).thenReturn(Boolean.TRUE);
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.delete("/cart/cartItem/deleteByIndex")
                        .param("userEmail", "testowy@email.com")
                        .param("index","1"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();
        String response = mvcResult.getResponse().getContentAsString();
        Boolean booleanResponse = objectMapper.readValue(response, Boolean.class);
        //then
        Mockito.verify(cartService, VerificationModeFactory.times(1)).deleteCartItemByIndex(ArgumentMatchers.any(),ArgumentMatchers.any());
        assertTrue(booleanResponse);
    }

    @Test
    void getCartByUserEmailTest() throws Exception{
        //given
        User user = createSampleUser("testowe", "testowy", "pass");
        CartDTO cartDTO = createSampleCartDTO(user);
        //when
        Mockito.when(cartService.getCartByUserEmail(ArgumentMatchers.any()))
                .thenReturn(cartDTO);
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/cart")
                        .param("userEmail", "testowy"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();
        String resultContent = mvcResult.getResponse().getContentAsString();
        CartDTO responseCartDTO = objectMapper.readValue(resultContent, CartDTO.class);
        //then
        assertEquals(cartDTO, responseCartDTO);
        Mockito.verify(cartService, VerificationModeFactory.times(1)).getCartByUserEmail(ArgumentMatchers.any());
    }

    private CartDTO createSampleCartDTO(User user) {
        return CartMapper.getInstance().toDTO(Cart.builder()
                .totalPrice(BigDecimal.valueOf(0.00))
                .user(user)
                .cartItems(new ArrayList<>())
                .build());
    }

    private User createSampleUser(String firstName, String lastName, String pass) {
        return User.builder()
                .password(pass)
                .lastName(lastName)
                .firstName(firstName)
                .build();
    }

}

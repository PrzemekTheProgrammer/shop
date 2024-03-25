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
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import pl.com.coders.shop2.dto.OrderDTO;
import pl.com.coders.shop2.service.OrderService;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@AutoConfigureMockMvc
public class OrderControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private OrderService orderService;

    @Test
    void createOrderByEmailTest() throws Exception {
        //given
        OrderDTO orderDTO = OrderDTO.builder()
                .orderItems(new ArrayList<>())
                .totalPrice(BigDecimal.valueOf(5.55))
                .userName("testowy")
                .build();
        //when
        Mockito.when(orderService.createOrderByEmail(ArgumentMatchers.any())).thenReturn(orderDTO);
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post("/order")
                        .param("userEmail", "testowy@email.com"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();
        String jsonResponse = mvcResult.getResponse().getContentAsString();
        OrderDTO receivedOrderDTO = objectMapper.readValue(jsonResponse, OrderDTO.class);
        //then
        Mockito.verify(orderService, VerificationModeFactory.times(1)).createOrderByEmail(ArgumentMatchers.any());
        assertEquals(orderDTO, receivedOrderDTO);
    }

    @Test
    void getOrdersByEmailTest() throws Exception {
        //given
        OrderDTO orderDTO1 = OrderDTO.builder()
                .orderItems(new ArrayList<>())
                .totalPrice(BigDecimal.valueOf(5.55))
                .userName("testowy")
                .build();
        OrderDTO orderDTO2 = OrderDTO.builder()
                .orderItems(new ArrayList<>())
                .totalPrice(BigDecimal.valueOf(5.55))
                .userName("testowy")
                .build();
        List<OrderDTO> orderDTOList = new ArrayList<>();
        orderDTOList.add(orderDTO1);
        orderDTOList.add(orderDTO2);
        //when
        Mockito.when(orderService.getOrdersByEmail(ArgumentMatchers.any())).thenReturn(orderDTOList);
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/order")
                        .param("userEmail", "testowy@email.com"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();
        String jsonResponse = mvcResult.getResponse().getContentAsString();
        List<OrderDTO> receivedOrderDTOList = objectMapper.readValue(jsonResponse, new TypeReference<>() {
        });
        //then
        Mockito.verify(orderService, VerificationModeFactory.times(1)).getOrdersByEmail(ArgumentMatchers.any());
        assertEquals(2, receivedOrderDTOList.size());
        assertEquals(orderDTOList, receivedOrderDTOList);
    }

}

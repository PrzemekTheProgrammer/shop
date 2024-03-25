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
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import pl.com.coders.shop2.dto.UserDTO;
import pl.com.coders.shop2.service.UserService;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@AutoConfigureMockMvc
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private UserService userService;

    @Test
    void registerTestPositivePath() throws Exception {
        //given
        UserDTO userDTO = UserDTO.builder()
                .email("testowy@email.com")
                .firstName("testowe")
                .lastName("testowe")
                .password("pass")
                .build();
        String jsonInput = objectMapper.writeValueAsString(userDTO);
        //when
        Mockito.when(userService.create(ArgumentMatchers.any())).thenReturn(userDTO);
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post("/user/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonInput))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andReturn();
        String responseJson = mvcResult.getResponse().getContentAsString();
        UserDTO receivedUserDTO = objectMapper.readValue(responseJson, UserDTO.class);
        //then
        Mockito.verify(userService, VerificationModeFactory.times(1)).create(ArgumentMatchers.any());
        assertEquals(userDTO, receivedUserDTO);
    }

    @Test
    void registerTestBadEmailForValidation() throws Exception {
        //given
        UserDTO userDTO = UserDTO.builder()
                .email("testowy")
                .firstName("testowe")
                .lastName("testowe")
                .password("pass")
                .build();
        String jsonInput = objectMapper.writeValueAsString(userDTO);
        //when
        Mockito.when(userService.create(ArgumentMatchers.any())).thenReturn(userDTO);
        mockMvc.perform(MockMvcRequestBuilders.post("/user/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonInput))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
        //then
        Mockito.verify(userService, VerificationModeFactory.times(0)).create(ArgumentMatchers.any());
    }

}

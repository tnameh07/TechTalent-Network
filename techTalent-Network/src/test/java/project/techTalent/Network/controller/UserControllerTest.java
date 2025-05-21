package project.techTalent.Network.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;

import project.techTalent.Network.payloads.ApiResponse;
import project.techTalent.Network.payloads.UserDto;
import project.techTalent.Network.services.UserService;

@WebMvcTest(UserController.class)
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @Autowired
    private ObjectMapper objectMapper;

    private UserDto userDto;
    private List<UserDto> userDtoList;

    @BeforeEach
    void setUp() {
        userDto = new UserDto();
        userDto.setId(1);
        userDto.setName("Test User");
        userDto.setEmail("test@example.com");
        userDto.setPassword("password123");
        userDto.setAbout("Test User Description");

        userDtoList = Arrays.asList(userDto);
    }

    @Test
    void createUser_Success() throws Exception {
        when(userService.createUser(any(UserDto.class))).thenReturn(userDto);

        mockMvc.perform(post("/api/users/")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(userDto)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("Test User"))
                .andExpect(jsonPath("$.email").value("test@example.com"));
    }

    @Test
    void updateUser_Success() throws Exception {
        when(userService.updateUser(any(UserDto.class), anyInt())).thenReturn(userDto);

        mockMvc.perform(put("/api/users/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(userDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("Test User"));
    }

    @Test
    void deleteUser_Success() throws Exception {
        mockMvc.perform(delete("/api/users/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("User deleted successfully"))
                .andExpect(jsonPath("$.success").value(true));
    }

    @Test
    void getAllUsers_Success() throws Exception {
        when(userService.getAllUser()).thenReturn(userDtoList);

        mockMvc.perform(get("/api/users/"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].name").value("Test User"));
    }

    @Test
    void getSingleUser_Success() throws Exception {
        when(userService.getUserById(anyInt())).thenReturn(userDto);

        mockMvc.perform(get("/api/users/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("Test User"));
    }
} 
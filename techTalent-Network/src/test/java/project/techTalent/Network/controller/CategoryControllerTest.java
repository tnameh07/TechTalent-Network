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
import project.techTalent.Network.payloads.CategoryDto;
import project.techTalent.Network.services.CategoryService;

@WebMvcTest(CategoryController.class)
public class CategoryControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CategoryService categoryService;

    @Autowired
    private ObjectMapper objectMapper;

    private CategoryDto categoryDto;
    private List<CategoryDto> categoryDtoList;

    @BeforeEach
    void setUp() {
        categoryDto = new CategoryDto();
        categoryDto.setCategoryId(1);
        categoryDto.setCategoryTitle("Test Category");
        categoryDto.setCategoryDescription("Test Category Description");

        categoryDtoList = Arrays.asList(categoryDto);
    }

    @Test
    void createCategory_Success() throws Exception {
        when(categoryService.createCategory(any(CategoryDto.class))).thenReturn(categoryDto);

        mockMvc.perform(post("/api/categories/")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(categoryDto)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.categoryId").value(1))
                .andExpect(jsonPath("$.categoryTitle").value("Test Category"))
                .andExpect(jsonPath("$.categoryDescription").value("Test Category Description"));
    }

    @Test
    void updateCategory_Success() throws Exception {
        when(categoryService.updateCategory(any(CategoryDto.class), anyInt())).thenReturn(categoryDto);

        mockMvc.perform(put("/api/categories/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(categoryDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.categoryId").value(1))
                .andExpect(jsonPath("$.categoryTitle").value("Test Category"));
    }

    @Test
    void deleteCategory_Success() throws Exception {
        mockMvc.perform(delete("/api/categories/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("Categories deleted successfully"))
                .andExpect(jsonPath("$.success").value(true));
    }

    @Test
    void getCategory_Success() throws Exception {
        when(categoryService.getCategory(anyInt())).thenReturn(categoryDto);

        mockMvc.perform(get("/api/categories/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.categoryId").value(1))
                .andExpect(jsonPath("$.categoryTitle").value("Test Category"));
    }

    @Test
    void getAllCategories_Success() throws Exception {
        when(categoryService.getAllCategory()).thenReturn(categoryDtoList);

        mockMvc.perform(get("/api/categories/"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].categoryId").value(1))
                .andExpect(jsonPath("$[0].categoryTitle").value("Test Category"));
    }
} 
package project.techTalent.Network.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.Date;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;

import project.techTalent.Network.payloads.PostDto;
import project.techTalent.Network.services.PostService;

@WebMvcTest(PostController.class)
public class PostControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PostService postService;

    @Autowired
    private ObjectMapper objectMapper;

    private PostDto postDto;

    @BeforeEach
    void setUp() {
        postDto = new PostDto();
        postDto.setTitle("Test Post");
        postDto.setContent("Test Post Content");
        postDto.setImageName("test.jpg");
        postDto.setAddedDate(new Date());
    }

    @Test
    public void testCreatePost() throws Exception {
        when(postService.createPost(any(PostDto.class), any(Integer.class), any(Integer.class), any()))
                .thenReturn(postDto);

        MockMultipartFile file = new MockMultipartFile(
                "image",
                "test.jpg",
                MediaType.IMAGE_JPEG_VALUE,
                "test image content".getBytes()
        );

        MockMultipartFile postData = new MockMultipartFile(
                "postDto",
                "",
                MediaType.APPLICATION_JSON_VALUE,
                objectMapper.writeValueAsString(postDto).getBytes()
        );

        mockMvc.perform(multipart("/api/posts/user/1/category/1")
                .file(file)
                .file(postData))
                .andExpect(status().isCreated());
    }
} 
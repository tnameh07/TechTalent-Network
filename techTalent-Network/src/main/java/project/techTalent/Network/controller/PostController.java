package project.techTalent.Network.controller;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import jakarta.validation.Valid;
import project.techTalent.Network.payloads.ApiResponse;
import project.techTalent.Network.payloads.PostDto;
import project.techTalent.Network.services.PostService;

@RestController
@RequestMapping("/api/posts")
public class PostController {

	@Autowired
	private PostService postService;
	
	@PostMapping(value = "/user/{userId}/category/{categoryId}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public ResponseEntity<PostDto> createPost(
			@RequestParam("post") String postDtoJson,
			@PathVariable Integer userId,
			@PathVariable Integer categoryId,
			@RequestParam("image") MultipartFile image) throws IOException {

		System.out.println("postDtoJson : "+ postDtoJson);
		PostDto postDto = new com.fasterxml.jackson.databind.ObjectMapper().readValue(postDtoJson, PostDto.class);
		PostDto createdPost = this.postService.createPost(postDto, userId, categoryId, image);
		return new ResponseEntity<>(createdPost, HttpStatus.CREATED);
	}

	@PutMapping("/{postId}")
	public ResponseEntity<PostDto> updatePost(
			@Valid @RequestBody PostDto postDto,
			@PathVariable Integer postId,
			@RequestParam(value = "image", required = false) MultipartFile image) throws IOException {
		PostDto updatedPost = this.postService.updatePost(postDto, postId, image);
		return ResponseEntity.ok(updatedPost);
	}

	@DeleteMapping("/{postId}")
	public ResponseEntity<ApiResponse> deletePost(@PathVariable Integer postId) {
		this.postService.deletePost(postId);
		return new ResponseEntity<>(new ApiResponse("Post deleted successfully", true), HttpStatus.OK);
	}

	@GetMapping("/{postId}")
	public ResponseEntity<PostDto> getPost(@PathVariable Integer postId) {
		PostDto post = this.postService.getPost(postId);
		return ResponseEntity.ok(post);
	}

	@GetMapping("/")
	public ResponseEntity<List<PostDto>> getAllPosts() {
		List<PostDto> posts = this.postService.getAllpost();
		return ResponseEntity.ok(posts);
	}

	@GetMapping("/user/{userId}/posts")
	public ResponseEntity<List<PostDto>> getPostsByUser(@PathVariable Integer userId) {
		List<PostDto> posts = this.postService.getPostsByUser(userId);
		return ResponseEntity.ok(posts);
	}

	@GetMapping("/category/{categoryId}/posts")
	public ResponseEntity<List<PostDto>> getPostsByCategory(@PathVariable Integer categoryId) {
		List<PostDto> posts = this.postService.getPostsByCategory(categoryId);
		return ResponseEntity.ok(posts);
	}

	@GetMapping("/search/{keywords}")
	public ResponseEntity<List<PostDto>> searchPosts(@PathVariable String keywords) {
		List<PostDto> posts = this.postService.searchPosts(keywords);
		return ResponseEntity.ok(posts);
	}
}

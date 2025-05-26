package project.techTalent.Network.services;

import java.io.IOException;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import project.techTalent.Network.payloads.PostDto;

public interface PostService {
	//create
	PostDto createPost(PostDto postDto, Integer userId, Integer categoryId, MultipartFile image) throws IOException;
	
	//update
	PostDto updatePost(PostDto postDto, Integer postId, MultipartFile image) throws IOException;
	
	//getPost
	PostDto getPost(Integer postId);
	
	//getAllPost
	List<PostDto> getAllpost();
	
	//deletePost
	void deletePost(Integer postId);
	
	//get all posts by user
	List<PostDto> getPostsByUser(Integer userId);
	
	//get all posts by category
	List<PostDto> getPostsByCategory(Integer categoryId);
	
	//search posts
	List<PostDto> searchPosts(String keyword);
}

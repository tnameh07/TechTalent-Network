package project.techTalent.Network.respositories.Implementation;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import project.techTalent.Network.entities.Category;
import project.techTalent.Network.entities.Post;
import project.techTalent.Network.entities.User;
import project.techTalent.Network.exceptions.ResourceNotFoundException;
import project.techTalent.Network.payloads.PostDto;
import project.techTalent.Network.respositories.CategoryRepo;
import project.techTalent.Network.respositories.PostRepo;
import project.techTalent.Network.respositories.UserRepo;
import project.techTalent.Network.services.FileService;
import project.techTalent.Network.services.PostService;

@Service
public class PostServiceImpl implements PostService
{
	@Autowired
	private PostRepo postRepo;
	
	@Autowired
	private UserRepo userRepo;
	
	@Autowired
	private CategoryRepo categoryRepo;
	
	@Autowired
	private ModelMapper modelMapper;
	
	@Autowired
	private FileService fileService;
	
	

	@Override
	public PostDto createPost(PostDto postDto, Integer userId, Integer categoryId, MultipartFile image) throws IOException {
		
		User user = this.userRepo.findById(userId).
				orElseThrow(() -> new ResourceNotFoundException("User","user Id",userId));
		
		Category category = this.categoryRepo.findById(categoryId).
				orElseThrow(()-> new  ResourceNotFoundException("Category","category Id", categoryId));
		
		Post post = this.modelMapper.map(postDto, Post.class);
		
		// Upload image to Cloudinary
		String imageUrl = fileService.uploadImage("posts", image);
		post.setImageName(imageUrl);
		
		post.setAddedDate(new Date());
		post.setUser(user);
		post.setCategory(category);
		Post createdPost = this.postRepo.save(post);
		
		return this.modelMapper.map(createdPost, PostDto.class);
	}

	@Override
	public PostDto updatePost(PostDto postDto, Integer postId, MultipartFile image) throws IOException {
		Post post = this.postRepo.findById(postId)
				.orElseThrow(() -> new ResourceNotFoundException("Post", "Post Id", postId));
		
		// Update only if the new value is not null and not empty
		if (postDto.getTitle() != null && !postDto.getTitle().trim().isEmpty()) {
			if (postDto.getTitle().length() < 3) {
				throw new IllegalArgumentException("Post title must be at least 3 characters long");
			}
			post.setTitle(postDto.getTitle());
		}
		
		if (postDto.getContent() != null) {
			post.setContent(postDto.getContent());
		}
		
		if (image != null && !image.isEmpty()) {
			// Delete old image if exists
			if (post.getImageName() != null && !post.getImageName().equals("default.jpg")) {
				fileService.deleteImage(post.getImageName());
			}
			// Upload new image
			String imageUrl = fileService.uploadImage("posts", image);
			post.setImageName(imageUrl);
		}
		
		Post updatedPost = this.postRepo.save(post);
		return this.modelMapper.map(updatedPost, PostDto.class);
	}

	@Override
	public PostDto getPost(Integer postId) {
		Post post = this.postRepo.findById(postId)
				.orElseThrow(() -> new ResourceNotFoundException("Post", "Post Id", postId));
		return this.modelMapper.map(post, PostDto.class);
	}

	@Override
	public List<PostDto> getAllpost() {
		List<Post> posts = this.postRepo.findAll();
		return posts.stream()
				.map(post -> this.modelMapper.map(post, PostDto.class))
				.collect(Collectors.toList());
	}

	@Override
	public void deletePost(Integer postId) {
		Post post = this.postRepo.findById(postId)
				.orElseThrow(() -> new ResourceNotFoundException("Post", "Post Id", postId));
		
		// Delete image from Cloudinary if exists
		if (post.getImageName() != null && !post.getImageName().equals("default.jpg")) {
			try {
				fileService.deleteImage(post.getImageName());
			} catch (IOException e) {
				// Log the error but continue with post deletion
				e.printStackTrace();
			}
		}
		
		this.postRepo.delete(post);
	}

	@Override
	public List<PostDto> getPostsByUser(Integer userId) {
		User user = this.userRepo.findById(userId)
				.orElseThrow(() -> new ResourceNotFoundException("User", "Id", userId));
		List<Post> posts = this.postRepo.findByUser(user);
		return posts.stream()
				.map(post -> this.modelMapper.map(post, PostDto.class))
				.collect(Collectors.toList());
	}

	@Override
	public List<PostDto> getPostsByCategory(Integer categoryId) {
		Category category = this.categoryRepo.findById(categoryId)
				.orElseThrow(() -> new ResourceNotFoundException("Category", "Id", categoryId));
		List<Post> posts = this.postRepo.findByCategory(category);
		return posts.stream()
				.map(post -> this.modelMapper.map(post, PostDto.class))
				.collect(Collectors.toList());
	}

	@Override
	public List<PostDto> searchPosts(String keyword) {
		List<Post> posts = this.postRepo.findByTitleContaining(keyword);
		return posts.stream()
				.map(post -> this.modelMapper.map(post, PostDto.class))
				.collect(Collectors.toList());
	}

}

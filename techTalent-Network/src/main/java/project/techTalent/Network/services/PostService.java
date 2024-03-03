package project.techTalent.Network.services;

import java.util.List;

import project.techTalent.Network.entities.Post;
import project.techTalent.Network.payloads.PostDto;



public interface PostService 
{
	//create
  PostDto createPost(PostDto postDto, Integer userId, Integer categoryId);
  
 //update
  Post updatePost(PostDto postDto , Integer postId);
  
  //getPost
  
  Post getPost(Integer postId);
  
  //getAllPost
  
  List<Post> getAllpost();
  
  //deletePost
  void deletePost(Integer postId);
  
  //get all posts by user
  
  List<Post> getPostsByUser(Integer userId);
  
  //get all posts by category
  
  List<Post> getPostsByCategory(Integer categoryId);
  
  //search posts
  List<Post> searchPosts(String keyword);
}

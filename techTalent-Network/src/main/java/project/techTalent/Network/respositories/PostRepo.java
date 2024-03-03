package project.techTalent.Network.respositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import project.techTalent.Network.entities.Category;
import project.techTalent.Network.entities.Post;
import project.techTalent.Network.entities.User;

public interface PostRepo extends JpaRepository<Post, Integer>
{
   List<Post> findByUser(User user);
   List<Category> findByCategory(Category category);
}

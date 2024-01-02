package project.techTalent.Network.respositories;

import org.springframework.data.jpa.repository.JpaRepository;

import project.techTalent.Network.entities.Category;


public interface CategoryRepo extends JpaRepository<Category,Integer>
{

}

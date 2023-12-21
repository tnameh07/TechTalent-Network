package project.techTalent.Network.respositories;

import org.springframework.data.jpa.repository.JpaRepository;

import project.techTalent.Network.entities.User;



public interface UserRepo  extends JpaRepository< User, Integer>{

}

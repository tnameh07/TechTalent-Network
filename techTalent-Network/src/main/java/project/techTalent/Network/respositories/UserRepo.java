package project.techTalent.Network.respositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import project.techTalent.Network.entities.User;

public interface UserRepo extends JpaRepository<User, Integer> {
    Optional<User> findByEmail(String email);
}

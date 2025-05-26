package project.techTalent.Network.respositories.Implementation;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.password.PasswordEncoder;

import project.techTalent.Network.entities.User;
import project.techTalent.Network.payloads.UserDto;
import project.techTalent.Network.respositories.UserRepo;
import project.techTalent.Network.services.UserService;
import project.techTalent.Network.exceptions.*;

@Service
public class UserServiceImpl implements UserService 
{
	@Autowired
	private UserRepo userRepo;
	@Autowired
	private ModelMapper modelMapper;
    @Autowired
    private PasswordEncoder passwordEncoder;


	@Override
	public UserDto createUser(UserDto userDto) 
	{
		User user = this.dtoToUser(userDto);
		User savedUser = this.userRepo.save(user);
		return this.usertoDto(savedUser);		
	}

	@Override
	public UserDto updateUser(UserDto userDto, Integer userId) {
		User user = this.userRepo.findById(userId)
				.orElseThrow(() -> new ResourceNotFoundException("User", "Id", userId));
		
		// Update only if the new value is not null and not empty
		if (userDto.getName() != null && !userDto.getName().trim().isEmpty()) {
			if (userDto.getName().length() < 4) {
				throw new IllegalArgumentException("Username must be at least 4 characters long");
			}
			user.setName(userDto.getName());
		}
		
		if (userDto.getEmail() != null && !userDto.getEmail().trim().isEmpty()) {
			if (!userDto.getEmail().matches("^[A-Za-z0-9+_.-]+@(.+)$")) {
				throw new IllegalArgumentException("Invalid email format");
			}
			user.setEmail(userDto.getEmail());
		}
		
		if (userDto.getPassword() != null && !userDto.getPassword().trim().isEmpty()) {
			if (userDto.getPassword().length() < 3 || userDto.getPassword().length() > 10) {
				throw new IllegalArgumentException("Password must be between 3 and 10 characters");
			}
			user.setPassword(this.passwordEncoder.encode(userDto.getPassword())); // Encode password
		}
		
		if (userDto.getAbout() != null) {
			user.setAbout(userDto.getAbout());
		}
		
		User updatedUser = this.userRepo.save(user);
		return this.usertoDto(updatedUser);
	}

	@Override
	public UserDto getUserById( Integer userId) {
		User user = this.userRepo.findById(userId)
				.orElseThrow( () -> new ResourceNotFoundException( "User" , "Id" , userId ));
		return this.usertoDto(user);
		
	}

	@Override
	public List<UserDto> getAllUser() {
		List<User> users = this.userRepo.findAll();
		List<UserDto> userDtos = users.stream().map(user -> this.usertoDto(user)).collect(Collectors.toList());
		return userDtos;	
	}

	@Override
	public void deleteUser(Integer userId) {
		User user = this.userRepo.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User", "Id", userId));
		this.userRepo.delete(user);
		
	}

	public User dtoToUser(UserDto userDto)
	{
		User user = new User();
		user.setName(userDto.getName());
		user.setEmail(userDto.getEmail());
		user.setAbout(userDto.getAbout());
		user.setPassword(this.passwordEncoder.encode(userDto.getPassword()));
		return user;
	}
	
	public UserDto usertoDto(User user)
	{
		UserDto userDto = new UserDto();
		userDto.setId(user.getId());
		userDto.setName(user.getName());
		userDto.setEmail(user.getEmail());
		userDto.setAbout(user.getAbout());
		// Don't set password in DTO for security
		return userDto;	
	}
}

package project.techTalent.Network.respositories.Implementation;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
				.orElseThrow( () -> new ResourceNotFoundException( "User" , "Id" , userId ));
		user.setName(userDto.getName());
		user.setEmail(userDto.getEmail());
		user.setPassword(userDto.getPassword());
		user.setAbout(userDto.getAbout());
		
		User savedUser = this.userRepo.save(user);
		UserDto userDto1 = this.usertoDto(savedUser);
		return userDto1;
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
// converting dto to user first create object of that in which form you want to create
	public User dtoToUser(UserDto userDto)
	{
		User user = this.modelMapper.map(userDto, User.class);
//		user.setId(userDto.getId());
//		user.setName(userDto.getName());
//		user.setEmail(userDto.getEmail());
//		user.setAbout(userDto.getAbout());
//		user.setPassword(userDto.getPassword());
		return user;
		
	}
	
	// converting  user to DTO
	public UserDto usertoDto(User user)
	{
		UserDto  userDto = this.modelMapper.map(user, UserDto.class);
//		userDto.setId(user.getId());
//		userDto.setName(user.getName());
//		userDto.setEmail(user.getEmail());
//		userDto.setAbout(user.getAbout());
//		userDto.setPassword(user.getPassword());
		return userDto;	
	}

}

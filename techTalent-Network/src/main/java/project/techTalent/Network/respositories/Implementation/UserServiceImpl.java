package project.techTalent.Network.respositories.Implementation;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import project.techTalent.Network.entities.User;
import project.techTalent.Network.payloads.UserDto;
import project.techTalent.Network.respositories.UserRepo;
import project.techTalent.Network.services.UserService;

public class UserServiceImpl implements UserService 
{
	@Autowired
	private UserRepo userRepo;


	@Override
	public UserDto createUser(UserDto userDto) 
	{
		User user = this.dtoToUser(userDto);
		User savedUser = this.userRepo.save(user);
		return this.usertoDto(savedUser);		
	}

	@Override
	public UserDto updateUser(UserDto user, Integer userId) {
		
		return null;
	}

	@Override
	public UserDto getUserById(UserDto user, Integer userId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<UserDto> getAllUser() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void deleteUser(Integer userId) {
		// TODO Auto-generated method stub
		
	}
// converting dto to user first create object of that in which form you want to create
	public User dtoToUser(UserDto userDto)
	{
		User user = new User();
		user.setId(userDto.getId());
		user.setName(userDto.getName());
		user.setEmail(userDto.getEmail());
		user.setAbout(userDto.getAbout());
		user.setPassword(userDto.getPassword());
		return user;
		
	}
	
	// converting  user to DTO
	public UserDto usertoDto(User user)
	{
		UserDto  userDto = new UserDto();
		userDto.setId(user.getId());
		userDto.setName(user.getName());
		userDto.setEmail(user.getEmail());
		userDto.setAbout(user.getAbout());
		userDto.setPassword(user.getPassword());
		return userDto;	
	}

}

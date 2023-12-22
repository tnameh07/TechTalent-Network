package project.techTalent.Network.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import project.techTalent.Network.payloads.UserDto;
import project.techTalent.Network.services.UserService;

@RestController
@RequestMapping("/api/users")
public class UserController 
{
	@Autowired
	private UserService userService;
	
	//Post - create user
	@PostMapping("/")
	public ResponseEntity<UserDto> createUser(@RequestBody UserDto userDto)
	{
		UserDto createdUserDto = this.userService.createUser(userDto);
		return new ResponseEntity<>(createdUserDto , HttpStatus.CREATED);
	}
	
	//Put - update user
	
//	@PutMapping("/update")
//	public ResponseEntity<UserDto> updateUser(@RequestBody  UserDto userDto, Integer Id)
//	{
//		UserDto updatedUser = this.userService.updateUser(userDto, Id);
//		return new ResponseEntity<>(updatedUser , HttpStatus.CREATED);
//		
//	}
	
	
	
	
	
	

}

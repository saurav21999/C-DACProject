package com.onlineshopping.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.onlineshopping.dao.AddressDao;
import com.onlineshopping.dao.UserDao;
import com.onlineshopping.dto.AddUserRequest;
import com.onlineshopping.dto.UserLoginRequest;
import com.onlineshopping.model.Address;
import com.onlineshopping.model.User;
import com.onlineshopping.security.PasswordEcnoder;
import com.onlineshopping.security.SaltVal;

@RestController
@RequestMapping("api/user")
@CrossOrigin(origins = "http://localhost:3000")
public class UserController {
	
	@Autowired
	private UserDao userDao;
	
	@Autowired
	private AddressDao addressDao;
	
	@Autowired
	private SaltVal saltvalue;

	
	@PostMapping("register")
	public ResponseEntity<?> registerUser(@RequestBody AddUserRequest userRequest) {
		System.out.println("recieved request for REGISTER USER");
		System.out.println(userRequest);
		
		Address address = new Address();
		address.setCity(userRequest.getCity());
		address.setPincode(userRequest.getPincode());
		address.setStreet(userRequest.getStreet());
		
		Address addAddress = addressDao.save(address);
		String password = PasswordEcnoder.genrateSecurePassword(userRequest.getPassword(), saltvalue.getSalt());
		User user = new User();
		user.setAddress(addAddress);
		user.setEmailId(userRequest.getEmailId());
		user.setFirstName(userRequest.getFirstName());
		user.setLastName(userRequest.getLastName());
		user.setPhoneNo(userRequest.getPhoneNo());
		user.setPassword(password);
		user.setRole(userRequest.getRole());
		User addUser = userDao.save(user);
		
		System.out.println("response sent!!!");
		return ResponseEntity.ok(addUser);
	}
	@DeleteMapping("/delete/{userId}")
	public ResponseEntity<?> deleteUserById(@PathVariable int userId) {
	    System.out.println("Received request for deleting user with ID: " + userId);
	    
	    Optional<User> optionalUser = userDao.findById(userId);
	    if (optionalUser.isPresent()) {
	        User user = optionalUser.get();
	        userDao.delete(user);
	        System.out.println("User with ID " + userId + " has been deleted successfully.");
	        return ResponseEntity.ok("User with ID " + userId + " has been deleted successfully.");
	    } else {
	        System.out.println("User with ID " + userId + " does not exist.");
	        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User with ID " + userId + " does not exist.");
	    }
	}
	@GetMapping("users")
	public ResponseEntity<?> getAllUsers() {
	System.out.println("received request for GET ALL USERS");
	List<User> users = userDao.findAll();

	System.out.println("response sent!!!");
	return ResponseEntity.ok(users);}
	
	
	@PostMapping("login")
	public ResponseEntity<?> loginUser(@RequestBody UserLoginRequest loginRequest) {
		System.out.println("recieved request for LOGIN USER");
		System.out.println(loginRequest);
		String encryp = PasswordEcnoder.genrateSecurePassword(loginRequest.getPassword(), saltvalue.getSalt());
		
		User user = new User();
		user = userDao.findByEmailIdAndPasswordAndRole(loginRequest.getEmailId(), encryp, loginRequest.getRole());
		
		System.out.println("response sent!!!");
		return ResponseEntity.ok(user);
	}
	
	@GetMapping("firstName")
	public List<?> getAllUserFind(@RequestParam("name") String name)
	{
		List<?> u1 =  userDao.getAllUserInSorted(name);
		return u1;
	}
	
	
}

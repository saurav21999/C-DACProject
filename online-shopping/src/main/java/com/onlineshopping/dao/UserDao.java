package com.onlineshopping.dao;

import java.util.List;


import javax.persistence.*;
import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;


import com.onlineshopping.model.User;

@Repository
public interface UserDao extends JpaRepository<User, Integer> {
	
	User findByEmailIdAndPasswordAndRole(String emailId, String password, String role);
	List<User> findByRole(String role);
	User findByEmailId(String email);
	
	
	 
	// get all user in ascending order 
//	@Transactional
	//@Query("select u from User u  order by u.firstName")
//	List<User> getAllUserInSorted();
	
	
	@Query("select u from User u  where u.firstName   = :name")
	List<User> getAllUserInSorted(String name);
	
	
	

	


}

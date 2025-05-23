package com.packsure.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.packsure.entity.User;
import com.packsure.model.LoginRequest;
import com.packsure.repository.UserRepository;

@Service
public class UserService {
	
	@Autowired 
	UserRepository usersRepo;
	
	public User addUser(User user) {
		
		 if (usersRepo.findByEmail(user.getEmail()).isPresent()) {
		        throw new RuntimeException("Email is already registered");
		    }
		
		return usersRepo.save(user);
		
	}
	
	public Boolean loginUser(LoginRequest loginRequest) {
		
		Optional<User> user = usersRepo.findById(loginRequest.getUserId());
		User user1 = user.get();
		
		if(user1 == null) {
			return false;
		}
		
		
		
		if(!user1.getPassword().equals(loginRequest.getPassword())) {
			return false;
		}
		
		return true;
		
		
		
	}
}


package com.Sumitav.Services.Impl;

import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
 
import org.springframework.stereotype.Service;

import com.Sumitav.Entity.User;
import com.Sumitav.Entity.UserRole;
import com.Sumitav.Repository.RoleRepository;
import com.Sumitav.Repository.UserRepository;
import com.Sumitav.Services.UserService;

@Service
public class UserServiceImpl implements UserService {
	
	
	@Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

     
    private User user;

	@Override
	public User createUser(User user, Set<UserRole> userRoles) throws Exception {
		// TODO Auto-generated method stub
		User local = this.userRepository.findByEmail(user.getEmail());
        if (local != null) {
            System.out.println("Already Existed User !!");
            throw new Exception("User already present!!!");

        } else {
            //User create
            for (UserRole ur : userRoles) {
                roleRepository.save(ur.getRole());
            }
            user.getUserRoles().addAll(userRoles);
            local = this.userRepository.save(user);
        }
        return local;
		 
	}

	 

	@Override
	public void deleteUser(Long userId) {
		// TODO Auto-generated method stub
		this.userRepository.deleteById(userId);
		
	}

	@Override
	public User updateUser(Long userId) {
		// TODO Auto-generated method stub
		return this.userRepository.save(user);
	}

	@Override
	public User getUserById(Long userId) {
		// TODO Auto-generated method stub
		return userRepository.findById(userId).orElse(null);
	}

	@Override
	public User saveUser(User user) {
		// TODO Auto-generated method stub
		return  userRepository.save(user);
	}

	@Override
	public List<User> getAllUsers() {
		// TODO Auto-generated method stub
		return  userRepository.findAll();
	}

	@Override
	public User findByEmail(String email) {
		// TODO Auto-generated method stub
		return userRepository.findByEmail(email);
	}
	
	 
}

package com.Sumitav.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.Sumitav.Entity.User;

public interface UserRepository extends JpaRepository<User,Long>{
	
	public User findByEmail(String email);

	 

}

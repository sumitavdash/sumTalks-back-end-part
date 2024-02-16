package com.Sumitav;

import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.Sumitav.Entity.Role;
import com.Sumitav.Entity.User;
import com.Sumitav.Entity.UserRole;
import com.Sumitav.Services.UserService;

@SpringBootApplication
public class SumTalksApplication implements CommandLineRunner{
	
	
	 @Autowired
	    private UserService userService;

	    @Autowired
	    private BCryptPasswordEncoder bCryptPasswordEncoder;

	public static void main(String[] args) {
		SpringApplication.run(SumTalksApplication.class, args); }

	@Override
	public void run(String... args) throws Exception {
		// TODO Auto-generated method stub
		
	
		
		System.out.println("Lets Go BABY  !!!!");
		
		
		
//      User user= new User();
//     user.setFullName("sarmu");
//
//      user.setPassword(this.bCryptPasswordEncoder.encode("abc"));
//      user.setEmail("sarmu@gmail.com");
//
//
//      Role role1=new Role();
//      role1.setRoleId(21L);
//       role1.setRoleName("ADMIN");
//
//
//       Set<UserRole> userRoleSet= new HashSet<>();
//      UserRole userRole= new UserRole();
//       userRole.setRole(role1);
//       userRole.setUser(user);
//       userRoleSet.add(userRole);
//       User user1= this.userService.createUser(user,userRoleSet);
//       System.out.println(user1.getUsername());

	}

}

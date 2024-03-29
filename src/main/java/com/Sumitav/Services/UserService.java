package com.Sumitav.Services;

import java.util.List;
import java.util.Set;

import com.Sumitav.Entity.User;
import com.Sumitav.Entity.UserRole;

public interface UserService {

	

    //creating user
    public User createUser(User user, Set<UserRole> userRoles) throws Exception;
    

    //delete user by Id
    public void deleteUser(Long userId);
    //updating user by Id
    public User updateUser(Long userId);
    public User getUserById(Long userId);





    public User saveUser(User user);

    List<User> getAllUsers();




    //for reset password
    // Method to initiate the password reset process
//   public void initiatePasswordReset(String email) throws Exception;

    // Method to process the password reset token and update the password
//   public void completePasswordReset(String token, String newPassword) throws Exception;

    public User findByEmail(String email);
//    public User findByResetToken(String resetToken);
}

package com.api.ecommerce.security;

import com.api.ecommerce.entities.users.User;
import com.api.ecommerce.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public class ShopmeUserDetailsService implements UserDetailsService {

    @Autowired
    private UserService userService;

    /**
     * This method is called by Spring Security when a user tries to log in.
     * It returns a UserDetails object that Spring Security uses to build an
     * Authentication object (ShopmeUserDetails).
     */
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User userByEmail = userService.getUserByEmail(email);
        if (userByEmail == null) {
            throw new UsernameNotFoundException("User not found");
        }
        return new ShopmeUserDetails(userByEmail);
    }
}

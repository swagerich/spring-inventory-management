package com.erich.management.Services.Auth;

import com.erich.management.Entity.Auth.ExtendsUser;
import com.erich.management.Entity.User;
import com.erich.management.Exception.EntityNotFoundException;
import com.erich.management.Exception.Enum.ErrorCodes;
import com.erich.management.Repository.UserRepository;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class AppUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    public AppUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findUserByEmail(username).orElseThrow(() ->
                new EntityNotFoundException("User not found", ErrorCodes.USER_NOT_FOUND));
        List<SimpleGrantedAuthority> authoritys = new ArrayList<>();
        user.getRoles().forEach(role ->
                authoritys.add(new SimpleGrantedAuthority(role.getAuthority())));

        //return new ExtendsUser(user.getEmail(), user.getPassword(), user.getEnterprise().getId(), authoritys);
        return new org.springframework.security.core.userdetails.User(user.getEmail(),user.getPassword(),true,true,true,true, authoritys);
    }
}

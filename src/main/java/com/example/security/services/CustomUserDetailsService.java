/* package com.example.security.services;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.example.security.entities.MyUser;
import com.example.security.entities.Role;
import com.example.security.repository.MyUserRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService{

    private final MyUserRepository myUserRepository;
    public Collection<GrantedAuthority> mapToAuthorities(List<Role> roles) {
    return roles.stream().map(role -> new       
                 SimpleGrantedAuthority(role.getRole()))
                                  .collect(Collectors.toList());

    
}

    //Método para traernos un usuario con todos sus datos por medio de su username
    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        MyUser usuarios = MyUserRepository.findByUserName(userName).orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado"));
        return new User(usuarios.getUserName(), usuarios.getPassword(), 
                          mapToAuthorities(usuarios.getRoles()));
    }



}
 */
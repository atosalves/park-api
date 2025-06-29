package com.atosalves.park_api.jwt;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.atosalves.park_api.entity.User;
import com.atosalves.park_api.service.UserService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class JwtUserDetailsService implements UserDetailsService {

        private final UserService userService;

        @Override
        public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
                var user = userService.getByUsername(username);
                return new JwtUserDetails(user);
        }

        public JwtToken getTokenAuthenticated(String username) {
                User.Role role = userService.getRoleByUsername(username);
                return JwtUtils.createToken(username, role.name().substring("ROLE_".length()));
        }

}

package com.atosalves.park_api.web.dto.mapper;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.BeanUtils;

import com.atosalves.park_api.entity.User;
import com.atosalves.park_api.web.dto.user.UserCreateDto;
import com.atosalves.park_api.web.dto.user.UserResponseDto;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class UserMapper {

        public static User toUser(UserCreateDto createDto) {
                var user = new User();
                BeanUtils.copyProperties(createDto, user);
                return user;
        }

        public static UserResponseDto toDto(User user) {
                String role = user.getRole().name().substring("ROLE_".length());

                return new UserResponseDto(user.getId(), user.getUsername(), role);
        }

        public static List<UserResponseDto> toListDto(List<User> users) {
                return users.stream().map(user -> toDto(user)).collect(Collectors.toList());
        }

}

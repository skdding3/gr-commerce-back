package com.gr.commerce.app.mapper;

import com.gr.commerce.app.model.UserDto;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper {

    UserDto findUserByEmail(String email);

}

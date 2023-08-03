package com.fontana.backend.domain.user.dtos;

import com.fontana.backend.domain.user.entity.Users;
import org.springframework.stereotype.Service;

@Service
public  class UserMapper {
   public static Users mapUser(UserRequestDTO userDTO){
        Users theNewUser = new Users();
        //Role for testing purpusees delete later

//        Roles theTmpRole = new Roles(1,temp,"viewer");
        theNewUser.setName(userDTO.getLogin());
//        theNewUser.setRole(theTmpRole);
        return theNewUser;
    }

}

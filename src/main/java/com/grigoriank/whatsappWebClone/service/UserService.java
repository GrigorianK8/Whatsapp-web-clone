package com.grigoriank.whatsappWebClone.service;

import com.grigoriank.whatsappWebClone.dto.request.UpdateUserRequestDTO;
import com.grigoriank.whatsappWebClone.entity.User;
import com.grigoriank.whatsappWebClone.exception.UserException;

import java.util.List;

public interface UserService {

    User findUserById(Long id) throws UserException;

    User findUserProfile(String jwt) throws UserException;

    User updateUser(Long userId, UpdateUserRequestDTO updateReq) throws UserException;

    List<User> searchUser(String query);
}

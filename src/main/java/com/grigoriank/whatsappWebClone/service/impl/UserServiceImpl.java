package com.grigoriank.whatsappWebClone.service.impl;

import com.grigoriank.whatsappWebClone.dto.request.UpdateUserRequestDTO;
import com.grigoriank.whatsappWebClone.entity.User;
import com.grigoriank.whatsappWebClone.exception.UserException;
import com.grigoriank.whatsappWebClone.repository.UserRepository;
import com.grigoriank.whatsappWebClone.service.UserService;
import com.grigoriank.whatsappWebClone.util.JwtTokenUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final JwtTokenUtil tokenUtil;

    @Override
    public User findUserById(Long id) throws Exception {
        Optional<User> user = userRepository.findById(id);
        if (user.isPresent()) {
            return user.get();
        }
        throw new UserException("User not found with id " + id);
    }

    @Override
    public User findUserProfile(String jwt) throws Exception {

        String email = tokenUtil.getEmailByToken(jwt);
        if (email == null) {
            throw new BadCredentialsException("Invalid token");
        }

        User user = userRepository.findByEmail(email);
        if (user == null) {
            throw new UserException("User not found with email " + email);
        }

        return user;
    }

    @Override
    public User updateUser(Long userId, UpdateUserRequestDTO updateReq) throws UserException {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserException("User not found"));

        if (updateReq.getFullName() != null) {
            user.setFullName(updateReq.getFullName());
        }
        if (updateReq.getProfilePicture() != null) {
            user.setProfilePicture(updateReq.getProfilePicture());
        }

        return userRepository.save(user);
    }

    @Override
    public List<User> searchUser(String query) {
        List<User> users = userRepository.searchUser(query);
        return users;
    }
}

package com.grigoriank.whatsappWebClone.endpoint;

import com.grigoriank.whatsappWebClone.dto.request.UpdateUserRequestDTO;
import com.grigoriank.whatsappWebClone.dto.response.ApiResponseDTO;
import com.grigoriank.whatsappWebClone.entity.User;
import com.grigoriank.whatsappWebClone.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
public class UserEndpoint {

    private final UserService userService;

    @GetMapping("/profile")
    public ResponseEntity<User> getProfile(@RequestHeader("Authorization") String token)
            throws Exception {

        User user = userService.findUserProfile(token);
        return new ResponseEntity<>(user, HttpStatus.ACCEPTED);
    }

    @GetMapping("/{query}")
    public ResponseEntity<List<User>> searchProfile(@PathVariable("query") String query) {
        List<User> users = userService.searchUser(query);
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @PutMapping("/update")
    public ResponseEntity<ApiResponseDTO> updateUser(@RequestBody UpdateUserRequestDTO updateReq,
                                                     @RequestHeader("Authorization") String token)
            throws Exception {

        User user = userService.findUserProfile(token);
        ApiResponseDTO responseDTO = ApiResponseDTO.builder()
                .message("user updated successfully")
                .status(true)
                .build();

        return new ResponseEntity<>(responseDTO, HttpStatus.ACCEPTED);
    }
}

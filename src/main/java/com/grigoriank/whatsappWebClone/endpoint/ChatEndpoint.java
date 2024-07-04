package com.grigoriank.whatsappWebClone.endpoint;

import com.grigoriank.whatsappWebClone.dto.request.GroupChatRequestDTO;
import com.grigoriank.whatsappWebClone.dto.request.SingleChatRequestDTO;
import com.grigoriank.whatsappWebClone.dto.response.ApiResponseDTO;
import com.grigoriank.whatsappWebClone.entity.Chat;
import com.grigoriank.whatsappWebClone.entity.User;
import com.grigoriank.whatsappWebClone.exception.ChatException;
import com.grigoriank.whatsappWebClone.exception.UserException;
import com.grigoriank.whatsappWebClone.service.ChatService;
import com.grigoriank.whatsappWebClone.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/chats")
public class ChatEndpoint {

    private final ChatService chatService;
    private final UserService userService;

    @PostMapping("/single")
    public ResponseEntity<Chat> createChat(@RequestBody SingleChatRequestDTO sngChatReqDTO,
                                           @RequestHeader("Authorization") String jwt) throws UserException {

        User userReq = userService.findUserProfile(jwt);
        Chat chat = chatService.createChat(userReq, sngChatReqDTO.getUserId());
        return new ResponseEntity<>(chat, HttpStatus.OK);
    }

    @PostMapping("/group")
    public ResponseEntity<Chat> createGroup(@RequestBody GroupChatRequestDTO groupChatReqDTO,
                                            @RequestHeader("Authorization") String jwt) throws UserException {

        User userReq = userService.findUserProfile(jwt);
        Chat chat = chatService.createGroup(groupChatReqDTO, userReq);
        return new ResponseEntity<>(chat, HttpStatus.CREATED);
    }

    @GetMapping("/{chatId}")
    public ResponseEntity<Chat> findChatById(@PathVariable Long chatId) throws ChatException {

        Chat chat = chatService.findChatById(chatId);
        return new ResponseEntity<>(chat, HttpStatus.OK);
    }

    @GetMapping("/user")
    public ResponseEntity<List<Chat>> findAllChats(@RequestHeader("Authorization") String jwt) throws UserException {

        User userReq = userService.findUserProfile(jwt);
        List<Chat> chats = chatService.findAllChatByUserId(userReq.getId());
        return new ResponseEntity<>(chats, HttpStatus.OK);
    }

    @PutMapping("/{chatId}/add/{userId}")
    public ResponseEntity<Chat> addUserToGroup(@PathVariable Long chatId,
                                               @PathVariable Long userId,
                                               @RequestHeader("Authorization") String jwt)
            throws UserException, ChatException {

        User userReq = userService.findUserProfile(jwt);
        Chat chat = chatService.addUserToGroup(chatId, userId, userReq);
        return new ResponseEntity<>(chat, HttpStatus.OK);
    }

    @PutMapping("/{chatId}/remove/{userId}")
    public ResponseEntity<Chat> removeUserToGroup(@PathVariable Long chatId,
                                                  @PathVariable Long userId,
                                                  @RequestHeader("Authorization") String jwt)
            throws UserException, ChatException {

        User userReq = userService.findUserProfile(jwt);
        Chat chat = chatService.removeFromGroup(chatId, userId, userReq);
        return new ResponseEntity<>(chat, HttpStatus.OK);
    }

    @DeleteMapping("/delete/{chatId}")
    public ResponseEntity<ApiResponseDTO> deleteChat(@PathVariable Long chatId,
                                                     @RequestHeader("Authorization") String jwt)
            throws UserException, ChatException {

        User userReq = userService.findUserProfile(jwt);
        chatService.deleteChat(chatId, userReq.getId());
        ApiResponseDTO apiRespDTO = ApiResponseDTO.builder()
                .message("Chat is deleted successfully")
                .status(true)
                .build();

        return new ResponseEntity<>(apiRespDTO, HttpStatus.NO_CONTENT);
    }
}

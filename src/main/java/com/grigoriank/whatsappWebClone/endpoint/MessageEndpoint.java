package com.grigoriank.whatsappWebClone.endpoint;

import com.grigoriank.whatsappWebClone.dto.request.SendMessageRequestDTO;
import com.grigoriank.whatsappWebClone.dto.response.ApiResponseDTO;
import com.grigoriank.whatsappWebClone.entity.Message;
import com.grigoriank.whatsappWebClone.entity.User;
import com.grigoriank.whatsappWebClone.exception.ChatException;
import com.grigoriank.whatsappWebClone.exception.MessageException;
import com.grigoriank.whatsappWebClone.exception.UserException;
import com.grigoriank.whatsappWebClone.service.MessageService;
import com.grigoriank.whatsappWebClone.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/messages")
public class MessageEndpoint {

    private final MessageService messageService;
    private final UserService userService;

    @PostMapping("/create")
    public ResponseEntity<Message> sendMessage(@RequestBody SendMessageRequestDTO sendMsgReqDTO,
                                               @RequestHeader("Authorization") String jwt)
            throws UserException, ChatException {

        User user = userService.findUserProfile(jwt);
        sendMsgReqDTO.setUserId(user.getId());
        Message message = messageService.sendMessage(sendMsgReqDTO);
        return new ResponseEntity<>(message, HttpStatus.OK);
    }

    @GetMapping("/chat/{chatId}")
    public ResponseEntity<List<Message>> getChatsMessages(@PathVariable Long chatId,
                                                          @RequestHeader("Authorization") String jwt)
            throws UserException, ChatException {

        User user = userService.findUserProfile(jwt);
        List<Message> messages = messageService.getChatsMessages(chatId, user);
        return new ResponseEntity<>(messages, HttpStatus.OK);
    }

    @DeleteMapping("/{messageId}")
    public ResponseEntity<ApiResponseDTO> deleteMessage(@PathVariable Long messageId,
                                                        @RequestHeader("Authorization") String jwt)
            throws UserException, MessageException {

        User user = userService.findUserProfile(jwt);
        messageService.deleteMessage(messageId, user);
        ApiResponseDTO apiRespDTO = ApiResponseDTO.builder()
                .message("Message deleted successfully")
                .status(false)
                .build();

        return new ResponseEntity<>(apiRespDTO, HttpStatus.NO_CONTENT);
    }
}

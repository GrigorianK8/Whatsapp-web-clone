package com.grigoriank.whatsappWebClone.service.impl;

import com.grigoriank.whatsappWebClone.dto.request.SendMessageRequestDTO;
import com.grigoriank.whatsappWebClone.entity.Chat;
import com.grigoriank.whatsappWebClone.entity.Message;
import com.grigoriank.whatsappWebClone.entity.User;
import com.grigoriank.whatsappWebClone.exception.ChatException;
import com.grigoriank.whatsappWebClone.exception.MessageException;
import com.grigoriank.whatsappWebClone.exception.UserException;
import com.grigoriank.whatsappWebClone.repository.MessageRepository;
import com.grigoriank.whatsappWebClone.service.ChatService;
import com.grigoriank.whatsappWebClone.service.MessageService;
import com.grigoriank.whatsappWebClone.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MessageServiceImpl implements MessageService {

    private final MessageRepository messageRepository;
    private final ChatService chatService;
    private final UserService userService;

    @Override
    public Message sendMessage(SendMessageRequestDTO sendMsgReqDTO) throws UserException, ChatException {
        User user = userService.findUserById(sendMsgReqDTO.getUserId());
        Chat chat = chatService.findChatById(sendMsgReqDTO.getChatId());

        Message message = new Message();
        message.setUser(user);
        message.setChat(chat);
        message.setContent(sendMsgReqDTO.getContent());
        message.setTimestamp(LocalDateTime.now());

        return message;
    }

    @Override
    public List<Message> getChatsMessages(Long chatId, User userReq) throws UserException, ChatException {
        Chat chat = chatService.findChatById(chatId);

        if (!chat.getUsers().contains(userReq)) {
            throw new UserException("You are not releted to this chat " + chat.getId());
        }
        List<Message> messages = messageRepository.findByChatId(chatId);
        return messages;
    }

    @Override
    public Message findMessageById(Long messageId) throws MessageException {
        Optional<Message> opt = messageRepository.findById(messageId);

        if (opt.isPresent()) {
            return opt.get();
        }
        throw new MessageException("Message not found with id:" + messageId);
    }

    @Override
    public void deleteMessage(Long messageId, User userReq) throws UserException, MessageException {
        Message message = findMessageById(messageId);

        if (message.getUser().getId().equals(userReq.getId())) {
            messageRepository.deleteById(messageId);
        }
        throw new UserException("You can't delete another users message "+ userReq.getFullName());
    }
}

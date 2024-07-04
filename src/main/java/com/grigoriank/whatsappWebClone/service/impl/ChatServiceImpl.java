package com.grigoriank.whatsappWebClone.service.impl;

import com.grigoriank.whatsappWebClone.dto.request.GroupChatRequestDTO;
import com.grigoriank.whatsappWebClone.entity.Chat;
import com.grigoriank.whatsappWebClone.entity.User;
import com.grigoriank.whatsappWebClone.exception.ChatException;
import com.grigoriank.whatsappWebClone.exception.UserException;
import com.grigoriank.whatsappWebClone.repository.ChatRepository;
import com.grigoriank.whatsappWebClone.service.ChatService;
import com.grigoriank.whatsappWebClone.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ChatServiceImpl implements ChatService {

    private final ChatRepository chatRepository;
    private final UserService userService;

    @Override
    public Chat createChat(User userReq, Long userId) throws UserException {
        User user = userService.findUserById(userId);
        Chat chatExist = chatRepository.findSingleChatByUserId(user, userReq);
        if (chatExist != null) {
            return chatExist;
        }
        Chat chat = new Chat();
        chat.setCreatedBy(userReq);
        chat.getUsers().add(user);
        chat.getUsers().add(userReq);
        chat.setGroup(false);

        return chat;
    }

    @Override
    public Chat findChatById(Long id) throws ChatException {
        Optional<Chat> chat = chatRepository.findById(id);
        if (chat.isPresent()) {
            return chat.get();
        }
        throw new ChatException("Chat not found with id:" + id);
    }

    @Override
    public List<Chat> findAllChatByUserId(Long userId) throws UserException {
        User user = userService.findUserById(userId);
        List<Chat> chats = chatRepository.findChatByUserId(user.getId());

        return chats;
    }

    @Override
    public Chat createGroup(GroupChatRequestDTO chatReqDTO, User userReq) throws UserException {
        Chat group = new Chat();
        group.setGroup(true);
        group.setChatName(chatReqDTO.getChatName());
        group.setChatImage(chatReqDTO.getChatImage());
        group.setCreatedBy(userReq);
        group.getAdmins().add(userReq);

        for (Long userId : chatReqDTO.getUserIds()) {
            User user = userService.findUserById(userId);
            group.getUsers().add(user);
        }
        return group;
    }

    @Override
    public Chat addUserToGroup(Long userId, Long chatId, User userReq) throws UserException, ChatException {
        Optional<Chat> opt = chatRepository.findById(chatId);
        User user = userService.findUserById(userId);

        if (opt.isPresent()) {
            Chat chat = opt.get();
            if (chat.getAdmins().contains(userReq)) {
                chat.getUsers().add(user);

                return chatRepository.save(chat);
            } else {
                throw new UserException("You are not admin");
            }
        }
        throw new ChatException("Chat not found with id:" + chatId);
    }

    @Override
    public Chat renameGroup(Long chatId, String groupName, User userReq) throws ChatException, UserException {
        Optional<Chat> opt = chatRepository.findById(chatId);

        if (opt.isPresent()) {
            Chat chat = opt.get();
            if (chat.getUsers().contains(userReq)) {
                chat.setChatName(groupName);

                return chatRepository.save(chat);
            }
            throw new UserException("You are not member of this group");
        }
        throw new ChatException("Chat not found with id:" + chatId);
    }

    @Override
    public Chat removeFromGroup(Long chatId, Long userId, User userReq) throws ChatException, UserException {
        Optional<Chat> opt = chatRepository.findById(chatId);
        User user = userService.findUserById(userId);

        if (opt.isPresent()) {
            Chat chat = opt.get();
            if (chat.getAdmins().contains(userReq)) {
                chat.getUsers().remove(user);

                return chatRepository.save(chat);
            } else if (chat.getUsers().contains(userReq)) {
                if (user.getId().equals(userReq.getId())) {
                    chat.getUsers().remove(user);

                    return chatRepository.save(chat);
                }
            }
            throw new UserException("You can't remove another user");
        }
        throw new ChatException("Chat not found with id:" + chatId);
    }

    @Override
    public void deleteChat(Long chatId, Long userId) throws ChatException, UserException {
        Optional<Chat> opt = chatRepository.findById(chatId);
        if (opt.isPresent()) {
            Chat chat = opt.get();
            chatRepository.deleteById(chat.getId());
        }
    }
}

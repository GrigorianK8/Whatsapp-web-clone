package com.grigoriank.whatsappWebClone.service;

import com.grigoriank.whatsappWebClone.dto.request.GroupChatRequestDTO;
import com.grigoriank.whatsappWebClone.entity.Chat;
import com.grigoriank.whatsappWebClone.entity.User;
import com.grigoriank.whatsappWebClone.exception.ChatException;
import com.grigoriank.whatsappWebClone.exception.UserException;

import java.util.List;

public interface ChatService {

    Chat createChat(User userReq, Long userId) throws UserException;

    Chat findChatById(Long id) throws ChatException;

    List<Chat> findAllChatByUserId(Long userId) throws UserException;

    Chat createGroup(GroupChatRequestDTO chatReqDTO, User userReq) throws UserException;

    Chat addUserToGroup(Long userId, Long chatId, User userReq) throws UserException, ChatException;

    Chat renameGroup(Long chatId, String groupName, User userReq) throws ChatException, UserException;

    Chat removeFromGroup(Long chatId, Long userId, User userReq) throws ChatException, UserException;

    void deleteChat(Long chatId, Long userId) throws ChatException, UserException;
}

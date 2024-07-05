package com.grigoriank.whatsappWebClone.service;

import com.grigoriank.whatsappWebClone.dto.request.SendMessageRequestDTO;
import com.grigoriank.whatsappWebClone.entity.Message;
import com.grigoriank.whatsappWebClone.entity.User;
import com.grigoriank.whatsappWebClone.exception.ChatException;
import com.grigoriank.whatsappWebClone.exception.MessageException;
import com.grigoriank.whatsappWebClone.exception.UserException;

import java.util.List;

public interface MessageService {

    Message sendMessage(SendMessageRequestDTO sendMsgReqDTO) throws UserException, ChatException;

    List<Message> getChatsMessages(Long chatId, User userReq) throws UserException, ChatException;

    Message findMessageById(Long messageId) throws MessageException;

    void deleteMessage(Long messageId, User userReq) throws UserException, MessageException;
}

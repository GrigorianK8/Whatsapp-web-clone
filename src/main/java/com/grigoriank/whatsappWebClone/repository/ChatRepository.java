package com.grigoriank.whatsappWebClone.repository;

import com.grigoriank.whatsappWebClone.entity.Chat;
import com.grigoriank.whatsappWebClone.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ChatRepository extends JpaRepository<Chat, Long> {

    @Query("select c from Chat c join c.users u where u.id = : userId")
    List<Chat> findChatByUserId(@Param("userId") Long userId);

    @Query("select c from Chat c where c.isGroup=false and : user member of c.users and : userReq member of c.users")
    Chat findSingleChatByUserId(@Param("user") User user, @Param("userReq") User userReq);
}

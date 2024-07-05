package com.grigoriank.whatsappWebClone.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SendMessageRequestDTO {

    private Long userId;
    private Long chatId;
    private String content;
}

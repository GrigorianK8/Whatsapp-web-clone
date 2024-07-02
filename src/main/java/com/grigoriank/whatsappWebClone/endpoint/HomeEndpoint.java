package com.grigoriank.whatsappWebClone.endpoint;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeEndpoint {

    @GetMapping("/")
    public ResponseEntity<String> home() {
        return new ResponseEntity<>("welcome to our whatsapp api", HttpStatus.OK);
    }
}

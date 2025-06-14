package bitespeed.backend.test.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
public class ContactController {

    @PostMapping("/identify")
    public ResponseEntity<?> indetifyContact() {

        return ResponseEntity.ok("Hello");
    }
}

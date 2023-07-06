package uz.mkb.start_template.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import uz.mkb.start_template.config.MyRestTemplate;

@RestController
@RequestMapping("/api/v1/demo")
@RequiredArgsConstructor
public class DemoController {

    private final MyRestTemplate myRestTemplate;

    @PostMapping("/")
    public ResponseEntity<?> demo(){
        return ResponseEntity.ok("OK");
    }
}
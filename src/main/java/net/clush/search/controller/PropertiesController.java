package net.clush.search.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import net.clush.search.util.PropertiesUtil;
import net.clush.search.dto.PropertiesResponseDTO;

@RestController
@RequestMapping("/properties")
@RequiredArgsConstructor
@Tag(name = "Properties API")
public class PropertiesController {
    
    private final PropertiesUtil propertiesUtil;
    
    @GetMapping("/load")
    public ResponseEntity<?> reloadProperties() {
        try {
            propertiesUtil.init();
            return ResponseEntity.ok(new PropertiesResponseDTO("ok", "load success", 200));
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                .body(new PropertiesResponseDTO("fail", "load fail", 400));
        }
    }
}
package net.clush.search.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import net.clush.search.dto.SearchProperties;
import net.clush.search.util.PropertiesUtil;
import net.clush.search.dto.LoadResponse;

@RestController
@RequestMapping("/properties")
@RequiredArgsConstructor
public class PropertiesController {
    
    private final PropertiesUtil propertiesUtil;
    
    @GetMapping("/load")
    public ResponseEntity<?> reloadProperties() {
        try {
            propertiesUtil.init();
            return ResponseEntity.ok(new LoadResponse("ok", "load success", 200));
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                .body(new LoadResponse("fail", "load fail", 400));
        }
    }
}
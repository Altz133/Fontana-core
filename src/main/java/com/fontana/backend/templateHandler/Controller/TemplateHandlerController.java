package com.fontana.backend.templateHandler.Controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.fontana.backend.config.RestEndpoints.TEMPLATE;
import static com.fontana.backend.config.RestEndpoints.TEMPLATE_GET_PAGING;

@RestController
@RequestMapping(TEMPLATE)
@RequiredArgsConstructor
public class TemplateHandlerController {

    @PostMapping(value = TEMPLATE_GET_PAGING)
    public ResponseEntity<Object> getTemplatesPaging(int page) {
        return ResponseEntity.ok().build();
    }




}

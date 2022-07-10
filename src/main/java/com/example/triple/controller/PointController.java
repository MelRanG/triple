package com.example.triple.controller;

import com.example.triple.dto.EventDto;
import com.example.triple.service.PointService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PointController {

    PointService service;

    public PointController(PointService service){
        this.service = service;
    }

    @PostMapping("/events")
    public ResponseEntity review(@RequestBody EventDto dto){
        if(service.DispatcherType(dto) == null) System.out.println("service에서 null호출");;
        return new ResponseEntity(HttpStatus.OK);
    }

}

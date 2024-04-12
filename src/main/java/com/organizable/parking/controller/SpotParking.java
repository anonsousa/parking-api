package com.organizable.parking.controller;


import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/parking")
public class SpotParking {

    @GetMapping
    public ResponseEntity testing(){
        String name = "tudo ok!";

        return ResponseEntity.ok(name);
    }

}

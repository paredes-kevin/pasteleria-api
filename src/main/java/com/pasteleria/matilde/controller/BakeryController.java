package com.pasteleria.matilde.controller;


import com.pasteleria.matilde.dto.BakeryDto;
import com.pasteleria.matilde.model.Bakery;
import com.pasteleria.matilde.model.User;
import com.pasteleria.matilde.request.CreateBakeryRequest;
import com.pasteleria.matilde.service.BakeryService;
import com.pasteleria.matilde.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/bakery")
public class BakeryController {

    @Autowired
    private BakeryService bakeryService;

    @Autowired
    private UserService userService;

    @GetMapping("/search")
    public ResponseEntity<List<Bakery>> searchBakery(
            @RequestHeader("Authorization") String jwt,
            @RequestParam String keyword
    ) throws Exception {
        User user = userService.findUserByJwtToken(jwt);

        List<Bakery> bakery = bakeryService.searchBakery(keyword);
        return new ResponseEntity<>(bakery, HttpStatus.OK);
    }

    @GetMapping()
    public ResponseEntity<List<Bakery>> getAllBakery(
            @RequestHeader("Authorization") String jwt
    ) throws Exception {
        User user = userService.findUserByJwtToken(jwt);

        List<Bakery> bakery = bakeryService.getAllBakery();
        return new ResponseEntity<>(bakery, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Bakery> findBakeryById(
            @RequestHeader("Authorization") String jwt,
            @PathVariable Long id

    ) throws Exception {
        User user = userService.findUserByJwtToken(jwt);

        Bakery bakery = bakeryService.findBakeryById(id);
        return new ResponseEntity<>(bakery, HttpStatus.OK);
    }

    @PutMapping("/{id}/add-favorites")
    public ResponseEntity<BakeryDto> addToFavorites(
            @RequestHeader("Authorization") String jwt,
            @PathVariable Long id

    ) throws Exception {
        User user = userService.findUserByJwtToken(jwt);

        BakeryDto bakery = bakeryService.addToFavorites(id, user);

        return new ResponseEntity<>(bakery, HttpStatus.OK);
    }
}

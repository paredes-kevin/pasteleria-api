package com.pasteleria.matilde.controller;

import com.pasteleria.matilde.model.Bakery;
import com.pasteleria.matilde.model.User;
import com.pasteleria.matilde.request.CreateBakeryRequest;
import com.pasteleria.matilde.response.MessageResponse;
import com.pasteleria.matilde.service.BakeryService;
import com.pasteleria.matilde.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin/bakeries")
public class AdminBakeryController {

    @Autowired
    private BakeryService bakeryService;

    @Autowired
    private UserService userService;

    @PostMapping()
    public ResponseEntity<Bakery> createBakery(
            @RequestBody CreateBakeryRequest req,
            @RequestHeader("Authorization") String jwt
            ) throws Exception {
        User user = userService.findUserByJwtToken(jwt);

        Bakery bakery = bakeryService.createBakery(req, user);
        return new ResponseEntity<>(bakery, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Bakery> updateBakery(
            @RequestBody CreateBakeryRequest req,
            @RequestHeader("Authorization") String jwt,
            @PathVariable Long id
    ) throws Exception {
        User user = userService.findUserByJwtToken(jwt);

        Bakery bakery = bakeryService.updateBakery(id, req);
        return new ResponseEntity<>(bakery, HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<MessageResponse> deleteBakery(

            @RequestHeader("Authorization") String jwt,
            @PathVariable Long id
    ) throws Exception {
        User user = userService.findUserByJwtToken(jwt);

        bakeryService.deleteBakery(id);

        MessageResponse res= new MessageResponse();
        res.setMessage("bakery delete successfully");
        return new ResponseEntity<>(res, HttpStatus.OK);
    }

    @PutMapping("/{id}/status")
    public ResponseEntity<Bakery> updateBakeryStatus(

            @RequestHeader("Authorization") String jwt,
            @PathVariable Long id
    ) throws Exception {
        User user = userService.findUserByJwtToken(jwt);

        Bakery bakery=bakeryService.updateBakeryStatus(id);
        return new ResponseEntity<>(bakery, HttpStatus.OK);
    }

    @GetMapping("/user")
    public ResponseEntity<Bakery> findBakeryByUserId(

            @RequestHeader("Authorization") String jwt
    ) throws Exception {
        User user = userService.findUserByJwtToken(jwt);

        Bakery bakery=bakeryService.getBakeryByUserId(user.getId());
        return new ResponseEntity<>(bakery, HttpStatus.OK);
    }
}

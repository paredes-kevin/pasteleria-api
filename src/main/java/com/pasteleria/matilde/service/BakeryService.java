package com.pasteleria.matilde.service;

import com.pasteleria.matilde.dto.BakeryDto;
import com.pasteleria.matilde.model.Bakery;
import com.pasteleria.matilde.model.User;
import com.pasteleria.matilde.request.CreateBakeryRequest;

import java.util.List;

public interface BakeryService {

    public Bakery createBakery(CreateBakeryRequest req, User user);

    public Bakery updateBakery(Long bakeryId, CreateBakeryRequest updateBakery) throws Exception;

    public void deleteBakery(Long bakeryId) throws Exception;

    public List<Bakery> getAllBakery();

    public List<Bakery> searchBakery(String keyword);

    public Bakery findBakeryById(Long id) throws Exception;

    public Bakery getBakeryByUserId(Long userId) throws Exception;

    public BakeryDto addToFavorites(Long bakeryId, User user) throws Exception;

    public Bakery updateBakeryStatus(Long id) throws Exception;


}

package com.pasteleria.matilde.service;

import com.pasteleria.matilde.dto.BakeryDto;
import com.pasteleria.matilde.model.Address;
import com.pasteleria.matilde.model.Bakery;
import com.pasteleria.matilde.model.User;
import com.pasteleria.matilde.repository.AddressRepository;
import com.pasteleria.matilde.repository.BakeryRepository;
import com.pasteleria.matilde.repository.UserRepository;
import com.pasteleria.matilde.request.CreateBakeryRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class BakeryServiceImp implements BakeryService {

    @Autowired
    private BakeryRepository bakeryRepository;

    @Autowired
    private AddressRepository addressRepository;

    @Autowired
    private UserRepository userRepository;

    @Override
    public Bakery createBakery(CreateBakeryRequest req, User user) {

        Address address = addressRepository.save(req.getAddress());

        Bakery bakery = new Bakery();
        bakery.setAddress(address);
        bakery.setContactInformation(req.getContactInformation());
        bakery.setCuisineType(req.getCuisineType());
        bakery.setDescription(req.getDescription());
        bakery.setImages(req.getImages());
        bakery.setName(req.getName());
        bakery.setOpeningHours(req.getOpningHours());
        bakery.setRegistrationDate(LocalDateTime.now());
        bakery.setOwner(user);
        return bakeryRepository.save(bakery);
    }

    @Override
    public Bakery updateBakery(Long bakeryId, CreateBakeryRequest updateBakery) throws Exception {
        Bakery bakery=findBakeryById(bakeryId);

        if(bakery.getCuisineType()!=null){
            bakery.setCuisineType(updateBakery.getCuisineType());
        }
        if(bakery.getDescription()!=null){
            bakery.setDescription(updateBakery.getDescription());
        }
        if(bakery.getName()!=null){
            bakery.setName(updateBakery.getName());
        }

        return bakeryRepository.save(bakery);
    }

    @Override
    public void deleteBakery(Long bakeryId) throws Exception {

        Bakery bakery=findBakeryById(bakeryId);

        bakeryRepository.delete(bakery);
    }

    @Override
    public List<Bakery> getAllBakery() {
        return bakeryRepository.findAll();
    }

    @Override
    public List<Bakery> searchBakery(String keyword) {
        return bakeryRepository.findBySearchQuery(keyword);
    }

    @Override
    public Bakery findBakeryById(Long id) throws Exception {
        Optional<Bakery> opt=bakeryRepository.findById(id);

        if(opt.isEmpty()){
            throw  new Exception("bakery not found with id" + id);
        }
        return opt.get();
    }

    @Override
    public Bakery getBakeryByUserId(Long userId) throws Exception {
        Bakery bakery = bakeryRepository.findByOwnerId(userId);
        if(bakery==null){
            throw new Exception("bakery not found with owner id" + userId);
        }
        return bakery;
    }

    @Override
    public BakeryDto addToFavorites(Long bakeryId, User user) throws Exception {
        Bakery bakery= findBakeryById(bakeryId);

        BakeryDto dto = new BakeryDto();
        dto.setDescription(bakery.getDescription());
        dto.setImages(bakery.getImages());
        dto.setTitle(bakery.getName());
        dto.setId(bakeryId);

        boolean isFavorited = false;
        List<BakeryDto> favorites = user.getFavorites();
        for ( BakeryDto favorite: favorites) {
            if (favorite.getId().equals(bakeryId)){
                isFavorited = true;
                break;
            }
        }

        if(isFavorited){
            favorites.removeIf(favorite -> favorite.getId().equals(bakeryId));
        } else {
            favorites.add(dto);
        }

        userRepository.save(user);
        return dto;
    }

    @Override
    public Bakery updateBakeryStatus(Long id) throws Exception {
        Bakery bakery= findBakeryById(id);
        bakery.setOpen(!bakery.isOpen());
        return bakeryRepository.save(bakery);
    }
}

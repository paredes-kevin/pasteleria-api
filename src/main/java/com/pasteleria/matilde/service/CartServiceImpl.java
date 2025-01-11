package com.pasteleria.matilde.service;

import com.pasteleria.matilde.model.Cart;
import com.pasteleria.matilde.model.CartItem;
import com.pasteleria.matilde.model.Food;
import com.pasteleria.matilde.model.User;
import com.pasteleria.matilde.repository.CartItemRepository;
import com.pasteleria.matilde.repository.CartRepository;
import com.pasteleria.matilde.request.AddCartItemRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CartServiceImpl implements CartService{

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private CartItemRepository cartItemRepository;

    @Autowired
    private FoodService foodService;

    @Override
    public CartItem addItemToCart(AddCartItemRequest req, String jwt) throws Exception {
        User user= userService.findUserByJwtToken(jwt);

        Food food= foodService.findFoodById(req.getFoodId());

        Cart cart = cartRepository.findByCustomerId(user.getId());

        for (CartItem cartItem : cart.getItems()){
            if(cartItem.getFood().equals(food)){
                int newQuantity=cartItem.getQuantity()+req.getQuantity();
                return updateCartItemQuantity(cartItem.getId(), newQuantity);
            }
        }

        CartItem newCartItem= new CartItem();
        newCartItem.setFood(food);
        newCartItem.setCart(cart);
        newCartItem.setQuantity(req.getQuantity());
        newCartItem.setIngredients(req.getIngredients());
        newCartItem.setTotalPrice(req.getQuantity()*food.getPrice());

        CartItem savedCartItem = cartItemRepository.save(newCartItem);

        cart.getItems().add(savedCartItem);
        return savedCartItem;
    }

    @Override
    public CartItem updateCartItemQuantity(Long cartItemId, int quantity) throws Exception {

        Optional<CartItem> cartItemOptional = cartItemRepository.findById(cartItemId);
        if(cartItemOptional.isEmpty()){
            throw new Exception("cart item not fount");
        }
        CartItem item1 = cartItemOptional.get();
        item1.setQuantity(quantity);
        item1.setTotalPrice(item1.getFood().getPrice()*quantity);
        return cartItemRepository.save(item1);
    }

    @Override
    public Cart removeItemFromCart(Long cartItemId, String jwt) throws Exception {

        User user= userService.findUserByJwtToken(jwt);


        Cart cart = cartRepository.findByCustomerId(user.getId());

        Optional<CartItem> cartItemOptional = cartItemRepository.findById(cartItemId);
        if(cartItemOptional.isEmpty()){
            throw new Exception("cart item not fount");
        }

        CartItem item=cartItemOptional.get();

        cart.getItems().remove(item);
        return cartRepository.save(cart);
    }

    @Override
    public Long calculateCartTotals(Cart cart) throws Exception {
        Long total = 0L;

        for(CartItem cartItem : cart.getItems()){
            total+=cartItem.getFood().getPrice()*cartItem.getQuantity();
        }
        return total;
    }

    @Override
    public Cart findCartById(Long id) throws Exception {
        Optional<Cart> optionalCart=cartRepository.findById(id);
        if (optionalCart.isEmpty()) {
            throw new Exception("cart not found with id" + id);
        }
        return optionalCart.get();
    }

    @Override
    public Cart findCartByUserId(Long userId) throws Exception {
        //User user = userService.findUserByJwtToken(jwt);
        Cart cart = cartRepository.findByCustomerId(userId);
        cart.setTotal(calculateCartTotals(cart));
        return  cart;
    }

    @Override
    public Cart clearCart(Long userid) throws Exception {
        //User user = userService.findUserByJwtToken(jwt);
        Cart cart = findCartByUserId(userid);

        cart.getItems().clear();
        return cartRepository.save(cart);
    }
}

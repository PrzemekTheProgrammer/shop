package pl.com.coders.shop2.controller;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import pl.com.coders.shop2.dto.CartDTO;
import pl.com.coders.shop2.service.CartService;

@RestController
@AllArgsConstructor
@RequestMapping("/cart")
public class CartController {

    private final CartService cartService;

    @PostMapping("/{productTitle}/{amount}/addToCart")
    public CartDTO addToCart(@PathVariable String productTitle, @PathVariable int amount, @RequestParam String userEmail) {
        return cartService.addToCart(productTitle, amount, userEmail);
    }

    @DeleteMapping("/cartItem/deleteByIndex")
    public Boolean deleteCartItemByIndex(@RequestParam String userEmail, @RequestParam Integer index) {
        return cartService.deleteCartItemByIndex(index, userEmail);
    }

    @GetMapping
    public CartDTO getCartByUserEmail(@RequestParam String userEmail) {
        return cartService.getCartByUserEmail(userEmail);
    }

}

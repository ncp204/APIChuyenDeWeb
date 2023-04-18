package vn.edu.hcmuaf.st.chuyendeweb.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.edu.hcmuaf.st.chuyendeweb.dto.request.CartDTO;
import vn.edu.hcmuaf.st.chuyendeweb.dto.request.LaptopDTO;
import vn.edu.hcmuaf.st.chuyendeweb.dto.request.TokenAndIdsDTO;
import vn.edu.hcmuaf.st.chuyendeweb.dto.response.ResponMessenger;
import vn.edu.hcmuaf.st.chuyendeweb.service.impl.CartService;

import java.util.List;

@RestController
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
public class CartController {
    private final CartService cartService;

    @GetMapping("/cart/laptops")
    public CartDTO getLaptopsFromCart(@RequestParam String token) {
        return cartService.findLaptopByUser(token);
    }

    @PostMapping("/cart/laptop/add")
    public ResponseEntity<?> addLaptopToCart(@RequestParam String token, @RequestParam Long laptopId, @RequestParam Integer quantity) {
        cartService.addLaptopToCart(token, laptopId, quantity);
        return new ResponseEntity<>(new ResponMessenger("Đã thêm thành công sản phẩm vào giỏ hàng"), HttpStatus.OK);
    }

    @DeleteMapping("/cart/laptop/remove")
    public ResponseEntity<?> removeLaptopInCart(@RequestBody TokenAndIdsDTO dto) {
        cartService.removeLaptopInCart(dto);
        return new ResponseEntity<>(new ResponMessenger("Đã xóa thành công sản phẩm khỏi giỏ hàng"), HttpStatus.OK);
    }

    @PutMapping("/cart/laptop/reduce")
    public ResponseEntity<?> reduceLaptopQuantityInCart(@RequestBody TokenAndIdsDTO dto) {
        cartService.reduceLaptopQuantity(dto);
        return new ResponseEntity<>(new ResponMessenger("Đã giảm số lượng sản phẩm thành công"), HttpStatus.OK);
    }
}

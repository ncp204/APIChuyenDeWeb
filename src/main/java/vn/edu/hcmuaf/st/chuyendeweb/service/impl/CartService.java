package vn.edu.hcmuaf.st.chuyendeweb.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import vn.edu.hcmuaf.st.chuyendeweb.converter.LaptopConverter;
import vn.edu.hcmuaf.st.chuyendeweb.dto.request.CartDTO;
import vn.edu.hcmuaf.st.chuyendeweb.dto.request.LaptopDTO;
import vn.edu.hcmuaf.st.chuyendeweb.dto.request.TokenAndIdsDTO;
import vn.edu.hcmuaf.st.chuyendeweb.exception.ServiceException;
import vn.edu.hcmuaf.st.chuyendeweb.model.entity.*;
import vn.edu.hcmuaf.st.chuyendeweb.repository.AccountRepository;
import vn.edu.hcmuaf.st.chuyendeweb.repository.CartLaptopRepository;
import vn.edu.hcmuaf.st.chuyendeweb.repository.CartRepository;
import vn.edu.hcmuaf.st.chuyendeweb.repository.LaptopRepository;
import vn.edu.hcmuaf.st.chuyendeweb.security.jwt.JwtTokenProvider;
import vn.edu.hcmuaf.st.chuyendeweb.service.ICartService;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CartService implements ICartService {
    private final LaptopRepository laptopRepository;
    private final JwtTokenProvider jwtTokenProvider;
    private final LaptopConverter laptopConverter;
    private final AccountRepository accountRepository;
    private final CartRepository cartRepository;
    private final CartLaptopRepository cartLaptopRepository;

    @Override
    public CartDTO findLaptopByUser(String token) {
        CartDTO cartDTO = new CartDTO();
        String username = jwtTokenProvider.getUserNameFromToken(token);
        Optional<Account> accountOptional = accountRepository.findByUserName(username);
        if (accountOptional.isPresent()) {
            Account account = accountOptional.get();
            Cart cart = account.getCart();
            List<CartLaptop> cartLaptopList = cart.getCartLaptops();
            List<LaptopDTO> dtoList = cartDTO.getLaptopDTOs();

            Laptop laptop;
            LaptopDTO laptopDTO;
            if (!cartLaptopList.isEmpty()) {
                for (CartLaptop cartLaptop : cartLaptopList) {
                    laptop = cartLaptop.getLaptop();
                    laptop.setQuantity(cartLaptop.getQuantity());
                    laptopDTO = laptopConverter.toLaptopDTO(laptop);
                    laptopDTO.setTotalAmout(laptopDTO.getPrice() * laptopDTO.getQuantity());
                    dtoList.add(laptopDTO);
                    cartDTO.setTotalPayment(cartDTO.getTotalPayment() + laptopDTO.getTotalAmout());
                }
            }
            return cartDTO;
        }
        return new CartDTO();
    }

    @Override
    public void addLaptopToCart(String token, Long laptopId, Integer quantity) {
        String username = jwtTokenProvider.getUserNameFromToken(token);
        Optional<Account> accountOptional = accountRepository.findByUserName(username);
        Optional<Laptop> laptopOptional = laptopRepository.findById(laptopId);
        if (accountOptional.isEmpty()) {
            throw new ServiceException(HttpStatus.INTERNAL_SERVER_ERROR, "Tài khoản không tồn tại, vui lòng kiểm tra lại");
        }
        if (laptopOptional.isEmpty()) {
            throw new ServiceException(HttpStatus.INTERNAL_SERVER_ERROR, "Sản phẩm không tồn tại, vui lòng kiểm tra lại");
        }
        if (laptopOptional.get().getQuantity() < 1) {
            throw new ServiceException(HttpStatus.INTERNAL_SERVER_ERROR, "Sản phẩm đã hết hàng");
        }

        Account account = accountOptional.get();
        Laptop laptop = laptopOptional.get();
        Cart cart = account.getCart();
        List<CartLaptop> cartLaptops = cart.getCartLaptops();
        CartLaptop cartLaptop = null;

        for (CartLaptop cl : cartLaptops) {
            if (cl.getLaptop().getId().equals(laptopId)) {
                cartLaptop = cl;
                break;
            }
        }

        if (cartLaptop == null) {
            cartLaptop = new CartLaptop();
            cartLaptop.setCart(cart);
            cartLaptop.setLaptop(laptop);
            cartLaptop.setQuantity(quantity);
            laptop.getCartLaptops().add(cartLaptop);
        } else {
            cartLaptop.setQuantity(cartLaptop.getQuantity() + quantity);
        }

        accountRepository.save(account);
    }

    @Override
    @Transactional
    public void removeLaptopInCart(TokenAndIdsDTO dto) {
        String token = dto.getToken();
        List<Long> ids = dto.getIds();
        String username = jwtTokenProvider.getUserNameFromToken(token);
        Optional<Account> accountOptional = accountRepository.findByUserName(username);
        if (accountOptional.isPresent()) {
            Account account = accountOptional.get();
            Cart cart = account.getCart();
            List<CartLaptop> cartLaptops = cart.getCartLaptops();
            Optional<CartLaptop> cartLaptopOptional;
            for (Long id : ids) {
                cartLaptopOptional = cartLaptops.stream().filter(cartLaptop ->
                        cartLaptop.getLaptop().getId().equals(id)).findFirst();
                if (cartLaptopOptional.isPresent()) {
                    CartLaptop cartLaptop = cartLaptopOptional.get();
                    cartLaptops.remove(cartLaptop);
                    cartLaptopRepository.delete(cartLaptop);
                }
            }
            accountRepository.save(account);
        }
    }

    @Override
    public void reduceLaptopQuantity(TokenAndIdsDTO dto) {
        String token = dto.getToken();
        String username = jwtTokenProvider.getUserNameFromToken(token);
        Optional<Account> accountOptional = accountRepository.findByUserName(username);
        if (accountOptional.isPresent()) {
            Account account = accountOptional.get();
            Cart cart = account.getCart();
            List<CartLaptop> cartLaptops = cart.getCartLaptops();
            Optional<CartLaptop> cartLaptopOptional;
            for (Long id : dto.getIds()) {
                cartLaptopOptional = cartLaptops.stream().filter(cartLaptop ->
                        cartLaptop.getLaptop().getId().equals(id)).findFirst();
                if (cartLaptopOptional.isPresent()) {
                    CartLaptop cartLaptop = cartLaptopOptional.get();
                    if(cartLaptop.getQuantity() > 1) {
                        cartLaptop.setQuantity(cartLaptop.getQuantity() -1);
                        cartLaptopRepository.save(cartLaptop);
                    }
                }
            }
        }
    }
}

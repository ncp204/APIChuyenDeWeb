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
import vn.edu.hcmuaf.st.chuyendeweb.repository.LaptopRepository;
import vn.edu.hcmuaf.st.chuyendeweb.security.jwt.JwtTokenProvider;
import vn.edu.hcmuaf.st.chuyendeweb.service.ICartService;

import javax.transaction.Transactional;
import java.util.*;

@Service
@RequiredArgsConstructor
public class CartService implements ICartService {
    private final LaptopRepository laptopRepository;
    private final JwtTokenProvider jwtTokenProvider;
    private final LaptopConverter laptopConverter;
    private final AccountRepository accountRepository;

    @Override
    public CartDTO findLaptopByUser(String token) {
        CartDTO cartDTO = new CartDTO();
        String username = jwtTokenProvider.getUserNameFromToken(token);
        Optional<Account> accountOptional = accountRepository.findByUserName(username);
        if (accountOptional.isPresent()) {
            Account account = accountOptional.get();
            Cart cart = account.getCart();
            Map<Laptop, Integer> cartLaptop = cart.getCartLaptop();
            List<LaptopDTO> dtoList = cartDTO.getLaptopDTOs();

            LaptopDTO laptopDTO;
            if (!cartLaptop.isEmpty()) {
                List<Laptop> laptopList = new ArrayList<>(cartLaptop.keySet());
                for (Laptop laptop : laptopList) {
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
    @Transactional
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

        Map<Laptop, Integer> cartLaptop = cart.getCartLaptop();
        if (cartLaptop.containsKey(laptop)) {
            cartLaptop.put(laptop, quantity + cartLaptop.get(laptop));
        } else {
            cartLaptop.put(laptop, quantity);
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
            Map<Laptop, Integer> cartLaptop = cart.getCartLaptop();

            Optional<Laptop> optionalLaptop;
            Laptop laptop;
            for (Long id : ids) {
                optionalLaptop = laptopRepository.findById(id);
                if (optionalLaptop.isPresent()) {
                    laptop = optionalLaptop.get();
                    cartLaptop.remove(laptop);
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
            Map<Laptop, Integer> cartLaptop = cart.getCartLaptop();

            Optional<Laptop> optionalLaptop;
            Laptop laptop;
            int quantity;
            for (Long id : dto.getIds()) {
                optionalLaptop = laptopRepository.findById(id);
                if (optionalLaptop.isPresent()) {
                    laptop = optionalLaptop.get();
                    if (cartLaptop.containsKey(laptop)) {
                        quantity = cartLaptop.get(laptop);
                        cartLaptop.put(laptop, quantity > 1 ? quantity - 1 : 1);
                    }
                }
            }
            accountRepository.save(account);
        }
    }
}

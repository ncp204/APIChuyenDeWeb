package vn.edu.hcmuaf.st.chuyendeweb.service;

import vn.edu.hcmuaf.st.chuyendeweb.dto.request.CartDTO;
import vn.edu.hcmuaf.st.chuyendeweb.dto.request.TokenAndIdsDTO;

public interface ICartService {
   CartDTO findLaptopByUser(String token);
   void addLaptopToCart(String token, Long laptopId, Integer quantity);
   void removeLaptopInCart(TokenAndIdsDTO dto);

   void reduceLaptopQuantity(TokenAndIdsDTO dto);
}

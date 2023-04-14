package vn.edu.hcmuaf.st.chuyendeweb.evenlistener;

import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import vn.edu.hcmuaf.st.chuyendeweb.model.entity.Account;
import vn.edu.hcmuaf.st.chuyendeweb.model.entity.Cart;
import vn.edu.hcmuaf.st.chuyendeweb.repository.CartRepository;

@Component
@RequiredArgsConstructor
public class AccountCreatedListener {
    private final CartRepository cartRepository;

    @EventListener
    public void handleAccountCreateEvent(AccountCreatedEvent event) {
        Account account = event.getAccount();
        Cart cart = new Cart();
        cart.setAccount(account);
        cartRepository.save(cart);
    }
}

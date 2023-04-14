package vn.edu.hcmuaf.st.chuyendeweb.evenlistener;

import lombok.Getter;
import vn.edu.hcmuaf.st.chuyendeweb.model.entity.Account;

@Getter
public class AccountCreatedEvent {
    private Account account;

    public AccountCreatedEvent(Account newAccount) {
        this.account = newAccount;
    }
}

package vn.edu.hcmuaf.st.chuyendeweb.converter;

import org.springframework.stereotype.Component;
import vn.edu.hcmuaf.st.chuyendeweb.dto.request.AccountDTO;
import vn.edu.hcmuaf.st.chuyendeweb.model.entity.Account;

@Component
public class AccountConverter {

    public Account toAccount(AccountDTO dto) {
        Account account = new Account();
        account.setUserName(dto.getUserName());
        account.setPassword(dto.getPassword());
        account.setFullName(dto.getFullName());
        account.setPhone(dto.getPhone());
        account.setEmail(dto.getEmail());
        account.setSex(dto.getSex());
        account.setAddress(dto.getAddress());
        account.setAddressDetail(dto.getAddressDetail());
        account.setState(dto.getState());
        account.setRoles(dto.getRoles());
        return account;
    }

    public AccountDTO toAccountDTO(Account account) {
        AccountDTO dto = AccountDTO.builder()
                .userName(account.getUserName())
                .password(account.getPassword())
                .fullName(account.getFullName())
                .phone(account.getPhone())
                .email(account.getEmail())
                .sex(account.getSex())
                .address(account.getAddress())
                .addressDetail(account.getAddressDetail())
                .state(account.getState())
                .build();
        if (null != dto.getId()) {
            dto.setId(account.getId());
        }
        return dto;
    }

    public Account toAccount(AccountDTO dto, Account account) {
        account.setUserName(dto.getUserName());
        account.setPassword(dto.getPassword());
        account.setFullName(dto.getFullName());
        account.setPhone(dto.getPhone());
        account.setEmail(dto.getEmail());
        account.setSex(dto.getSex());
        account.setAddress(dto.getAddress());
        account.setAddressDetail(dto.getAddressDetail());
        account.setState(dto.getState());
        account.setRoles(dto.getRoles());
        return account;
    }
}

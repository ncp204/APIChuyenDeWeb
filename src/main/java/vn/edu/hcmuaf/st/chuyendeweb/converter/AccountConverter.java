package vn.edu.hcmuaf.st.chuyendeweb.converter;

import org.springframework.stereotype.Component;
import vn.edu.hcmuaf.st.chuyendeweb.dto.request.AccountDTO;
import vn.edu.hcmuaf.st.chuyendeweb.model.entity.Account;

@Component
public class AccountConverter {

    public Account toAccount(AccountDTO dto) {
        Account account = new Account();
        account = toAccount(dto, account);
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
        if (dto.getUserName() != null && dto.getUserName().trim().length() > 0) {
            account.setUserName(dto.getUserName());
        }
        if (dto.getPassword() != null && dto.getPassword().trim().length() > 0) {
            account.setPassword(dto.getPassword());
        }
        if (dto.getFullName() != null && dto.getFullName().trim().length() > 0) {
            account.setFullName(dto.getFullName());
        }
        if (dto.getPhone() != null && dto.getPhone().trim().length() > 0) {
            account.setPhone(dto.getPhone());
        }
        if (dto.getEmail() != null && dto.getEmail().trim().length() > 0) {
            account.setEmail(dto.getEmail());
        }
        if (dto.getSex() != null && dto.getSex().trim().length() > 0) {
            account.setSex(dto.getSex());
        }
        if (dto.getAddress() != null && dto.getAddress().trim().length() > 0) {
            account.setAddress(dto.getAddress());
        }
        if (dto.getAddressDetail() != null && dto.getAddressDetail().trim().length() > 0) {
            account.setAddressDetail(dto.getAddressDetail());
        }
        if (dto.getState() != null) {
            account.setState(dto.getState());
        }
        if (dto.getRoles() != null && dto.getRoles().size() > 0) {
            account.setRoles(dto.getRoles());
        }
        return account;
    }
}

package vn.edu.hcmuaf.st.chuyendeweb.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import vn.edu.hcmuaf.st.chuyendeweb.model.State;

@Getter
@Setter
@Builder
public class AccountDTO extends AbstractDTO<AccountDTO>{
    private String userName;
    private String password;
    private String fullName;
    private String phone;
    private String email;
    private String sex;
    private String address;
    private String addressDetail;
    private Integer status;
    private State state;
}

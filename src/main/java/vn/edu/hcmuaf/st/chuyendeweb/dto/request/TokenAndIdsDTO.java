package vn.edu.hcmuaf.st.chuyendeweb.dto.request;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class TokenAndIdsDTO {
    private String token;
    private List<Long> ids;
}

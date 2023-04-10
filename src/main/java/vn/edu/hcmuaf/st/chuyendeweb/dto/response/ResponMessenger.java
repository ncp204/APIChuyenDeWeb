package vn.edu.hcmuaf.st.chuyendeweb.dto.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResponMessenger {
    private String messenger;

    public ResponMessenger() {

    }

    public ResponMessenger(String messenger) {
        this.messenger = messenger;
    }
}

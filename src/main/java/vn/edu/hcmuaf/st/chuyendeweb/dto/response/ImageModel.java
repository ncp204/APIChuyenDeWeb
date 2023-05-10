package vn.edu.hcmuaf.st.chuyendeweb.dto.response;

import lombok.*;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ImageModel {
    private Long uid;
    private String name;
    private String url;
}

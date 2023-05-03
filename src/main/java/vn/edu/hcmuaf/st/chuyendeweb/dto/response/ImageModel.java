package vn.edu.hcmuaf.st.chuyendeweb.dto.response;

import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ImageModel {
    private Long id;
    private String imageName;
    private String imageLink;
}

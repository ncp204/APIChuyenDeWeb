package vn.edu.hcmuaf.st.chuyendeweb.dto.request;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class LaptopFilter {
    private List<String> types = new ArrayList<>();
    private List<String> brands = new ArrayList<>();
    private List<String> chipCpus= new ArrayList<>();
}

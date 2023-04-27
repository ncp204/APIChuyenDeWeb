package vn.edu.hcmuaf.st.chuyendeweb.service.impl;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import vn.edu.hcmuaf.st.chuyendeweb.dto.request.LaptopDTO;
import vn.edu.hcmuaf.st.chuyendeweb.mail.GMailer;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Map;

public class Test {

    public static void main(String[] args) throws Exception {
//        // Tạo đối tượng Gson
//        Gson gson = new Gson();
//        String jsonString = "{\"battery\":\"Thinkpad x1 carbon gen 10\",\"brand\":\"Thinkpad x1 carbon gen 10\",\"chipCpu\":\"Thinkpad x1 carbon gen 10\",\"color\":\"Thinkpad x1 carbon gen 10\",\"cpu\":\"Thinkpad x1 carbon gen 10\",\"display\":\"Thinkpad x1 carbon gen 10\",\"graphics\":\"Thinkpad x1 carbon gen 10\",\"laptopState\":\"Thinkpad x1 carbon gen 10\",\"price\":\"100\",\"productName\":\"Thinkpad x1 carbon gen 10\",\"quantity\":\"10\",\"ram\":\"Thinkpad x1 carbon gen 10\",\"storage\":\"Thinkpad x1 carbon gen 10\",\"type\":\"Thinkpad x1 carbon gen 10\",\"weight\":\"12\"}";
//        // Chuyển đổi chuỗi thành đối tượng JSON
//        JsonObject jsonObject = gson.fromJson(jsonString, JsonObject.class);
//        LaptopDTO laptopDTO = gson.fromJson(jsonObject, LaptopDTO.class);


        String email = "19130172@st.hcmuaf.edu.vn";
        String subject = "test";
        String body = "Test gui mail";
        new GMailer().sendMail(email, subject, body);
    }
}

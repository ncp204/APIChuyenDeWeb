package vn.edu.hcmuaf.st.chuyendeweb.service.impl;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Map;

public class Test {

    public static void main(String[] args) {
        Cloudinary cloudinary = new Cloudinary(ObjectUtils.asMap(
                "cloud_name", "diyrrlmqk",
                "api_key", "137284888978213",
                "api_secret", "Rxu7XVXAxkeUXoEcwgt1s4dSpAs"));

        try {
            File file = new File("D:\\chuyenDeWeb\\git\\APIChuyenDeWeb\\src\\main\\resources\\images\\gravity falls.jpg");
            String fileName = file.getName().substring(0, file.getName().lastIndexOf("."));
            Map params = ObjectUtils.asMap(
                    "public_id", fileName+" 2"
            );
            Map uploadResult = cloudinary.uploader().upload(file, params);
            String linkImage = (String) uploadResult.get("url");
            System.out.println("\r\n"+linkImage +" hahahahaha");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

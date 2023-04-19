package vn.edu.hcmuaf.st.chuyendeweb.service.impl;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import vn.edu.hcmuaf.st.chuyendeweb.converter.LaptopConverter;
import vn.edu.hcmuaf.st.chuyendeweb.dto.request.LaptopDTO;
import vn.edu.hcmuaf.st.chuyendeweb.dto.request.LaptopFilter;
import vn.edu.hcmuaf.st.chuyendeweb.exception.ServiceException;
import vn.edu.hcmuaf.st.chuyendeweb.model.CPU;
import vn.edu.hcmuaf.st.chuyendeweb.model.ImageModel;
import vn.edu.hcmuaf.st.chuyendeweb.model.entity.Facility;
import vn.edu.hcmuaf.st.chuyendeweb.model.entity.ImageLaptop;
import vn.edu.hcmuaf.st.chuyendeweb.model.entity.Laptop;
import vn.edu.hcmuaf.st.chuyendeweb.repository.FacilityRepository;
import vn.edu.hcmuaf.st.chuyendeweb.repository.ImageLaptopRepository;
import vn.edu.hcmuaf.st.chuyendeweb.repository.LaptopRepository;
import vn.edu.hcmuaf.st.chuyendeweb.repository.FilterListRepository;
import vn.edu.hcmuaf.st.chuyendeweb.service.ILaptopService;

import java.awt.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class LaptopService implements ILaptopService {
    private final LaptopRepository laptopRepository;
    private final LaptopConverter laptopConverter;
    private final FacilityRepository facilityRepository;
    private final FilterListRepository filterListRepository;
    private final ImageLaptopRepository imageLaptopRepository;

//    @Override
//    public LaptopDTO addLaptop(LaptopDTO laptopDTO,MultipartFile linkAvatar, MultipartFile[] imageFiles) {
//        try {
//            Laptop laptop = laptopConverter.toLaptop(laptopDTO);
//            Optional<Facility> optionalFacility = facilityRepository.findById(laptopDTO.getFacilityId());
//            if (optionalFacility.isEmpty()) {
//                 throw new ServiceException(HttpStatus.INTERNAL_SERVER_ERROR, "Nhà kho không tồn tại hoặc đã xóa");
//            }
//
//            Cloudinary cloudinary = new Cloudinary(ObjectUtils.asMap(
//                    "cloud_name", "diyrrlmqk",
//                    "api_key", "137284888978213",
//                    "api_secret", "Rxu7XVXAxkeUXoEcwgt1s4dSpAs"));
//            Map params;
//            Map uploadResult;
//            String linkImage;
//
//            ImageLaptop imageLaptop = new ImageLaptop();
//            for (MultipartFile file : imageFiles) {
//                String fileName = file.getOriginalFilename().substring(0, file.getOriginalFilename().lastIndexOf("."));
//                params = ObjectUtils.asMap(
//                        "public_id", fileName
//                );
//                uploadResult = cloudinary.uploader().upload(file.getBytes(), params);
//
//                linkImage = (String) uploadResult.get("url");
//                imageLaptop.setImageName(fileName);
//                imageLaptop.setLinkImage(linkImage);
//                imageLaptop.setLaptop(laptop);
//                imageLaptopRepository.save(imageLaptop);
//            }
//
//            laptop.setFacility(optionalFacility.get());
//            laptop = laptopRepository.save(laptop);
//            return laptopConverter.toLaptopDTO(laptop);
//
//        } catch (IOException e) {
//            throw new ServiceException(HttpStatus.INTERNAL_SERVER_ERROR, "Lỗi khi thêm hình ảnh, vui lòng thử lại");
//        }
//    }

    @Override
    public LaptopDTO addLaptop(LaptopDTO laptopDTO, MultipartFile linkAvatar, MultipartFile[] files) {
        Laptop laptop = laptopConverter.toLaptop(laptopDTO);
        Optional<Facility> optionalFacility = facilityRepository.findById(laptopDTO.getFacilityId());
        if (optionalFacility.isEmpty()) {
            throw new ServiceException(HttpStatus.INTERNAL_SERVER_ERROR, "Nhà kho không tồn tại hoặc đã xóa");
        }

        laptop.setFacility(optionalFacility.get());
        laptop = laptopRepository.save(laptop);
        return laptopConverter.toLaptopDTO(laptop);
    }

    public LaptopDTO save(LaptopDTO laptopDTO) {
        Laptop laptop;
        if (laptopDTO.getId() != null) {
            Optional<Laptop> optionalLaptop = laptopRepository.findById(laptopDTO.getId());
            Laptop oldLaptop = optionalLaptop.get();
            laptop = laptopConverter.toLaptop(laptopDTO, oldLaptop);

        } else {
            laptop = laptopConverter.toLaptop(laptopDTO);
        }
        Optional<Facility> optionalFacility = facilityRepository.findById(laptopDTO.getFacilityId());
        if (!optionalFacility.isPresent()) {
            throw new RuntimeException("Nhà kho không tồn tại hoặc đã xóa");
        }
        laptop.setFacility(optionalFacility.get());
        laptop = laptopRepository.save(laptop);
        List<ImageLaptop> imageLaptops = new ArrayList<>();
        ImageLaptop imageLaptop = null;
        for (String image : laptopDTO.getListImages()) {
            imageLaptop = new ImageLaptop();
            imageLaptop.setLaptop(laptop);
            imageLaptop.setLinkImage(image);
            imageLaptops.add(imageLaptop);
        }

        return laptopConverter.toLaptopDTO(laptop);
    }

    @Override
    public Page<Laptop> getAllLaptop(LaptopFilter filter, int start, int limit) {
        Page<Laptop> laptopPage = filterListRepository.findTest(filter.getTypes(), filter.getBrands(), filter.getChipCpus(), start - 1, limit);
        return laptopPage;
    }

    @Override
    public List<String> getAllBrand() {
        List<String> brands = laptopRepository.findAllBrand();
        return brands;
    }

    @Override
    public List<LaptopDTO> findAll(Pageable pageable) {
        List<LaptopDTO> result = new ArrayList<>();
        List<Laptop> entities = laptopRepository.findAll(pageable).getContent();
        for (Laptop laptop : entities) {
            result.add(laptopConverter.toLaptopDTO(laptop));
        }
        return result;
    }

    @Override
    public int totalItem() {
        return (int) laptopRepository.count();
    }
}

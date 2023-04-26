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

    @Override
    public LaptopDTO addLaptop(LaptopDTO laptopDTO, MultipartFile linkAvatar, MultipartFile[] imageFiles) {
        try {
            // Lưu ảnh avatar laptop vào cloud
            String linkAva = getLink(linkAvatar);
            laptopDTO.setLinkAvatar(linkAva);
            Laptop laptop = laptopConverter.toLaptop(laptopDTO);
            Optional<Facility> optionalFacility = facilityRepository.findById(laptopDTO.getFacilityId());
            if (optionalFacility.isEmpty()) {
                throw new ServiceException(HttpStatus.INTERNAL_SERVER_ERROR, "Nhà kho không tồn tại hoặc đã xóa");
            }

            ImageLaptop imageLaptop = new ImageLaptop();
            for (MultipartFile file : imageFiles) {
                imageLaptop.setLinkImage(getLink(file));
                imageLaptop.setLaptop(laptop);
                laptop.getImages().add(imageLaptop);
                imageLaptopRepository.save(imageLaptop);
            }

            laptop.setFacility(optionalFacility.get());
            laptop = laptopRepository.save(laptop);
            return laptopConverter.toLaptopDTO(laptop);

        } catch (IOException e) {
            throw new ServiceException(HttpStatus.INTERNAL_SERVER_ERROR, "Lỗi khi thêm hình ảnh, vui lòng thử lại");
        }
    }

    @Override
    public LaptopDTO updateLaptop(LaptopDTO laptopDTO) {
        Long id = laptopDTO.getId();
        if (id != null) {
            Optional<Laptop> laptopOptional = laptopRepository.findById(id);
            if (laptopOptional.isPresent()) {
                Laptop oldLaptop = laptopOptional.get();
                Laptop laptop = laptopConverter.toLaptop(laptopDTO, oldLaptop);
                laptop = laptopRepository.save(laptop);
                return laptopConverter.toLaptopDTO(laptop);
            } else {
                throw new ServiceException(HttpStatus.INTERNAL_SERVER_ERROR, "Không tìm thấy laptop hoặc laptop đã bị xóa");
            }
        } else {
            throw new ServiceException(HttpStatus.INTERNAL_SERVER_ERROR, "Chưa có id laptop hoặc laptop đã bị xóa");
        }
    }

    @Override
    public void deleteLaptop(Long... ids) {
        if (ids != null && ids.length > 0) {
            for (Long id : ids) {
                Optional<Laptop> laptopOptional = laptopRepository.findById(id);
                if (laptopOptional.isPresent()) {
                    laptopRepository.delete(laptopOptional.get());
                }
            }
        } else {
            throw new ServiceException(HttpStatus.INTERNAL_SERVER_ERROR, "Chưa có id laptop");
        }
    }

//    @Override
//    public LaptopDTO addLaptop(LaptopDTO laptopDTO, MultipartFile linkAvatar, MultipartFile[] files) {
//        Laptop laptop = laptopConverter.toLaptop(laptopDTO);
//        Optional<Facility> optionalFacility = facilityRepository.findById(laptopDTO.getFacilityId());
//        if (optionalFacility.isEmpty()) {
//            throw new ServiceException(HttpStatus.INTERNAL_SERVER_ERROR, "Nhà kho không tồn tại hoặc đã xóa");
//        }
//
//        laptop.setFacility(optionalFacility.get());
//        laptop = laptopRepository.save(laptop);
//        return laptopConverter.toLaptopDTO(laptop);
//    }

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
    public List<String> getAllType() {
        return laptopRepository.findAllType();
    }

    @Override
    public List<String> getAllChipCpu() {
        return laptopRepository.findAllChipCpu();
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
    public LaptopDTO findLaptopById(Long id) {
        Optional<Laptop> laptopOptional = laptopRepository.findById(id);
        if (laptopOptional.isPresent()) {
            return laptopConverter.toLaptopDTO(laptopOptional.get());
        } else {
            throw new ServiceException(HttpStatus.INTERNAL_SERVER_ERROR, "Không tìm thấy laptop hoặc đã bị xóa");
        }
    }

    @Override
    public int totalItem() {
        return (int) laptopRepository.count();
    }

    @Override
    public List<String> getImageLinks(Long id) {
        Optional<Laptop> laptopOptional = laptopRepository.findById(id);
        if (laptopOptional.isPresent()) {
            List<String> links = new ArrayList<>();
            List<ImageLaptop> imageLaptops = laptopOptional.get().getImages();
            for (ImageLaptop il : imageLaptops) {
                links.add(il.getLinkImage());
            }
            return links;
        } else {
            throw new ServiceException(HttpStatus.INTERNAL_SERVER_ERROR, "Không tìm thấy laptop");
        }
    }

    private String getLink(MultipartFile file) throws IOException {
        Cloudinary cloudinary = new Cloudinary(ObjectUtils.asMap(
                "cloud_name", "diyrrlmqk",
                "api_key", "137284888978213",
                "api_secret", "Rxu7XVXAxkeUXoEcwgt1s4dSpAs"));

        String fileName = file.getOriginalFilename().substring(0, file.getOriginalFilename().lastIndexOf("."));
        Map params = ObjectUtils.asMap(
                "public_id", fileName
        );
        Map uploadResult = cloudinary.uploader().upload(file.getBytes(), params);
        String linkImage = (String) uploadResult.get("url");
        return linkImage;
    }
}

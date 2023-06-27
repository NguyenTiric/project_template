package com.example.template_project.service.impl;

import com.example.template_project.entity.ChucVu;
import com.example.template_project.entity.NhanVien;
import com.example.template_project.exception.DuplicateRecordException;
import com.example.template_project.exception.InternalServerException;
import com.example.template_project.exception.NotFoundException;
import com.example.template_project.model.mapper.NhanVienMapper;
import com.example.template_project.model.request.CreateNhanVienRequest;
import com.example.template_project.model.request.UpdateNhanVienRequest;
import com.example.template_project.model.response.NhanVienResponse;
import com.example.template_project.repository.ChucVuRepository;
import com.example.template_project.repository.NhanVienRepository;
import com.example.template_project.service.NhanVienService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class NhanVienServiceImpl implements NhanVienService {

    @Autowired
    NhanVienMapper nhanVienMapper;

    @Autowired
    NhanVienRepository nhanVienRepository;

    @Autowired
    ChucVuRepository chucVuRepository;

    @Override
    public List<NhanVienResponse> getAll() {
        return nhanVienMapper.listNhanVienEntityToNhanVienRequest(nhanVienRepository.findAll());
    }

    @Override
    public NhanVienResponse create(CreateNhanVienRequest createNhanVienRequest) {
        try {
            NhanVien nhanVien = nhanVienMapper.createNhanVienRequestToNhanVienEntity(createNhanVienRequest);
            return nhanVienMapper.nhanVienEntityToNhanVienResponse(nhanVienRepository.save(nhanVien));
        } catch (DataIntegrityViolationException ex) {
            throw new DuplicateRecordException("Failed to create product. Possibly duplicate record." + ex);
        } catch (Exception ex) {
            throw new InternalServerException("Failed to create product due to an unexpected error." + ex);
        }
    }

    @Override
    public NhanVienResponse update(UpdateNhanVienRequest updateNhanVienRequest, Integer id) {

        Optional<NhanVien> nhanVienById = nhanVienRepository.findById(id);

        if (!nhanVienById.isPresent()) {
            throw new NotFoundException("NhanVien not found with id " + id);
        }

        NhanVien nhanVien = nhanVienById.get();
        ChucVu chucVu = nhanVien.getChucVu();

        if (chucVu == null || chucVu.getId() == null) {
            throw new NotFoundException("ChucVu not found with id " + id);
        }

        Optional<ChucVu> chucVuById = chucVuRepository.findById(chucVu.getId());

        if (!chucVuById.isPresent()) {
            throw new NotFoundException("ChucVu not found with id " + chucVu.getId());
        }

        nhanVien = nhanVienMapper.updateNhanVienRequestToNhanVienEntity(updateNhanVienRequest);
        return nhanVienMapper.nhanVienEntityToNhanVienResponse(nhanVienRepository.save(nhanVien));
    }

    @Override
    public NhanVienResponse getOne(Integer id) {
        Optional<NhanVien> nhanVienById = nhanVienRepository.findById(id);
        if (!nhanVienById.isPresent()) {
            throw new NotFoundException("NhanVien not found with id " + nhanVienById);
        }
        return nhanVienMapper.nhanVienEntityToNhanVienResponse(nhanVienById.get());
    }

    @Override
    public void delete(Integer id) {
        nhanVienRepository.deleteById(id);
    }
}

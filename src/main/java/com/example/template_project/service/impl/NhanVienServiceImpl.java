package com.example.template_project.service.impl;

import com.example.template_project.entity.NhanVien;
import com.example.template_project.model.mapper.NhanVienMapper;
import com.example.template_project.model.request.CreateNhanVienRequest;
import com.example.template_project.model.request.UpdateNhanVienRequest;
import com.example.template_project.model.response.NhanVienResponse;
import com.example.template_project.repository.ChucVuRepository;
import com.example.template_project.repository.NhanVienRepository;
import com.example.template_project.service.NhanVienService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

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
    public Page<NhanVienResponse> pageNhanVien(Integer pageNo, Integer size) {
        Pageable pageable = PageRequest.of(pageNo, size);
        Page<NhanVien> nhanVienPage = nhanVienRepository.findAll(pageable);
        return nhanVienPage.map(nhanVienMapper::nhanVienEntityToNhanVienResponse);
    }

    @Override
    public NhanVienResponse create(CreateNhanVienRequest createNhanVienRequest) {
            NhanVien nhanVien = nhanVienMapper.createNhanVienRequestToNhanVienEntity(createNhanVienRequest);
            return nhanVienMapper.nhanVienEntityToNhanVienResponse(nhanVienRepository.save(nhanVien));
    }

    @Override
    public NhanVienResponse update(UpdateNhanVienRequest updateNhanVienRequest, Integer id) {

        Optional<NhanVien> nhanVienById = nhanVienRepository.findById(id);
        NhanVien nhanVien = nhanVienById.get();
        nhanVien = nhanVienMapper.updateNhanVienRequestToNhanVienEntity(updateNhanVienRequest);
        return nhanVienMapper.nhanVienEntityToNhanVienResponse(nhanVienRepository.save(nhanVien));
    }

    @Override
    public NhanVienResponse getOne(Integer id) {
        Optional<NhanVien> nhanVienById = nhanVienRepository.findById(id);
        return nhanVienMapper.nhanVienEntityToNhanVienResponse(nhanVienById.get());
    }

    @Override
    public void delete(Integer id) {
        nhanVienRepository.deleteById(id);
    }
}

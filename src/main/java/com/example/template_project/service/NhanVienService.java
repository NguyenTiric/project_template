package com.example.template_project.service;

import com.example.template_project.model.request.CreateNhanVienRequest;
import com.example.template_project.model.request.UpdateNhanVienRequest;
import com.example.template_project.model.response.NhanVienResponse;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public interface NhanVienService {

    List<NhanVienResponse> getAll();

    NhanVienResponse create(CreateNhanVienRequest createNhanVienRequest);

    NhanVienResponse update(UpdateNhanVienRequest updateNhanVienRequest,Integer id);

    NhanVienResponse getOne(Integer id);

    void delete(Integer id);

}

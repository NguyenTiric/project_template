package com.example.template_project.controller;

import com.example.template_project.model.response.NhanVienResponse;
import com.example.template_project.service.ChucVuService;
import com.example.template_project.service.NhanVienService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.util.List;


@Controller
public class NhanVienController {
    //demo
    @Autowired
    ChucVuService chucVuService;
    @Autowired
    NhanVienService nhanVienService;

    // list nhan vien
    List<NhanVienResponse> nhanVienResponseList;
}

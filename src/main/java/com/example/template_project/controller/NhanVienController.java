package com.example.template_project.controller;

import com.example.template_project.model.request.CreateNhanVienRequest;
import com.example.template_project.model.request.UpdateNhanVienRequest;
import com.example.template_project.model.response.ChucVuResponse;
import com.example.template_project.model.response.NhanVienResponse;
import com.example.template_project.service.ChucVuService;
import com.example.template_project.service.NhanVienService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;


@Controller
@RequestMapping("/nhan-vien")
public class NhanVienController {
    //demo
    @Autowired
    ChucVuService chucVuService;
    @Autowired
    NhanVienService nhanVienService;
    List<ChucVuResponse> chucVuResponseList;

    @GetMapping("/hien-thi")
    public String getAll(Model model) {
        model.addAttribute("nhanVien", new NhanVienResponse());
        return pageNhanVien(model, 0);
    }

    @GetMapping("/page/{pageNo}")
    public String pageNhanVien(Model model, @PathVariable int pageNo) {
        chucVuResponseList = chucVuService.getAll();
        model.addAttribute("listChucVuResponse", chucVuResponseList);
        model.addAttribute("nhanVien", new NhanVienResponse());
        Page<NhanVienResponse> nhanVienResponsePage = nhanVienService.pageNhanVien(pageNo, 3);
        model.addAttribute("size", nhanVienResponsePage.getSize());
        model.addAttribute("totalPages", nhanVienResponsePage.getTotalPages());
        model.addAttribute("currentPage", pageNo);
        model.addAttribute("listNhanVien", nhanVienResponsePage);
        return "trang_chu";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable("id") int id) {
        nhanVienService.delete(id);
        return "redirect:/nhan-vien/hien-thi";
    }

    @GetMapping("/view-update/{id}")
    public String viewUpdate(@PathVariable("id") int id, Model model) {
        chucVuResponseList = chucVuService.getAll();
        NhanVienResponse nhanVienResponse = nhanVienService.getOne(id);
        model.addAttribute("listChucVuResponse", chucVuResponseList);
        model.addAttribute("nhanVien", nhanVienResponse);
        return "view_update";
    }

    @PostMapping("/add")
    public String add(@Valid @ModelAttribute("nhanVien") CreateNhanVienRequest createNhanVienRequest, BindingResult result, Model model) {
        if (result.hasErrors()) {
            chucVuResponseList = chucVuService.getAll();
            model.addAttribute("listChucVuResponse", chucVuResponseList);
            model.addAttribute("nhanVien", createNhanVienRequest);
            Page<NhanVienResponse> nhanVienResponsePage = nhanVienService.pageNhanVien(0, 3);
            model.addAttribute("size", nhanVienResponsePage.getSize());
            model.addAttribute("totalPages", nhanVienResponsePage.getTotalPages());
            model.addAttribute("currentPage", 0 );
            model.addAttribute("listNhanVien", nhanVienResponsePage);
            return "trang_chu";
        }
        model.addAttribute("nhanVien", createNhanVienRequest);
        nhanVienService.create(createNhanVienRequest);
        return "redirect:/nhan-vien/hien-thi";
    }

    @PostMapping("/update")
    public String update(@Valid @ModelAttribute("nhanVien") UpdateNhanVienRequest updateNhanVienRequest, BindingResult result, Model model) {
        if (result.hasErrors()) {
            chucVuResponseList = chucVuService.getAll();
            model.addAttribute("listChucVuResponse", chucVuResponseList);
            model.addAttribute("nhanVien", updateNhanVienRequest);
            return "view_update";
        }
        model.addAttribute("nhanVien", updateNhanVienRequest);
        nhanVienService.update(updateNhanVienRequest, updateNhanVienRequest.getId());
        return "redirect:/nhan-vien/hien-thi";
    }
}

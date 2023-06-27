package com.example.template_project.rest_controller;

import com.example.template_project.model.request.CreateNhanVienRequest;
import com.example.template_project.model.request.UpdateNhanVienRequest;
import com.example.template_project.model.response.NhanVienResponse;
import com.example.template_project.service.NhanVienService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/nhan-vien")
@RestController
@CrossOrigin(origins = "*",maxAge = -1)
public class NhanVienRestController {

    @Autowired
    NhanVienService nhanVienService;

    @GetMapping()
    public ResponseEntity<?> getAllNhanVien() {
        List<NhanVienResponse> list = nhanVienService.getAll();
        return ResponseEntity.status(HttpStatus.OK).body(list);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getNhanVienById(@PathVariable Integer id) {
        return ResponseEntity.ok(nhanVienService.getOne(id));
    }

    @PostMapping("/create")
    public ResponseEntity<?> createNhanVien(@Valid @RequestBody CreateNhanVienRequest createNhanVienRequest) {
        return ResponseEntity.ok(nhanVienService.create(createNhanVienRequest));
    }
     // update
    @PutMapping("/update/{id}")
    public ResponseEntity<NhanVienResponse> updateNhanVien(@Valid @RequestBody UpdateNhanVienRequest updateNhanVienRequest, @PathVariable("id") Integer id) {
        return ResponseEntity.ok(nhanVienService.update(updateNhanVienRequest, id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteNhanVien(@PathVariable Integer id) {
        nhanVienService.delete(id);
        return ResponseEntity.ok("Delete Success");
    }

}

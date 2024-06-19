package fpoly.asm.controller;

import fpoly.asm.entity.DanhMuc;
import fpoly.asm.entity.KhachHang;
import fpoly.asm.service.KhachHangService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/khach-hang")
public class KhachHangController {
    @Autowired
    private KhachHangService khachHangService;

    @GetMapping("/index")
    public String showDanhMuc(Model model) {
        model.addAttribute("KhachHangs", khachHangService.findAll());
        return "admin/Khach-Hang/index";
    }

    @GetMapping("/add")
    public String add(Model model) {
        model.addAttribute("KhachHang", new KhachHang());
        return "admin/Khach-Hang/FormKhachHang";
    }

    @PostMapping("/save")
    public String save(@Valid @ModelAttribute("KhachHang") KhachHang khachHang, BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "admin/Khach-Hang/FormKhachHang";
        }
        khachHangService.save(khachHang);
        return "redirect:/khach-hang/index";
    }

    @PostMapping("/sua")
    public String sua(@ModelAttribute("id") int id, Model model) {
        Optional<KhachHang> khachHang = khachHangService.findById(id);
        model.addAttribute("KhachHang", khachHang);
        return "admin/Khach-Hang/FormKhachHang";
    }

    @PostMapping("/xoa")
    public String xoa(@ModelAttribute("id") int id, Model model) {
        khachHangService.findById(id);
        return "redirect:/khach-hang/index";
    }
}

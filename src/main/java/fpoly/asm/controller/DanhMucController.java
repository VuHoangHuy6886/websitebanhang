package fpoly.asm.controller;

import fpoly.asm.entity.DanhMuc;
import fpoly.asm.entity.SanPham;
import fpoly.asm.service.DanhMucService;
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
@RequestMapping("danh-muc")
public class DanhMucController {
    @Autowired
    private DanhMucService danhMucService;
    private List<String> trangThai = new ArrayList<>();

    // Khối khởi tạo
    {
        trangThai.add("Hoạt Động");
        trangThai.add("Không Hoạt Động");
    }

    public List<String> list() {
        return trangThai;
    }

    @GetMapping("/index")
    public String showDanhMuc(Model model) {
        model.addAttribute("DanhMucs", danhMucService.findAll());
        return "admin/Danh-Muc/index";
    }

    @GetMapping("/add")
    public String add(Model model) {
        List<String> trangThai = new ArrayList<>();
        trangThai.add("Hoạt Động");
        trangThai.add("Không Hoạt Động");
        model.addAttribute("DanhMuc", new DanhMuc());
        model.addAttribute("TrangThais", trangThai);
        return "admin/Danh-Muc/FormDanhMuc";
    }

    @PostMapping("/save")
    public String save(@Valid @ModelAttribute("DanhMuc") DanhMuc danhMuc, BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("TrangThais", trangThai);
            return "admin/Danh-Muc/FormDanhMuc";
        }
        danhMucService.save(danhMuc);
        return "redirect:/danh-muc/index";
    }

    @PostMapping("/sua")
    public String sua(@ModelAttribute("id") int id, Model model) {
        Optional<DanhMuc> danhMucOptional = danhMucService.findById(id);
        if (danhMucOptional.isPresent()) {
            DanhMuc danhMuc = danhMucOptional.get();
            model.addAttribute("DanhMuc", danhMuc);
            model.addAttribute("TrangThais", trangThai);
        }
        return "admin/Danh-Muc/FormDanhMuc";
    }

    @PostMapping("/xoa")
    public String xoa(@ModelAttribute("id") int id, Model model) {
        Optional<DanhMuc> danhMucOptional = danhMucService.findById(id);
        if (danhMucOptional.isPresent()) {
            DanhMuc danhMuc = danhMucOptional.get();
            String tt = trangThai.get(1);
            danhMuc.setTrangThai(tt);
            danhMucService.save(danhMuc);
        }
        return "redirect:/danh-muc/index";
    }
}

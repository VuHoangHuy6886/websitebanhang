package fpoly.asm.controller;

import fpoly.asm.entity.DanhMuc;
import fpoly.asm.entity.SanPham;
import fpoly.asm.service.DanhMucService;
import fpoly.asm.service.SanPhamSerice;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Controller
public class SanPhamConTroller {
    private List<String> trangThai = new ArrayList<>();

    // Khối khởi tạo
    {
        trangThai.add("Hoạt Động");
        trangThai.add("Không Hoạt Động");
    }

    public List<String> list() {
        return trangThai;
    }

    @Autowired
    private SanPhamSerice sanPhamService;
    @Autowired
    private DanhMucService danhMucService;

    @GetMapping("/san-pham")
    public String trangChu(@RequestParam(defaultValue = "0") int page,
                           @RequestParam(defaultValue = "5") int size,
                           Model model) {
        Page<SanPham> products = sanPhamService.findAll(PageRequest.of(page, size));
        model.addAttribute("products", products);
        return "admin/San-Pham/index";
    }

    @GetMapping("/add")
    public String add(Model model) {
        List<DanhMuc> danhMucs = danhMucService.findAll();
        model.addAttribute("SanPham", new SanPham());
        model.addAttribute("DanhMucs", danhMucs);
        List<String> trangThai = new ArrayList<>();
        trangThai.add("Hoạt Động");
        trangThai.add("Không Hoạt Động");
        model.addAttribute("TrangThais", trangThai);
        return "admin/San-Pham/ThemMoi";
    }

    @PostMapping("/save")
    public String save(@Valid @ModelAttribute("SanPham") SanPham sanPham, BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("TrangThais", trangThai);
            List<DanhMuc> danhMucs = danhMucService.findAll();
            model.addAttribute("DanhMucs", danhMucs);
            return "admin/San-Pham/ThemMoi";
        }
        sanPhamService.save(sanPham);
        return "redirect:/san-pham";
    }

    @PostMapping("/sua")
    public String sua(@ModelAttribute("id") int id, Model model) {
        Optional<SanPham> sanPhamOptional = sanPhamService.findById(id);
        if (sanPhamOptional.isPresent()) {
            SanPham sanPham = sanPhamOptional.get();
            List<DanhMuc> danhMucs = danhMucService.findAll();
            model.addAttribute("SanPham", sanPham);
            model.addAttribute("DanhMucs", danhMucs);
            model.addAttribute("TrangThais", trangThai);
        }
        return "admin/San-Pham/ThemMoi";
    }

    @PostMapping("/xoa")
    public String xoa(@ModelAttribute("id") int id, Model model) {
        Optional<SanPham> sanPhamOptional = sanPhamService.findById(id);
        if (sanPhamOptional.isPresent()) {
            SanPham sanPham = sanPhamOptional.get();
            String tt = trangThai.get(1);
            sanPham.setTrangThai(tt);
            sanPhamService.save(sanPham);
        }
        return "redirect:/san-pham";
    }

}
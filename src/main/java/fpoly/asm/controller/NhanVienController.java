package fpoly.asm.controller;

import fpoly.asm.entity.NhanVien;
import fpoly.asm.entity.VaiTro;
import fpoly.asm.service.NhanVienService;
import fpoly.asm.service.VaiTroService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/nhan-vien")
public class NhanVienController {
    @Autowired
    private NhanVienService nhanVienService;
    @Autowired
    VaiTroService vaiTroService;

    @GetMapping("/index")
    public String showDanhMuc(Model model) {
        model.addAttribute("NhanViens", nhanVienService.findAll());
        return "admin/Nhan-Vien/index";
    }

    @GetMapping("/add")
    public String add(Model model) {
        model.addAttribute("NhanVien", new NhanVien());
        model.addAttribute("VaiTros", vaiTroService.findAll());
        return "admin/Nhan-Vien/formEmployee";
    }

    @PostMapping("/save")
    public String save(@ModelAttribute("NhanVien") @Valid NhanVien nhanVien, BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("NhanVien", nhanVien);
            model.addAttribute("VaiTros", vaiTroService.findAll());
            System.out.println("lỗi");
            return "admin/Nhan-Vien/formEmployee";
        }
        List<VaiTro> vaiTros = vaiTroService.findAllById(nhanVien.getVaiTros().stream().map(VaiTro::getId).collect(Collectors.toList()));
        nhanVien.setVaiTros(vaiTros);
        nhanVienService.save(nhanVien);
        return "redirect:/nhan-vien/index";
    }


    @PostMapping("/sua")
    public String sua(@ModelAttribute("id") int id, Model model) {
        NhanVien nv = nhanVienService.findById(id);
        model.addAttribute("NhanVien", nv);
        model.addAttribute("VaiTros", vaiTroService.findAll());
        return "admin/Nhan-Vien/formEmployee";
    }

//    @PostMapping("/xoa")
//    public String xoa(@ModelAttribute("id") int id, Model model) {
//        khachHangService.findById(id);
//        return "redirect:/khach-hang/index";
//    }
}

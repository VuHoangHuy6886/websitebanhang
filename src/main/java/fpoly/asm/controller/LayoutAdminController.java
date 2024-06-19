package fpoly.asm.controller;

import fpoly.asm.service.ServiceProject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LayoutAdminController {
    @Autowired
    ServiceProject serviceProject;

    @GetMapping("/admin")
    public String layoutAdmin(Model model) {
        model.addAttribute("NhanVien", serviceProject.getNhanVien());
        return "admin/index";
    }
}

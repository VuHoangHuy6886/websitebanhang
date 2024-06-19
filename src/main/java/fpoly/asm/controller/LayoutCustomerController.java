package fpoly.asm.controller;

import fpoly.asm.entity.SanPham;
import fpoly.asm.repository.SanPhamRepo;
import fpoly.asm.service.DanhMucService;
import fpoly.asm.service.GioHangLocalService;
import fpoly.asm.service.SanPhamSerice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class LayoutCustomerController {
    @Autowired
    private SanPhamSerice sanPhamService;
    @Autowired
    private GioHangLocalService gioHangService;
    @Autowired
    private SanPhamRepo sanPhamRepo;
    @Autowired
    private DanhMucService danhMucService;

    @GetMapping("/trang-chu")
    public String trangChu(@RequestParam(name = "page", defaultValue = "0") int page,
                           @RequestParam(name = "size", defaultValue = "10") int size,
                           @RequestParam(name = "idDanhMuc", required = false) Integer idDanhMuc,
                           Model model) {
        Page<SanPham> products;
        if (idDanhMuc != null) {
            Pageable pageable = PageRequest.of(page, size);
            products = sanPhamRepo.findSanPhamByDanhMuc(idDanhMuc, pageable);
        } else {
            products = sanPhamService.findAll(PageRequest.of(page, size));
        }
        model.addAttribute("DanhMucs", danhMucService.findAll());
        model.addAttribute("products", products);
        model.addAttribute("soLuongSanPham", gioHangService.getSoLuong());
        return "trangchu/index";
    }

}

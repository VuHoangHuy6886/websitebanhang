package fpoly.asm.controller;

import fpoly.asm.entity.KhachHang;
import fpoly.asm.entity.SanPham;
import fpoly.asm.repository.SanPhamRepo;
import fpoly.asm.service.GioHangLocalService;
import fpoly.asm.service.SanPhamSerice;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Collection;


@Controller
public class GioHangLocalController {
    @Autowired
    private GioHangLocalService gioHangService;
    @Autowired
    private SanPhamSerice sanPhamSerice;

    @PostMapping("/them-vao-gio-hang")
    public String addToCart(@ModelAttribute("id") int id, Model model) {
        this.gioHangService.add(id);
        System.out.println("id sản phẩm : " + id);
        return "redirect:/trang-chu";
    }

    @PostMapping("/tang")
    public String updateToCart(@ModelAttribute("id") int id, @ModelAttribute("soLuong") int sl, Model model) {
        System.out.println("id sp : " + id + " - So Lượng sản phẩm này : " + sl);
        // get san phâm trong kho len so sanh
        SanPham spInKho = sanPhamSerice.findById(id).get();
        int tangSl = 1;
        if (spInKho.getSoLuong() < sl + tangSl) {
            model.addAttribute("showThongBao", "Số Lượng Vượt Quá Giới Hạn");
            Collection<SanPham> sanPhams = gioHangService.getAllSanPham();
            Double tongTien = gioHangService.getTongTien();
            model.addAttribute("sanPhams", sanPhams);
            model.addAttribute("SumMoney", tongTien);
            model.addAttribute("soLuongSanPham", gioHangService.getSoLuong());
            return "trangchu/cart";
        } else {
            this.gioHangService.update(id, sl + 1);
        }

        return "redirect:/cart";
    }

    @PostMapping("/giam")
    public String updateToCartTwo(@ModelAttribute("id") int id, @ModelAttribute("soLuong") int sl, Model model) {
        System.out.println("id sp : " + id + " - So Lượng sản phẩm này : " + sl);

        if (sl <= 1) {
            model.addAttribute("showThongBao", "Số Lượng Không Được nhỏ hơn 1");
            Collection<SanPham> sanPhams = gioHangService.getAllSanPham();
            Double tongTien = gioHangService.getTongTien();
            model.addAttribute("sanPhams", sanPhams);
            model.addAttribute("SumMoney", tongTien);
            model.addAttribute("soLuongSanPham", gioHangService.getSoLuong());
            return "trangchu/cart";
        } else {
            this.gioHangService.update(id, sl - 1);
        }
        return "redirect:/cart";
    }

    @GetMapping("/clearAll")
    public String clearAll() {
        gioHangService.clear();
        return "redirect:/cart";
    }

    @PostMapping("/clear")
    public String clearById(@ModelAttribute("id") int id) {
        gioHangService.remove(id);
        return "redirect:/cart";
    }

    @GetMapping("/cart")
    public String cart(Model model) {
        Collection<SanPham> sanPhams = gioHangService.getAllSanPham();
        Double tongTien = gioHangService.getTongTien();
        model.addAttribute("sanPhams", sanPhams);
        model.addAttribute("SumMoney", tongTien);
        model.addAttribute("soLuongSanPham", gioHangService.getSoLuong());
        return "trangchu/cart";
    }

    @GetMapping("/trang-dat-hang")
    public String thanhToan(Model model) {
        Collection<SanPham> sanPhams = gioHangService.getAllSanPham();
        Double tongTien = gioHangService.getTongTien();
        model.addAttribute("sanPhams", sanPhams);
        model.addAttribute("SumMoney", tongTien);
        model.addAttribute("soLuongSanPham", gioHangService.getSoLuong());
        model.addAttribute("KhachHang", new KhachHang());
        return "trangchu/Trang-Dat-Hang";
    }

    @PostMapping("/luu-gio-hang")
    public String saveGioHangLocal(@Valid @ModelAttribute("KhachHang") KhachHang khachHang, BindingResult result, Model model) {
        // lưu thông tin để in lại nêu Loi
        Collection<SanPham> sanPhams = gioHangService.getAllSanPham();
        Double tongTien = gioHangService.getTongTien();
        if (result.hasErrors()) {
            model.addAttribute("sanPhams", sanPhams);
            model.addAttribute("SumMoney", tongTien);
            model.addAttribute("soLuongSanPham", gioHangService.getSoLuong());
            System.out.println("Có lỗi xảy ra:");
            // cách xem lỗi ở đâu
            result.getAllErrors().forEach(error -> System.out.println(error.getDefaultMessage()));
            model.addAttribute("KhachHang", khachHang);
            return "trangchu/Trang-Dat-Hang";
        }
        if (sanPhams != null && tongTien > 0) {
            System.out.println("Hết Lỗi đã vào hàm update giỏ hàng");
            gioHangService.themGioHangVaoHoaDon(khachHang);
            return "redirect:/trang-chu";
        } else {
            model.addAttribute("sanPhams", sanPhams);
            model.addAttribute("SumMoney", tongTien);
            model.addAttribute("soLuongSanPham", gioHangService.getSoLuong());
            model.addAttribute("KhachHang", khachHang);
            model.addAttribute("showThongTin", "Giỏ Hàng Rỗng Không Đặt Hàng Được");
            return "trangchu/Trang-Dat-Hang";
        }
    }
}

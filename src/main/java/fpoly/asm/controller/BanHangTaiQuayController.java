package fpoly.asm.controller;

import fpoly.asm.dto.request.HDCTRequest;
import fpoly.asm.entity.HoaDon;
import fpoly.asm.entity.HoaDonChiTiet;
import fpoly.asm.entity.SanPham;
import fpoly.asm.repository.SanPhamRepo;
import fpoly.asm.service.BanHangService;
import fpoly.asm.service.ServiceProject;
import fpoly.asm.service.impl.BanHangServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.awt.print.Pageable;
import java.util.List;

@Controller
@RequestMapping("/ban-hang")
public class BanHangTaiQuayController {
    @Autowired
    private BanHangServiceImpl banHangService;
    @Autowired
    private ServiceProject serviceProject;
    @Autowired
    private SanPhamRepo sanPhamRepo;

    @GetMapping("/index")
    public String pageBanHang(Model model) {
        List<HoaDon> listHoaDon = banHangService.findHoaDon();
        String message = serviceProject.getError();
        if (serviceProject.getIdSP() != null) {
            model.addAttribute("showError", message);
            model.addAttribute("Idsp", serviceProject.getIdSP());
        }
        if (banHangService.getIdHoaDon() != null) {
            List<HDCTRequest> listHDCT = banHangService.getHoaDonChiTietByIdHoaDon(banHangService.getIdHoaDon());
            model.addAttribute("listHDCT", listHDCT);
            if (banHangService.getTongTien() != null) {
                model.addAttribute("tongTien", banHangService.getTongTien());
            }
            if (banHangService.getSoLuongSanPham() != null) {
                model.addAttribute("SumQuantity", banHangService.getSoLuongSanPham());
            }
        }
        System.out.println("hinhThucThanhToan : " + serviceProject.getHinhThucThanhToan().getChuyenKhoan());
        if (banHangService.getIdHoaDon() != null) {
            model.addAttribute("idmyHD", banHangService.getIdHoaDon());
        } else {
            model.addAttribute("idmyHD", null);
        }

        model.addAttribute("listHoaDon", listHoaDon);
        return "admin/Ban-Hang/index";
    }

    @PostMapping("/tao-hoa-don")
    public String taoHoaDon(Model model) {
        HoaDon hoaDon = new HoaDon();
        banHangService.saveHoaDon(hoaDon);
        return "redirect:/ban-hang/index";
    }

    @GetMapping("/hien-hdct-theo-idHD")
    public String hienThiHDCTTheoIdHoaDon(@RequestParam("idHoaDon") Integer idHoaDon, Model model) {
        //cách đối tượng hiển thị lại :
        List<HoaDon> listHoaDon = banHangService.findHoaDon();
        model.addAttribute("listHoaDon", listHoaDon);
        // xử lý lọc hóa đơn chi tiết theo id hóa đơn
        List<HDCTRequest> listHDCT;
        if (idHoaDon != null) {
            listHDCT = banHangService.getHoaDonChiTietByIdHoaDon(idHoaDon);
            model.addAttribute("listHDCT", listHDCT);
            if (banHangService.getTongTien() != null) {
                model.addAttribute("tongTien", banHangService.getTongTien());
            }
            if (banHangService.getSoLuongSanPham() != null) {
                model.addAttribute("SumQuantity", banHangService.getSoLuongSanPham());
            }
        } else {
            listHDCT = banHangService.getHoaDonChiTietByIdHoaDon(banHangService.getIdHoaDon());
            model.addAttribute("listHDCT", listHDCT);
        }

        model.addAttribute("idmyHD", banHangService.getIdHoaDon());

        return "admin/Ban-Hang/index";
    }


    @PostMapping("/them-san-pham")
    public String showPageSanPham(
            @RequestParam(name = "page", defaultValue = "0") int page,
            @RequestParam(name = "size", defaultValue = "5") int size
            , Model model) {
        PageRequest pageRequest = PageRequest.of(page, size);
        model.addAttribute("listSanPham", banHangService.findAllSanPham(pageRequest));
        return "admin/Ban-Hang/danh-sach-san-pham";
    }

    @GetMapping("/add-hdct")
    public String addHoaDonChiTiet(@RequestParam("id") int idSP, Model model) {
        System.out.println("id sản phẩm là : " + idSP);
        banHangService.addSanPhamInHoaDonCho(idSP);
        return "redirect:/ban-hang/index";
    }

    @GetMapping("/tang")
    public String tangSoLuong(@RequestParam("id") Integer idSP, Model model) {
        System.out.println("id : " + idSP);
        List<HDCTRequest> hdct = banHangService.getHoaDonChiTietByIdHoaDon(banHangService.getIdHoaDon());
        Integer soLuongUpdateKhiTrung = 1;
        for (int i = 0; i < hdct.size(); i++) {
            if (hdct.get(i).getId().equals(idSP)) {
                HDCTRequest sanPhamGet = hdct.get(i);
                // lay so luong sarn pham trong database neu con nhieu hon thi moi update
                SanPham sanPham = sanPhamRepo.findById(idSP).orElseThrow();
                HDCTRequest spUpdate = new HDCTRequest(sanPhamGet.getId(), sanPhamGet.getTen(), sanPhamGet.getGia(),
                        sanPhamGet.getSoLuong() + soLuongUpdateKhiTrung);
                // sanPhamUpdate.setSoLuong(soLuongUpdateKhiTrung + sanPhamUpdate.getSoLuong());
                if (spUpdate.getSoLuong() > sanPham.getSoLuong()) {
                    serviceProject.showErrors(idSP, "vượt quá số lượng sản phẩm trong kho");
                    return "redirect:/ban-hang/index";
                }
                hdct.set(i, spUpdate);
            }
        }
        return "redirect:/ban-hang/index";
    }

    @GetMapping("/giam")
    public String giamSoLuong(@RequestParam("id") Integer idSP, Model model) {
        List<HDCTRequest> hdct = banHangService.getHoaDonChiTietByIdHoaDon(banHangService.getIdHoaDon());
        Integer soLuongUpdateKhiTrung = 1;
        for (int i = 0; i < hdct.size(); i++) {
            if (hdct.get(i).getId().equals(idSP)) {
                // lay so luong sarn pham trong database neu con nhieu hon thi moi update
                SanPham sanPham = sanPhamRepo.findById(idSP).orElseThrow();
                HDCTRequest sanPhamGet = hdct.get(i);
                HDCTRequest spUpdate = new HDCTRequest(sanPhamGet.getId(), sanPhamGet.getTen(), sanPhamGet.getGia(), sanPhamGet.getSoLuong() - soLuongUpdateKhiTrung);
                // sanPhamUpdate.setSoLuong(soLuongUpdateKhiTrung + sanPhamUpdate.getSoLuong());
                if (spUpdate.getSoLuong() < 1) {
                    serviceProject.showErrors(idSP, "Số Lượng sản phẩm không hợp lệ ");
                    return "redirect:/ban-hang/index";
                }
                hdct.set(i, spUpdate);
            }
        }
        return "redirect:/ban-hang/index";
    }

    @GetMapping("/xoa")
    public String xoa(@RequestParam("id") Integer idSP, Model model) {
        List<HDCTRequest> hdct = banHangService.getHoaDonChiTietByIdHoaDon(banHangService.getIdHoaDon());
        for (int i = 0; i < hdct.size(); i++) {
            if (hdct.get(i).getId().equals(idSP)) {
//                HDCTRequest sanPhamGet = hdct.get(i);
//                HDCTRequest spUpdate = new HDCTRequest(sanPhamGet.getId(), sanPhamGet.getTen(), sanPhamGet.getGia(), sanPhamGet.getSoLuong() - soLuongUpdateKhiTrung);
//                // sanPhamUpdate.setSoLuong(soLuongUpdateKhiTrung + sanPhamUpdate.getSoLuong());
//                hdct.set(i, spUpdate);
                hdct.remove(i);
            }
        }
        return "redirect:/ban-hang/index";
    }

    // phần thanh toán
    @PostMapping("/thanh-toan")
    public String thanhToan(
            @ModelAttribute("httt") String hinhThucThanhToan, Model model) {
        banHangService.thanhToan(hinhThucThanhToan);
        System.out.println("hinh thuc thanh toán là : " + hinhThucThanhToan);
        return "redirect:/ban-hang/index";
    }

    @PostMapping("/cleant-message")
    public String cleantMessage() {
        serviceProject.showErrors(null, null);
        return "redirect:/ban-hang/index";
    }
}

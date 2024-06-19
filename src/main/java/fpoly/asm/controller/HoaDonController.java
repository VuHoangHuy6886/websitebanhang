package fpoly.asm.controller;

import fpoly.asm.dto.SanPhamDTO;
import fpoly.asm.entity.HoaDon;
import fpoly.asm.entity.HoaDonChiTiet;
import fpoly.asm.entity.LichSuHoaDon;
import fpoly.asm.entity.NhanVien;
import fpoly.asm.repository.LichSuHoaDonRepo;
import fpoly.asm.service.HoaDonService;
import fpoly.asm.service.ServiceProject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/hoa-don")
public class HoaDonController {
    @Autowired
    private HoaDonService hoaDonService;
    @Autowired
    private ServiceProject serviceProject;
    @Autowired
    private LichSuHoaDonRepo lichSuHoaDonRepo;

    @GetMapping("/index")
    public String showTable(
            @RequestParam(name = "page", defaultValue = "0") int page,
            @RequestParam(name = "size", defaultValue = "8") int size
            , Model model) {

        Page<HoaDon> hoaDons = hoaDonService.findHoaDonAndSortDay(page, size);
        model.addAttribute("HoaDons", hoaDons);
        model.addAttribute("NhanVien", serviceProject.getNhanVien());
        return "admin/Hoa-Don/index";
    }

    @PostMapping("/detail")
    public String detailHoaDon(@ModelAttribute("id") int id, Model model) {
        List<SanPhamDTO> sanPhamDTOList = hoaDonService.findSanPhamByHoaDonId(id);
        Optional<HoaDon> hoaDonOptional = hoaDonService.findById(id);
        HoaDon hoaDon = new HoaDon();
        if (hoaDonOptional.isPresent()) {
            hoaDon = hoaDonOptional.get();
        }
        for (SanPhamDTO sp : sanPhamDTOList) {
            System.out.println("sản phẩm trong hóa đơn " + sp.toString());
        }
        model.addAttribute("hoadonchitiets", sanPhamDTOList);
        model.addAttribute("HoaDon", hoaDon);
        List<LichSuHoaDon> lichSuHoaDonList = lichSuHoaDonRepo.findLichSuHoaDonByIdHoaDon(id);
        model.addAttribute("BillHistory", lichSuHoaDonList);

        return "admin/Hoa-Don/Detail";
    }

    @PostMapping("/xac-nhan")
    public String xacNhan(@ModelAttribute("id") int id, Model model) {
        Optional<HoaDon> hoaDonOptional = hoaDonService.findById(id);
        if (hoaDonOptional.isPresent()) {
            HoaDon hoaDon = hoaDonOptional.get();
            hoaDon.setTrangThai(serviceProject.getTrangThaiHoaDon().getDaXacNhan());
            hoaDonService.save(hoaDon);
            LichSuHoaDon lichSuHoaDon = new LichSuHoaDon();
            lichSuHoaDon.setHoaDon(hoaDon);
            lichSuHoaDon.setNgayTao(serviceProject.getTimeNow());
            lichSuHoaDon.setTrangThai(serviceProject.getTrangThaiHoaDon().getDaXacNhan());
            lichSuHoaDon.setNhanVien(serviceProject.getNhanVien());
            lichSuHoaDon.setMoTa("Hóa Đơn Đã Xác Nhận Chờ Vận Chuyển");
            lichSuHoaDonRepo.save(lichSuHoaDon);
        }
        return "redirect:/hoa-don/index";
    }

    @PostMapping("/hoan-thanh")
    public String hoanThanh(@ModelAttribute("id") int id, Model model) {
        Optional<HoaDon> hoaDonOptional = hoaDonService.findById(id);
        if (hoaDonOptional.isPresent()) {
            HoaDon hoaDon = hoaDonOptional.get();
            hoaDon.setTrangThai(serviceProject.getTrangThaiHoaDon().getDanHoanThanh());
            hoaDonService.save(hoaDon);
            LichSuHoaDon lichSuHoaDon = new LichSuHoaDon();
            lichSuHoaDon.setHoaDon(hoaDon);
            lichSuHoaDon.setNgayTao(serviceProject.getTimeNow());
            lichSuHoaDon.setTrangThai(serviceProject.getTrangThaiHoaDon().getDanHoanThanh());
            lichSuHoaDon.setNhanVien(serviceProject.getNhanVien());
            lichSuHoaDon.setMoTa("Hóa Đơn Đã Hoàn Thành");
            lichSuHoaDonRepo.save(lichSuHoaDon);
        }
        return "redirect:/hoa-don/index";
    }

    @PostMapping("/huy")
    public String huy(@ModelAttribute("id") int id, Model model) {
        Optional<HoaDon> hoaDonOptional = hoaDonService.findById(id);
        if (hoaDonOptional.isPresent()) {
            HoaDon hoaDon = hoaDonOptional.get();
            hoaDon.setTrangThai(serviceProject.getTrangThaiHoaDon().getHuy());
            hoaDon.setNhanVien(serviceProject.getNhanVien());
            hoaDonService.save(hoaDon);
            LichSuHoaDon lichSuHoaDon = new LichSuHoaDon();
            lichSuHoaDon.setHoaDon(hoaDon);
            lichSuHoaDon.setNgayTao(serviceProject.getTimeNow());
            lichSuHoaDon.setTrangThai(serviceProject.getTrangThaiHoaDon().getHuy());
            lichSuHoaDon.setNhanVien(serviceProject.getNhanVien());
            lichSuHoaDon.setMoTa("Hóa Đơn Đã Hủy");
            lichSuHoaDonRepo.save(lichSuHoaDon);
        }
        return "redirect:/hoa-don/index";
    }
}

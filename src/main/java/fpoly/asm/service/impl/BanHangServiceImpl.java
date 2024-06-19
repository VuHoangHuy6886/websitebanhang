package fpoly.asm.service.impl;

import fpoly.asm.dto.request.HDCTRequest;
import fpoly.asm.entity.*;
import fpoly.asm.repository.HoaDonChiTietRepo;
import fpoly.asm.repository.HoaDonRepo;
import fpoly.asm.repository.LichSuHoaDonRepo;
import fpoly.asm.repository.SanPhamRepo;
import fpoly.asm.service.ServiceProject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
public class BanHangServiceImpl {
    // list hóa đơn chi tiết
    //List<HoaDonChiTiet> hoaDonChiTiets = new ArrayList<>();
    // hóa đơn chờ


    // import  repo : hóa đon , hoad đon chi tiets , sản phẩm chi tiét
    @Autowired
    private HoaDonRepo hoaDonRepo;
    @Autowired
    private HoaDonChiTietRepo hoaDonChiTietRepo;
    @Autowired
    private SanPhamRepo sanPhamRepo;
    @Autowired
    private ServiceProject serviceProject;
    @Autowired
    private LichSuHoaDonRepo lichSuHoaDonRepo;
    Map<Integer, List<HDCTRequest>> hoaDonCho = new HashMap<>();
    Integer myHoaDon = null;


    // hàm hiển thị hóa đơn chi tiêt theo id hoa đon
    public List<HDCTRequest> findHDCTbyIdHoaDon(int idHoaDon) {
        return hoaDonCho.get(idHoaDon);
    }

    // hàm hiển thị hóa đơn
    public List<HoaDon> findHoaDon() {
        return hoaDonRepo.findAll().stream().filter(loc -> loc.getTrangThai().equalsIgnoreCase(serviceProject.getTrangThaiHoaDon().getHoaDonCho())).toList();
    }

    // find sản phẩm có phân trang
    public Page<SanPham> findAllSanPham(Pageable pageable) {
        return sanPhamRepo.findAll(pageable);
    }

    // tạo hóa đơn
    @Transactional
    public void saveHoaDon(HoaDon hoaDon) {
        List<HoaDon> hoaDons = hoaDonRepo.findAll();
        int count = (int) hoaDons.stream().filter(sl -> sl.getTrangThai().equalsIgnoreCase(serviceProject.getTrangThaiHoaDon().getHoaDonCho())).count();
        if (count <= 4) {
            hoaDon.setMa(serviceProject.generateMaHoaDon());
            hoaDon.setNgayTao(serviceProject.getTimeNow());
            hoaDon.setLoaiHoaDon(serviceProject.getLoaiHoaDon().getBanHangTaiQuay());
            hoaDon.setTrangThai(serviceProject.getTrangThaiHoaDon().getHoaDonCho());
            hoaDonRepo.saveAndFlush(hoaDon);
            LichSuHoaDon lichSuHoaDon = new LichSuHoaDon();
            lichSuHoaDon.setHoaDon(hoaDon);
            lichSuHoaDon.setMoTa("Khách Hàng Mua Tại Quầy");
            lichSuHoaDon.setNgayTao(hoaDon.getNgayTao());
            lichSuHoaDon.setNhanVien(serviceProject.getNhanVien());
            lichSuHoaDon.setTrangThai(hoaDon.getTrangThai());
            lichSuHoaDonRepo.save(lichSuHoaDon);
            hoaDonCho.put(hoaDon.getId(), new ArrayList<>());
        }
    }

    // find hoa don
    @Transactional
    public HoaDon findHoaDonById(int id) {
        return hoaDonRepo.findById(id).orElseThrow();
    }

    // tạo , sửa Sản Phẩm
    @Transactional
    public void saveSanPham(SanPham sanPham) {
        sanPhamRepo.save(sanPham);
    }

    public List<HDCTRequest> getHoaDonChiTietByIdHoaDon(int idHoaDon) {
        this.myHoaDon = idHoaDon;
        return hoaDonCho.get(idHoaDon);
    }

    public Integer getIdHoaDon() {
        return myHoaDon;
    }

    // add sản phảm vào hoa don cho
    public void addSanPhamInHoaDonCho(int idSP) {
        System.out.println("id hóa đơn khi thêm là  : " + myHoaDon);
        // Kiểm tra id hóa đơn hợp lệ
        if (myHoaDon == null) {
            throw new IllegalStateException("Hoa don is not set.");
        }
        // Lấy danh sách hiện tại từ hoaDonCho
        List<HDCTRequest> listHDCT = hoaDonCho.get(myHoaDon);
        if (listHDCT == null) {
            listHDCT = new ArrayList<>();
            hoaDonCho.put(myHoaDon, listHDCT);
        }
        // call sản phẩm trong database ra
        SanPham spDB = sanPhamRepo.findById(idSP).orElseThrow();
        // tạo 1 đối tượng sản phẩm mới
        HDCTRequest sanPham = new HDCTRequest(spDB.getId(), spDB.getTen(), spDB.getGia(), 1);
        // Thêm sản phẩm vào danh sách hiện tại
        int soLuongUpdateKhiTrung = 1;
        boolean checkSP = false;
        for (int i = 0; i < listHDCT.size(); i++) {
            if (listHDCT.get(i).getId().equals(sanPham.getId())) {
                HDCTRequest sanPhamGet = listHDCT.get(i);
                HDCTRequest spUpdate = new HDCTRequest(sanPhamGet.getId(), sanPhamGet.getTen(), sanPhamGet.getGia(), sanPhamGet.getSoLuong() + soLuongUpdateKhiTrung);
                // sanPhamUpdate.setSoLuong(soLuongUpdateKhiTrung + sanPhamUpdate.getSoLuong());
                listHDCT.set(i, spUpdate);
                checkSP = true;
            }
        }
        if (checkSP == false) {
            listHDCT.add(sanPham);
        }
        System.out.println("Danh sách hóa đơn chi tiết sau khi thêm: " + listHDCT);
    }

    public Double getTongTien() {
        List<HDCTRequest> getTongTien = this.getHoaDonChiTietByIdHoaDon(myHoaDon);
        Double tongTien = 0.0;
        if (getTongTien != null) {
            tongTien = getTongTien.stream().mapToDouble(tt -> tt.getGia() * tt.getSoLuong()).sum();
        }
        return tongTien;
    }

    public Integer getSoLuongSanPham() {
        List<HDCTRequest> getSoLuong = this.getHoaDonChiTietByIdHoaDon(myHoaDon);
        if (getSoLuong == null) {
            getSoLuong = Collections.emptyList();
        }
        return getSoLuong.stream().mapToInt(HDCTRequest::getSoLuong).sum();
    }

    // add Sản phẩm vào hoa đơn chi tiết database
    @Transactional
    public void thanhToan(String hinhThucThanhToan) {
        //add sp vào hóa đơn chi tiết
        HoaDon hoaDon = hoaDonRepo.findById(myHoaDon).orElseThrow();
        List<HDCTRequest> hoaDonChiTietLocal = this.getHoaDonChiTietByIdHoaDon(myHoaDon);
        for (HDCTRequest local : hoaDonChiTietLocal) {
            SanPham sanPhamUpdate = sanPhamRepo.findById(local.getId()).orElseThrow();

            HoaDonChiTiet hoaDonChiTiet = new HoaDonChiTiet();
            hoaDonChiTiet.setHoaDon(hoaDon);
            hoaDonChiTiet.setSanPham(sanPhamUpdate);
            hoaDonChiTiet.setSoLuong(local.getSoLuong());
            hoaDonChiTiet.setGia(local.getGia());
            hoaDonChiTietRepo.save(hoaDonChiTiet);
            // hàm update lại sản phẩm trong database
            List<SanPham> sanPhamList = sanPhamRepo.findAll();
            for (SanPham spDB : sanPhamList) {
                if (local.getId().equals(spDB.getId())) {
                    int slUpdate = spDB.getSoLuong() - local.getSoLuong();
                    spDB.setSoLuong(slUpdate);
                    sanPhamRepo.save(spDB);
                }
            }
        }
        // update lại hóa đơn
        hoaDon.setHinhThucThanhToan(hinhThucThanhToan);
        hoaDon.setTrangThai(serviceProject.getTrangThaiHoaDon().getDanHoanThanh());
        //hoaDon.setNgayTao();
        hoaDon.setTongTien(this.getTongTien());
        hoaDon.setNhanVien(serviceProject.getNhanVien());
        hoaDon.setNgayCapNhap(serviceProject.getTimeNow());
        hoaDonRepo.save(hoaDon);
        LichSuHoaDon lichSuHoaDon = new LichSuHoaDon();
        lichSuHoaDon.setHoaDon(hoaDon);
        lichSuHoaDon.setNgayTao(hoaDon.getNgayCapNhap());
        lichSuHoaDon.setTrangThai(hoaDon.getTrangThai());
        lichSuHoaDon.setMoTa("Hóa Đơn Đã Hoàn Thành");
        lichSuHoaDon.setNhanVien(serviceProject.getNhanVien());
        lichSuHoaDonRepo.save(lichSuHoaDon);
        hoaDonCho.remove(myHoaDon);
        myHoaDon = null;
    }
}

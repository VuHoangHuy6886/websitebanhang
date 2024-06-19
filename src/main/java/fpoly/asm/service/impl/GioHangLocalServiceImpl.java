package fpoly.asm.service.impl;

import fpoly.asm.entity.*;
import fpoly.asm.repository.*;
import fpoly.asm.service.CookiesService;
import fpoly.asm.service.GioHangLocalService;
import fpoly.asm.service.ServiceProject;
import jakarta.persistence.EntityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Date;
import java.util.*;

@Service
public class GioHangLocalServiceImpl implements GioHangLocalService {
    @Autowired
    private SanPhamRepo sanPhamRepo;
    @Autowired
    private CookiesService cookiesService;
    @Autowired
    private KhachHangRepo khachHangRepo;
    @Autowired
    private EntityManager entityManager;
    @Autowired
    private HoaDonRepo hoaDonRepo;
    @Autowired
    private HoaDonChiTietRepo hoaDonChiTietRepo;
    private Map<Integer, SanPham> maps = new HashMap<>();
    @Autowired
    private ServiceProject serviceProject;
    @Autowired
    private LichSuHoaDonRepo lichSuHoaDonRepo;

    @Override
    public void add(int id) {
        // Lấy sản phẩm từ cơ sở dữ liệu
        SanPham sanPhamInDatabase = this.entityManager.find(SanPham.class, id);
        int slSpAdd = 1;
        if (sanPhamInDatabase != null) {
            // Tạo một bản sao của sản phẩm từ cơ sở dữ liệu
            SanPham sp = new SanPham();
            sp.setId(sanPhamInDatabase.getId());
            sp.setTen(sanPhamInDatabase.getTen());
            sp.setGia(sanPhamInDatabase.getGia());
            sp.setSoLuong(sanPhamInDatabase.getSoLuong());
            sp.setMoTa(sanPhamInDatabase.getMoTa());
            sp.setDanhMuc(sanPhamInDatabase.getDanhMuc());
            // update sản phẩm vào giỏ hàng
            if (maps.containsKey(id)) {
                System.out.println("sản phẩm đã có trong giỏ");
                // Lấy sản phẩm từ giỏ hàng
                SanPham sanPhamInCart = maps.get(id);
                // Tạo một bản sao của sản phẩm từ giỏ hàng
                SanPham sanPhamInCartCopy = new SanPham();
                sanPhamInCartCopy.setId(sanPhamInCart.getId());
                sanPhamInCartCopy.setTen(sanPhamInCart.getTen());
                sanPhamInCartCopy.setGia(sanPhamInCart.getGia());
                sanPhamInCartCopy.setSoLuong(sanPhamInCart.getSoLuong());
                sanPhamInCartCopy.setMoTa(sanPhamInCart.getMoTa());
                sanPhamInCartCopy.setDanhMuc(sanPhamInCart.getDanhMuc());

                // Tăng số lượng của sản phẩm trong giỏ hàng lên 1
                int newSoLuong = sanPhamInCartCopy.getSoLuong() + slSpAdd;
                sanPhamInCartCopy.setSoLuong(newSoLuong);
                maps.put(id, sanPhamInCartCopy);
            } else {
                sp.setSoLuong(slSpAdd);
                maps.put(sp.getId(), sp);
            }
        } else {
            System.out.println("Sản Phẩm có id : " + id + " không tìm thấy ");
        }
    }


    @Override
    @Transactional
    public void remove(int id) {
        this.maps.remove(id);
    }

    @Override
    @Transactional
    public void update(int id, int soLuong) {
        // Lấy sản phẩm từ cơ sở dữ liệu
        SanPham sanPhamInDatabase = this.entityManager.find(SanPham.class, id);
        if (sanPhamInDatabase != null) {
            // Tạo một bản sao của sản phẩm từ cơ sở dữ liệu
            SanPham sp = new SanPham();
            sp.setId(sanPhamInDatabase.getId());
            sp.setTen(sanPhamInDatabase.getTen());
            sp.setGia(sanPhamInDatabase.getGia());
            sp.setSoLuong(sanPhamInDatabase.getSoLuong());
            sp.setMoTa(sanPhamInDatabase.getMoTa());
            sp.setDanhMuc(sanPhamInDatabase.getDanhMuc());
            // update sản phẩm vào giỏ hàng
            sp.setSoLuong(soLuong);
            System.out.println("sản phẩm update là : " + sp.getSoLuong());
            maps.put(sp.getId(), sp);

        } else {
            System.out.println("Sản Phẩm có id : " + id + " không tìm thấy ");
        }
    }

    @Override
    @Transactional
    public void clear() {
        this.maps.clear();
    }

    @Override
    public Collection<SanPham> getAllSanPham() {
        return this.maps.values();
    }

    @Override
    @Transactional
    public int getSoLuong() {
        return this.maps.values()
                .stream()
                .mapToInt(item -> item.getSoLuong())
                .sum();
    }

    @Override
    @Transactional
    public Double getTongTien() {
        return this.maps.values().stream().mapToDouble(item -> item.getGia() * item.getSoLuong()).sum();
    }

    @Override
    @Transactional
    public void themGioHangVaoHoaDon(KhachHang khachHang) {
        // Tạo đối tượng HoaDon
        HoaDon hoaDon = new HoaDon();
        //Lưu và flush khachHang
        khachHangRepo.saveAndFlush(khachHang);
        // Đặt khách hàng và ngày tạo cho hoaDon
        hoaDon.setKhachHang(khachHang);
        hoaDon.setNgayTao(serviceProject.getTimeNow());
        hoaDon.setTrangThai(serviceProject.getTrangThaiHoaDon().getChoXacNhan());
        hoaDon.setLoaiHoaDon(serviceProject.getLoaiHoaDon().getBanHangTrenWeb());
        hoaDon.setHinhThucThanhToan(serviceProject.getHinhThucThanhToan().getThanhToanKhiNhanHang());
        hoaDon.setMa(serviceProject.generateMaHoaDon());
        hoaDon.setNhanVien(serviceProject.getNhanVien());
        // set tổng tiền
        Double tongTien = maps.values().stream().mapToDouble(item -> item.getSoLuong() * item.getGia()).sum();
        hoaDon.setTongTien(tongTien);

        // Lưu  và flush  Hóa Đơn
        hoaDonRepo.saveAndFlush(hoaDon);
        // tạo lịch sử hóa đơn
        LichSuHoaDon lichSuHoaDon = new LichSuHoaDon();
        lichSuHoaDon.setHoaDon(hoaDon);
        lichSuHoaDon.setNgayTao(hoaDon.getNgayTao());
        lichSuHoaDon.setMoTa("Khách Hàng Đặt Hàng Trên Web");
        lichSuHoaDon.setTrangThai(hoaDon.getTrangThai());
        lichSuHoaDonRepo.save(lichSuHoaDon);

        // đưa những sản phẩm từ giỏ hàng vào hóa đơn chi tiết

        // solution One : nếu sản phẩm đó chưa có trong hóa đơn chi tiết thì Thêm
        // solution Two : nếu sản phẩm đó có trong hóa đơn chi tiết thì cập nhập số  lượng

        // B-1 : duyệt map

        // B-2 : Tạo 1 đối tượng

        //B-3 save :  hóa đơn chi tiết vào database

        //B-4 Clear : giỏ hàng
        for (SanPham sp : maps.values()) {
            HoaDonChiTiet hoaDonChiTiet = new HoaDonChiTiet();
            hoaDonChiTiet.setHoaDon(hoaDon);
            hoaDonChiTiet.setSanPham(sp);
            hoaDonChiTiet.setSoLuong(sp.getSoLuong());
            hoaDonChiTiet.setGia(sp.getGia());
            hoaDonChiTietRepo.save(hoaDonChiTiet);
            // hàm update lại sản phẩm trong database
            List<SanPham> sanPhamList = sanPhamRepo.findAll();
            for (SanPham spDB : sanPhamList) {
                if (sp.getId().equals(spDB.getId())) {
                    int slUpdate = spDB.getSoLuong() - sp.getSoLuong();
                    spDB.setSoLuong(slUpdate);
                    sanPhamRepo.save(spDB);
                }
            }

        }
        maps.clear();
    }
}

package fpoly.asm.service;

import fpoly.asm.entity.KhachHang;
import fpoly.asm.entity.SanPham;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;

public interface GioHangLocalService {
    @Transactional
    void add(int id);

    void remove(int id);

    void update(int id, int soLuong);

    void clear();

    Collection<SanPham> getAllSanPham();

    int getSoLuong();

    Double getTongTien();

    void themGioHangVaoHoaDon(KhachHang khachHang);
}

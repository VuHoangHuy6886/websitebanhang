package fpoly.asm.service;

import fpoly.asm.entity.HoaDon;
import fpoly.asm.entity.HoaDonChiTiet;
import fpoly.asm.entity.SanPham;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface BanHangService {
    List<HoaDonChiTiet> findByIdHoaDon(int hoaDonId);

    List<HoaDon> findHoaDonByNumber();

    Page<SanPham> findAllSanPham(Pageable pageable);
}

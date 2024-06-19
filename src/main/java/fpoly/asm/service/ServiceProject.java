package fpoly.asm.service;

import fpoly.asm.dto.request.HinhThucThanhToan;
import fpoly.asm.dto.request.LoaiHoaDon;
import fpoly.asm.dto.request.TrangThaiHoaDon;
import fpoly.asm.entity.NhanVien;

import java.util.Date;

public interface ServiceProject {
    Date getTimeNow();

    String formatDateToString(Date date);

    TrangThaiHoaDon getTrangThaiHoaDon();

    NhanVien NhanVienDangNhap(int id);

    NhanVien getNhanVien();

    HinhThucThanhToan getHinhThucThanhToan();

    LoaiHoaDon getLoaiHoaDon();

    String generateMaHoaDon();

    String showErrors(Integer idSanPham, String message);

    String getError();

    Integer getIdSP();
}

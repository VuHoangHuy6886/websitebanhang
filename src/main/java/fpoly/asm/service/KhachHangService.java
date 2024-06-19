package fpoly.asm.service;

import fpoly.asm.entity.KhachHang;

import java.util.List;
import java.util.Optional;

public interface KhachHangService {
    public List<KhachHang> findAll();

    Optional<KhachHang> findById(int id);

    void save(KhachHang khachHang);

    void delete(int id);
}

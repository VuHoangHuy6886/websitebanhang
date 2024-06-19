package fpoly.asm.service;

import fpoly.asm.entity.DanhMuc;
import fpoly.asm.entity.SanPham;

import java.util.List;
import java.util.Optional;

public interface DanhMucService {
    List<DanhMuc> findAll();

    Optional<DanhMuc> findById(int id);

    void save(DanhMuc danhMuc);

    void delete(int id);
}

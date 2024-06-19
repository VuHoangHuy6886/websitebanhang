package fpoly.asm.service;


import fpoly.asm.entity.SanPham;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface SanPhamSerice {
    Page<SanPham> findAll(Pageable pageable);

    Optional<SanPham> findById(int id);

    void save(SanPham sanPham);

    void delete(int id);
}

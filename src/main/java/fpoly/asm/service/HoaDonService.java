package fpoly.asm.service;

import fpoly.asm.dto.SanPhamDTO;
import fpoly.asm.entity.HoaDon;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface HoaDonService {
    List<HoaDon> findAll();

    Optional<HoaDon> findById(int id);

    void save(HoaDon hoaDon);

    void delete(int id);

    List<SanPhamDTO> findSanPhamByHoaDonId(int idHoaDon);

    Page<HoaDon> findHoaDonAndSortDay(int page, int size);
}

package fpoly.asm.repository;

import fpoly.asm.dto.SanPhamDTO;
import fpoly.asm.entity.HoaDon;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HoaDonRepo extends JpaRepository<HoaDon, Integer> {
    // viết query
    @Query(value = "SELECT * FROM hoa_don ORDER BY ma DESC LIMIT 1", nativeQuery = true)
    HoaDon findLastHoaDon();

    @Query("select hd from HoaDon hd order by hd.ngayTao desc ")
    Page<HoaDon> findHoaDonAndSortDay(Pageable pageable);
}

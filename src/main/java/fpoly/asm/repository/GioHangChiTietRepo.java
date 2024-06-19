package fpoly.asm.repository;

import fpoly.asm.entity.GioHangChiTiet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GioHangChiTietRepo extends JpaRepository<GioHangChiTiet, Integer> {
}

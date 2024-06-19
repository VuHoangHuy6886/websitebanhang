package fpoly.asm.repository;

import fpoly.asm.entity.NhanVien;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface NhanVienRepo extends JpaRepository<NhanVien, Integer> {
    @Query("SELECT u FROM NhanVien u JOIN FETCH u.vaiTros WHERE u.ten = :username")
    NhanVien findByTen(@Param("username") String username);
}

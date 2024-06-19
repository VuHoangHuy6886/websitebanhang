package fpoly.asm.repository;

import fpoly.asm.dto.SanPhamDTO;
import fpoly.asm.entity.HoaDonChiTiet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HoaDonChiTietRepo extends JpaRepository<HoaDonChiTiet, Integer> {
    @Query("SELECT new fpoly.asm.dto.SanPhamDTO(sp.ten, hdct.gia, hdct.soLuong) " +
            "FROM HoaDon hd " +
            "JOIN hd.hoaDonChiTiets hdct " +
            "JOIN hdct.sanPham sp " +
            "WHERE hd.id = :hoaDonId")
    List<SanPhamDTO> findSanPhamByHoaDonId(@Param("hoaDonId") int hoaDonId);

    @Query("select h from HoaDonChiTiet  h where h.hoaDon.id =:idHoaDon")
    List<HoaDonChiTiet> findHoaDonChiTietByIdHoaDon(@Param("idHoaDon") int idHoaDon);
}

package fpoly.asm.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Entity
@Table(name = "hoa_don_chi_tiet")
public class HoaDonChiTiet {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_sp")
    private SanPham sanPham;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_hd")
    private HoaDon hoaDon;
    @Column(name = "gia")
    private Double gia;
    @Column(name = "so_luong")
    private int soLuong;
}

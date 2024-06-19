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
@Table(name = "gio_hang_chi_tiet")
public class GioHangChiTiet {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_gio_hang")
    private GioHang gioHang;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_sp")
    private SanPham sanPham;
    @Column(name = "so_luong")
    private int soLuong;
    @Column(name = "gia_ban")
    private Double giaBan;
    @Column(name = "trang_thai")
    private int trangThai;
}

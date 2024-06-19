package fpoly.asm.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Entity
@Table(name = "hoa_don")
public class HoaDon {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;
    @Column(name = "ma")
    private String ma;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_khach_hang")
    private KhachHang khachHang;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_nhan_vien")
    private NhanVien nhanVien;
    @Column(name = "ngay_tao")
    private Date ngayTao;
    @Column(name = "hinh_thuc_thanh_toan")
    private String hinhThucThanhToan;
    @Column(name = "loai_hoa_don")
    private String loaiHoaDon;
    @Column(name = "tong_tien")
    private Double tongTien;
    @OneToMany(mappedBy = "hoaDon")
    private List<HoaDonChiTiet> hoaDonChiTiets;
    @OneToMany(mappedBy = "hoaDon", cascade = CascadeType.ALL)
    private List<LichSuHoaDon> lichSuHoaDons;
    @Column(name = "trang_thai")
    private String trangThai;
    @Column(name = "ngay_cap_nhap")
    private Date ngayCapNhap;
}

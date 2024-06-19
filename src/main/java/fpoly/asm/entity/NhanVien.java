package fpoly.asm.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Entity
@Table(name = "nhan_vien")
public class NhanVien {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")

    private int id;
    @NotBlank(message = "Ten ko được trống")
    @Column(name = "ten")
    private String ten;
    @NotBlank(message = "email ko được trống")
    @Column(name = "email")
    private String email;
    @NotBlank(message = "mat_khau ko được trống")
    @Column(name = "mat_khau")
    private String mat_khau;
    @NotBlank(message = "dia_chi ko được trống")
    @Column(name = "dia_chi")
    private String dia_chi;
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "nhanvien_vaitro",
            joinColumns = @JoinColumn(name = "nv_id"),
            inverseJoinColumns = @JoinColumn(name = "vt_id"))
    @NotNull(message = "vaiTros ko được trống")
    private List<VaiTro> vaiTros;
    @Column(name = "trang_thai")
    private String trangThai;
    @OneToMany(mappedBy = "nhanVien")
    private List<HoaDon> hoaDons;
    @OneToMany(mappedBy = "nhanVien")
    private List<LichSuHoaDon> lichSuHoaDons;

}

package fpoly.asm.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
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
@Table(name = "khach_hang")
public class KhachHang {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;
    @Column(name = "ten")
    @NotEmpty(message = "ten không được trống")
    private String ten;
    @Column(name = "sdt")
    @NotEmpty(message = "sdt không được trống")
    private String sdt;
    @Column(name = "email")
    @NotEmpty(message = "email không được trống")
    private String email;
    @Column(name = "mat_khau")
    @NotEmpty(message = "mat_khau không được trống")
    private String mat_khau;
    @Column(name = "dia_chi")
    @NotEmpty(message = "dia chỉ không được trống")
    private String dia_chi;
    @OneToOne(mappedBy = "khachHang")
    private GioHang gioHang;
    @OneToMany(mappedBy = "khachHang")
    private List<HoaDon> hoaDons;
}

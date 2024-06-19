package fpoly.asm.entity;

import jakarta.persistence.*;
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
@Table(name = "gio_hang")
public class GioHang {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;
    @OneToOne
    @JoinColumn(name = "id_kh")
    private KhachHang khachHang;
    @Column(name = "tong_tien")
    private double tongTien;
    @OneToMany(mappedBy = "gioHang")
    private List<GioHangChiTiet> gioHangChiTiets;
}

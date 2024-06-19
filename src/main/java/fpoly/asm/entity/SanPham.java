package fpoly.asm.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

import java.io.Serializable;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "san_pham")
public class SanPham implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "ten")
    @NotEmpty(message = "ten ko được để trống !")
    private String ten;

    @Column(name = "gia")
    @NotNull(message = "giá ko được để trống")
    @Positive(message = "giá phải là số dương")
    @DecimalMin(value = "0.0", message = "giá phải là  số")
    private double gia;

    @Column(name = "so_luong")
    @NotNull(message = "số Lượng Ko được Để trống !")
    @Positive(message = "số Lượng phai la so duong")
    @Min(value = 1, message = "số lượng ít nhất là 1")
    private int soLuong;

    @Column(name = "mo_ta")
    @NotEmpty(message = "mo tả không được để trống")
    private String moTa;

    @Column(name = "trang_thai")
    @NotEmpty(message = "trang thái ko được để trống ")
    private String trangThai;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_danh_muc")
    @NotNull(message = "danh mục không được để trống")
    private DanhMuc danhMuc;

    @OneToMany(mappedBy = "sanPham")
    private List<GioHangChiTiet> gioHangChiTiets;
}

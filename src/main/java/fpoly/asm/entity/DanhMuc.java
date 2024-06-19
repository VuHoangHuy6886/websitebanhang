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
@Getter
@Setter
@Entity
@Table(name = "danh_muc")
public class DanhMuc {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "ten")
    @NotEmpty(message = "ten không được trống")
    private String ten;

    @Column(name = "trang_thai")
    @NotEmpty(message = "trang Thái không được trống")
    private String trangThai;

    @OneToMany(mappedBy = "danhMuc")
    private List<SanPham> sanPhamList;
}

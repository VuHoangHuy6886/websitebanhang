package fpoly.asm.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class HinhThucThanhToan {
    private String thanhToanKhiNhanHang;
    private String chuyenKhoan;
    private String tienMat;
}
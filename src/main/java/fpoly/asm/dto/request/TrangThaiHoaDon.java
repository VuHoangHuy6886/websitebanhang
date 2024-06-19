package fpoly.asm.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class TrangThaiHoaDon {
    private String hoaDonCho;
    private String choXacNhan;
    private String daXacNhan;
    private String dangVanChuyen;
    private String danHoanThanh;
    private String huy;
}

package fpoly.asm.dto;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class SanPhamDTO {
    private String ten;
    private Double gia;
    private int soLuong;
}

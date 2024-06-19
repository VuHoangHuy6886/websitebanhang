package fpoly.asm.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class HDCTRequest {
    private Integer id;


    private String ten;

    private double gia;

    private int soLuong;
}

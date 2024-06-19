package fpoly.asm.service.impl;

import fpoly.asm.dto.request.HinhThucThanhToan;
import fpoly.asm.dto.request.LoaiHoaDon;
import fpoly.asm.dto.request.TrangThaiHoaDon;
import fpoly.asm.entity.HoaDon;
import fpoly.asm.entity.NhanVien;
import fpoly.asm.repository.HoaDonRepo;
import fpoly.asm.repository.NhanVienRepo;
import fpoly.asm.service.ServiceProject;
import jakarta.persistence.criteria.CriteriaBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.StringReader;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;

@Service
public class ServiceProjectImpl implements ServiceProject {
    public static NhanVien NHAN_VIEN_ID = null;
    public static String errors;
    public static Integer idSp;
    @Autowired
    private NhanVienRepo nhanVienRepo;
    @Autowired
    private HoaDonRepo hoaDonRepo;

    @Override
    public Date getTimeNow() {
        // Lấy thời gian hiện tại
        LocalDateTime localDateTime = LocalDateTime.now().withNano(0); // Làm tròn tới giây
        // Chuyển đổi LocalDateTime sang java.sql.Timestamp
        Timestamp timestamp = Timestamp.valueOf(localDateTime);
        return timestamp;
    }

    @Override
    public String formatDateToString(Date date) {
        // Chuyển đổi Date sang LocalDateTime
        LocalDateTime localDateTime = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();

        // Định dạng LocalDateTime thành chuỗi với định dạng yyyy-MM-dd HH:mm:ss
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return localDateTime.format(formatter);
    }

    @Override
    public TrangThaiHoaDon getTrangThaiHoaDon() {
        TrangThaiHoaDon tthd = new TrangThaiHoaDon("Hóa Đơn Chờ", "Chờ Xác Nhận", "Đã Xác Nhận", "Đang Vận Chuyển", "Đã Hoàn Thành", "Hủy");
        return tthd;
    }

    @Override
    public NhanVien NhanVienDangNhap(int id) {
        return NHAN_VIEN_ID = nhanVienRepo.findById(id).orElseThrow();
    }

    @Override
    public NhanVien getNhanVien() {
        return NHAN_VIEN_ID;
    }

    @Override
    public HinhThucThanhToan getHinhThucThanhToan() {
        HinhThucThanhToan httt = new HinhThucThanhToan("Thanh Toán Khi Nhận Hàng", "Chuyển Khoản", "Tiền Mặt");
        return httt;
    }

    @Override
    public LoaiHoaDon getLoaiHoaDon() {
        LoaiHoaDon lhd = new LoaiHoaDon("Bán Tại Quầy", "Bán Trên Website");
        return lhd;
    }

    @Override
    public String generateMaHoaDon() {
        // Lấy mã hóa đơn cuối cùng trong cơ sở dữ liệu (nếu có)
        HoaDon lastHoaDon = hoaDonRepo.findLastHoaDon();
        int nextMa = 1;

        // Kiểm tra xem lastHoaDon có null hay không
        if (lastHoaDon != null) {
            String lastMa = lastHoaDon.getMa();

            // Kiểm tra xem lastMa có null hay không
            if (lastMa != null) {
                try {
                    nextMa = Integer.parseInt(lastMa.substring(3)) + 1;
                } catch (NumberFormatException e) {
                    // Xử lý trường hợp lastMa không phải là số hợp lệ
                    e.printStackTrace();
                    // Nếu gặp lỗi, vẫn sử dụng nextMa = 1
                }
            }
        }

        // Tạo mã hóa đơn mới
        return "MHD" + String.format("%03d", nextMa);
    }

    @Override
    public String showErrors(Integer idSanPham, String message) {
        idSp = idSanPham;
        return errors = message;
    }

    @Override
    public String getError() {
        return errors;
    }

    @Override
    public Integer getIdSP() {
        return idSp;
    }
}

package fpoly.asm.test;

import fpoly.asm.service.ServiceProject;
import fpoly.asm.service.impl.ServiceProjectImpl;

public class TestDateTime {
    //    public static String getCurrentTime() {
//        // Lấy thời gian hiện tại
//        LocalDateTime now = LocalDateTime.now();
//
//        // Định dạng thời gian theo định dạng mong muốn
//        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
//
//        // Trả về thời gian hiện tại dưới dạng chuỗi
//        return now.format(formatter);
//    }
    public static ServiceProject serviceProject = new ServiceProjectImpl();


    public static void main(String[] args) {
        // Gọi hàm getCurrentTime và in kết quả
        //System.out.println("Current Date and Time: " + getCurrentTime());
        // System.out.println("thời gian : " + serviceProject.getTimeNow());
    }

}

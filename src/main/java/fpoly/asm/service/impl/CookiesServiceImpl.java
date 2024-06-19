package fpoly.asm.service.impl;

import fpoly.asm.entity.SanPham;
import fpoly.asm.service.CookiesService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CookiesServiceImpl implements CookiesService {
    @Autowired
    private HttpServletRequest request;

    @Autowired
    private HttpServletResponse response;
    private static final String cart = "cart";

    @Override
    public List<SanPham> getAll() {
//        List<SanPham> sanPhamList = new ArrayList<>();
//        Cookie[] cookies = request.getCookies();
//        if (cookies != null) {
//            for (Cookie cookie : cookies) {
//                if (cart.equals(cookie.getName())) {
//                    String sanPhamJson = cookie.getValue();
//                    if (!StringUtils.isEmpty(sanPhamJson)) {
//                        // Convert JSON string to list of SanPham objects
//                        ObjectMapper objectMapper = new ObjectMapper();
//                        try {
//                            SanPham[] sanPhams = objectMapper. (sanPhamJson, SanPham[].class);
//                            sanPhamList = Arrays.asList(sanPhams);
//                        } catch (JsonProcessingEreadValuexception e) {
//                            e.printStackTrace();
//                        }
//                    }
//                }
//            }
//        }
        return null;
    }

    @Override
    public void add(SanPham sanPham) {
        if (sanPham != null) {
            Cookie cookie = new Cookie(cart, String.valueOf(sanPham.getId()));
            cookie.setMaxAge(30 * 24 * 60 * 60); // 30 days
            cookie.setPath("/");
            response.addCookie(cookie);
            System.out.println("Ten Cookies : " + cookie.getName() + " Value  : " + cookie.getValue());
        }
    }


    @Override
    public void remove() {
        Cookie cookie = new Cookie(cart, null);
        cookie.setMaxAge(0);
        cookie.setPath("/");
        response.addCookie(cookie);
    }
}

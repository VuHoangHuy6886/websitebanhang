package fpoly.asm.service;

import fpoly.asm.entity.SanPham;

import java.util.List;

public interface CookiesService {
    List<SanPham> getAll();

    void add(SanPham sanPham);

    void remove();
}

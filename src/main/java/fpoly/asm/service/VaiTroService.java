package fpoly.asm.service;

import fpoly.asm.entity.VaiTro;

import java.util.List;

public interface VaiTroService {
    List<VaiTro> findAll();

    VaiTro findById(Integer id);

    void save(VaiTro vt);

    void remove(Integer id);

    List<VaiTro> findAllById(List<Integer> vaiTroIds);
}

package fpoly.asm.service.impl;

import fpoly.asm.entity.NhanVien;
import fpoly.asm.repository.NhanVienRepo;
import fpoly.asm.service.NhanVienService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class NhanVienServiceImpl implements NhanVienService {
    @Autowired
    private NhanVienRepo nhanVienRepo;

    @Override
    public List<NhanVien> findAll() {
        return nhanVienRepo.findAll();
    }

    @Override
    public NhanVien findById(Integer id) {
        return nhanVienRepo.findById(id).orElseThrow();
    }

    @Override
    @Transactional
    public void save(NhanVien nhanVien) {
        nhanVienRepo.save(nhanVien);
    }

    @Override
    @Transactional
    public void remove(Integer id) {
        nhanVienRepo.findById(id);
    }
}

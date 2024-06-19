package fpoly.asm.service.impl;

import fpoly.asm.entity.KhachHang;
import fpoly.asm.repository.KhachHangRepo;
import fpoly.asm.service.KhachHangService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class KhachHangServiceImpl implements KhachHangService {
    @Autowired
    private KhachHangRepo khachHangRepo;

    @Override
    public List<KhachHang> findAll() {
        return khachHangRepo.findAll();
    }

    @Override
    public Optional<KhachHang> findById(int id) {
        Optional<KhachHang> khachHangOptional = khachHangRepo.findById(id);
        return khachHangOptional;
    }

    @Override
    @Transactional
    public void save(KhachHang khachHang) {
        khachHangRepo.save(khachHang);
    }

    @Override
    @Transactional
    public void delete(int id) {
        khachHangRepo.deleteById(id);
    }
}

package fpoly.asm.service.impl;

import fpoly.asm.entity.DanhMuc;
import fpoly.asm.repository.DanhMucRepo;
import fpoly.asm.service.DanhMucService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class DanhMucServiceImpl implements DanhMucService {
    @Autowired
    private DanhMucRepo danhMucRepo;

    @Override
    public List<DanhMuc> findAll() {
        return danhMucRepo.findAll();
    }

    @Override
    public Optional<DanhMuc> findById(int id) {
        Optional<DanhMuc> danhMuc = danhMucRepo.findById(id);
        return danhMuc;
    }

    @Override
    @Transactional
    public void save(DanhMuc danhMuc) {
        danhMucRepo.save(danhMuc);
    }

    @Override
    @Transactional
    public void delete(int id) {
        danhMucRepo.deleteById(id);
    }
}

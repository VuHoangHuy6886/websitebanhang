package fpoly.asm.service.impl;

import fpoly.asm.entity.SanPham;
import fpoly.asm.repository.SanPhamRepo;
import fpoly.asm.service.SanPhamSerice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class SanPhamServiceImpl implements SanPhamSerice {
    @Autowired
    private SanPhamRepo sanPhamRepository;


    @Override
    public Page<SanPham> findAll(Pageable pageable) {
        return sanPhamRepository.findAll(pageable);
    }

    @Override
    public Optional<SanPham> findById(int id) {
        Optional<SanPham> sanPhamOptional = sanPhamRepository.findById(id);
        return sanPhamOptional;
    }

    @Override
    @Transactional
    public void save(SanPham sanPham) {
        this.sanPhamRepository.save(sanPham);
    }

    @Override
    public void delete(int id) {
        this.sanPhamRepository.deleteById(id);
    }
}

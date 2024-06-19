package fpoly.asm.service.impl;

import fpoly.asm.dto.SanPhamDTO;
import fpoly.asm.entity.HoaDon;
import fpoly.asm.repository.HoaDonChiTietRepo;
import fpoly.asm.repository.HoaDonRepo;
import fpoly.asm.service.HoaDonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class HoaDonServiceImpl implements HoaDonService {
    @Autowired
    private HoaDonRepo hoaDonRepo;
    @Autowired
    private HoaDonChiTietRepo hoaDonChiTietRepo;

    @Override
    public List<HoaDon> findAll() {
        return hoaDonRepo.findAll();
    }

    @Override
    public Optional<HoaDon> findById(int id) {
        Optional<HoaDon> hoaDon = hoaDonRepo.findById(id);
        return hoaDon;
    }

    @Override
    @Transactional
    public void save(HoaDon hoaDon) {
        hoaDonRepo.save(hoaDon);
    }

    @Override
    public void delete(int id) {
        hoaDonRepo.deleteById(id);
    }

    @Override
    public List<SanPhamDTO> findSanPhamByHoaDonId(int idHoaDon) {
        return hoaDonChiTietRepo.findSanPhamByHoaDonId(idHoaDon);
    }

    @Override
    public Page<HoaDon> findHoaDonAndSortDay(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return hoaDonRepo.findHoaDonAndSortDay(pageable);
    }
}

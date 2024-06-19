package fpoly.asm.service.impl;

import fpoly.asm.entity.VaiTro;
import fpoly.asm.repository.VaiTroRepo;
import fpoly.asm.service.VaiTroService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class VaiTroServiceImpl implements VaiTroService {
    @Autowired
    private VaiTroRepo vaiTroRepo;

    @Override
    public List<VaiTro> findAll() {
        return vaiTroRepo.findAll();
    }

    @Override
    public VaiTro findById(Integer id) {
        return vaiTroRepo.findById(id).orElseThrow();
    }

    @Override
    @Transactional
    public void save(VaiTro vt) {
        vaiTroRepo.save(vt);
    }

    @Override
    @Transactional
    public void remove(Integer id) {
        vaiTroRepo.deleteById(id);
    }

    @Override
    public List<VaiTro> findAllById(List<Integer> vaiTroIds) {
        return vaiTroRepo.findAllById(vaiTroIds);
    }
}

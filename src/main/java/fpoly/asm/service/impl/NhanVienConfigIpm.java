package fpoly.asm.service.impl;

import fpoly.asm.entity.NhanVien;
import fpoly.asm.entity.VaiTro;
import fpoly.asm.repository.NhanVienRepo;
import fpoly.asm.service.ServiceProject;
import fpoly.asm.service.UserConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class NhanVienConfigIpm implements UserConfig {
    private final NhanVienRepo nhanVienRepo;
    private ServiceProject serviceProject;

    @Autowired
    public NhanVienConfigIpm(NhanVienRepo nhanVienRepo, ServiceProject serviceProject) {
        this.nhanVienRepo = nhanVienRepo;
        this.serviceProject = serviceProject;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        NhanVien nhanVien = nhanVienRepo.findByTen(username);
        serviceProject.NhanVienDangNhap(nhanVien.getId());
        return new org.springframework.security.core.userdetails.User(
                nhanVien.getTen(),
                nhanVien.getMat_khau(),
                getVaiTro(nhanVien.getVaiTros())
        );
    }

    private List<? extends GrantedAuthority> getVaiTro(List<VaiTro> vaiTros) {
        return vaiTros.stream().map(vt -> new SimpleGrantedAuthority(vt.getTen())).toList();
    }
}

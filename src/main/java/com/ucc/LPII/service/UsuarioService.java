package com.ucc.LPII.service;

import com.ucc.LPII.dto.UsuarioRequestDTO;
import com.ucc.LPII.dto.UsuarioResponseDTO;
import com.ucc.LPII.entity.Usuario;
import com.ucc.LPII.exception.EmailAlreadyExistsException;
import com.ucc.LPII.mapper.UsuarioMapper;
import com.ucc.LPII.repository.UsuarioRepository;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
@Transactional
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final UsuarioMapper usuarioMapper;
    private final PasswordEncoder passwordEncoder;

    public UsuarioService(UsuarioRepository usuarioRepository, UsuarioMapper usuarioMapper, PasswordEncoder passwordEncoder) {
        this.usuarioRepository = usuarioRepository;
        this.usuarioMapper = usuarioMapper;
        this.passwordEncoder = passwordEncoder;
    }

    public UsuarioResponseDTO registrarUsuario(UsuarioRequestDTO dto) {
        if (usuarioRepository.findByCorreo(dto.correo()).isPresent()) {
            throw new EmailAlreadyExistsException(dto.correo());
        }

        Usuario usuario = usuarioMapper.dtoToEntity(dto);
        usuario.setContrasena(passwordEncoder.encode(dto.contrasena()));
        usuario.setRol("ROLE_USER");

        Usuario usuarioGuardado = usuarioRepository.save(usuario);
        return usuarioMapper.entityToDTO(usuarioGuardado);
    }

    // CORRECCIÓN: Descomentado y adaptado para evaluar la autoridad exacta sin duplicar prefijos
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public List<UsuarioResponseDTO> obtenerTodosLosUsuarios() {
        return usuarioRepository.findAll()
                .stream()
                .map(usuarioMapper::entityToDTO)
                .toList();
    }
}
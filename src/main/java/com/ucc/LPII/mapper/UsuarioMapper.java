package com.ucc.LPII.mapper;

import com.ucc.LPII.dto.UsuarioRequestDTO;
import com.ucc.LPII.dto.UsuarioResponseDTO;
import com.ucc.LPII.entity.Usuario;
import org.springframework.stereotype.Component;

@Component
public class UsuarioMapper {

    public Usuario dtoToEntity(UsuarioRequestDTO dto) {
        Usuario usuario = new Usuario();
        usuario.setNombre(dto.nombre());
        usuario.setCorreo(dto.correo());
        usuario.setEdad(dto.edad());
        // Recuerda: la contraseña se encriptará en el Service con BCrypt antes de guardar en la base de datos.
        return usuario;
    }

    public UsuarioResponseDTO entityToDTO(Usuario usuario) {
        return new UsuarioResponseDTO(
            usuario.getId(),
            usuario.getNombre(),
            usuario.getCorreo(),
            usuario.getEdad(),
            usuario.getRol()
        );
    }
}
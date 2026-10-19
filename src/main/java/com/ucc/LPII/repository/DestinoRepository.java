package com.ucc.LPII.repository;

import com.ucc.LPII.entity.Destino;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DestinoRepository extends JpaRepository<Destino, Long> {
    // Hereda automáticamente todos los métodos CRUD necesarios para DestinoService
}
package com.ucc.LPII.repository;

import com.ucc.LPII.entity.Zona;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ZonaRepository extends JpaRepository<Zona, Long> {
    
    // El método debe llamarse findByDestino_Id y recibir un Long
    List<Zona> findByDestino_Id(Long destinoId);
}
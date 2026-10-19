package com.ucc.LPII.repository;

import com.ucc.LPII.entity.Prestador;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PrestadorRepository extends JpaRepository<Prestador, Long> {
    // Select * from prestadores where zona_id = {idZona}
    List<Prestador> findByZona_Id(Long idZona);
}
package com.ucc.LPII.repository;

import com.ucc.LPII.entity.Prestador;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PrestadorRepository extends JpaRepository<Prestador, Long> {
    
    // Consulta existente: Recupera el listado completo de prestadores por Zona
    List<Prestador> findByZona_Id(Long idZona);

    // NUEVA ADICIÓN OBLIGATORIA: Verifica la existencia de dependencias activas (retorna true o false)
    // Este método es el que utiliza ZonaService.borrarUnaZona(id) para evitar el error de clave foránea.
    boolean existsByZona_Id(Long idZona);
}
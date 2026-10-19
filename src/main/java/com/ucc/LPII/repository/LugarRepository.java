package com.ucc.LPII.repository;

import com.ucc.LPII.entity.Lugar;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LugarRepository extends JpaRepository<Lugar, Long> {
    // Select * from lugares where zona_id = {idZona}
    List<Lugar> findByZona_Id(Long idZona);
}
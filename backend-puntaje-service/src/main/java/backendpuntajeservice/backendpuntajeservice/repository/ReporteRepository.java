package backendpuntajeservice.backendpuntajeservice.repository;

import backendpuntajeservice.backendpuntajeservice.entity.ReporteEntity;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;

@Repository
public interface ReporteRepository extends CrudRepository<ReporteEntity, Long> {

    @Query(value = "SELECT * FROM reporte WHERE rut = :rut", nativeQuery = true)
    ReporteEntity findByRut(@Param("rut") String rut);

    @Query(value = "SELECT COUNT(*) FROM reporte WHERE rut = :rut", nativeQuery = true)
    int countByRut(@Param("rut") String rut);

    //eliminar por rut
    @Transactional
    @Modifying
    @Query(value = "DELETE FROM reporte WHERE rut = :rut", nativeQuery = true)
    void eliminarReporte(@Param("rut") String rut);

    @Transactional
    @Modifying
    @Query(value = "DELETE FROM reporte", nativeQuery = true)
    void eliminarReportes();


}
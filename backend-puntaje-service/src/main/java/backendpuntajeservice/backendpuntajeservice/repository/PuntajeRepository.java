package backendpuntajeservice.backendpuntajeservice.repository;

import backendpuntajeservice.backendpuntajeservice.entity.PuntajeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface PuntajeRepository extends JpaRepository<PuntajeEntity, Long> {

    @Query(value = "SELECT COUNT(*) FROM puntaje WHERE rut = :rut", nativeQuery = true)
    int verificar_puntaje(@Param("rut") String rut);

    @Query(value = "SELECT AVG(puntaje) AS promedio_puntajes FROM puntaje WHERE rut = :rut GROUP BY rut", nativeQuery = true)
    Double calcularPromedioPuntajesPorRut(@Param("rut") String rut);

    @Query(value = "SELECT COUNT(*) FROM puntaje", nativeQuery = true)
    int verificarTabla();

    @Query(value = "SELECT COUNT(*) FROM puntaje WHERE rut = :rut", nativeQuery = true)
    int examenesRendidos(@Param("rut") String rut);


    @Query(value = "SELECT COUNT(*) FROM puntaje WHERE rut = :rut  AND puntaje = :puntaje", nativeQuery = true)
    int buscarPuntaje(@Param("rut") String rut, @Param("puntaje") int puntaje);

    //eliminar por rut
    @Transactional
    @Modifying
    @Query(value = "DELETE FROM puntaje WHERE rut = :rut", nativeQuery = true)
    void eliminarPuntaje(@Param("rut") String rut);


}

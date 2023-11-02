package tingeso_mingeso.backendcuotasservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import tingeso_mingeso.backendcuotasservice.entity.PagoEntity;

import java.util.ArrayList;
import java.util.Date;

@Repository
public interface PagoRepository extends JpaRepository<PagoEntity, Long> {
    @Query(value = "SELECT COUNT(*) FROM pago WHERE rut = :rut", nativeQuery = true)
    int verificar_pago(@Param("rut") String rut);

    @Query(value = "SELECT * FROM pago WHERE rut = :rut", nativeQuery = true)
    ArrayList<PagoEntity> obtenerPagosEstudiante(@Param("rut") String rut);

    @Transactional
    @Modifying
    @Query(value = "DELETE FROM pago WHERE rut = :rut", nativeQuery = true)
    void eliminarPago(@Param("rut") String rut);

    @Query(value = "SELECT SUM(monto) FROM pago WHERE rut = :rut", nativeQuery = true)
    int obtenerMontoTotal(@Param("rut") String rut);

    @Query(value = "SELECT tipo_pago FROM pago WHERE rut = :rut limit 1;", nativeQuery = true)
    String obtenerTipoPago(@Param("rut") String rut);

    //Retorna las cantidades de cuotas pagadas
    @Query(value = "SELECT COUNT(*) FROM pago WHERE rut = :rut AND estado = 'Pagado'", nativeQuery = true)
    int obtenerCantidadCuotasPagadas(@Param("rut") String rut);

    //Monto total pagado
    @Query(value = "SELECT SUM(monto) FROM pago WHERE rut = :rut AND estado = 'Pagado'", nativeQuery = true)
    int obtenerMontoTotalPagado(@Param("rut") String rut);

    //Fecha del ultimo pago
    @Query(value = "SELECT fecha_pago FROM pago WHERE rut = :rut AND estado = 'Pagado' ORDER BY fecha_pago DESC LIMIT 1", nativeQuery = true)
    Date obtenerFechaUltimoPago(@Param("rut") String rut);

}

package backendpuntajeservice.backendpuntajeservice.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.util.Date;

@Entity  //entidad- persiste o pueden ser grabados en la base de datos
@Table(name = "reporte")  //mapea con la base de datos, el link que vincula
@Data   //anotaciones de lowbord , para no generar getter o setters
@NoArgsConstructor
@AllArgsConstructor
public class ReporteEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true, nullable = false)
    private Long id;

    private String rut;
    private String nombreEstudiante;
    private int examenesRendidos;
    private int promedioExamenes;
    private int montoTotalArancel;
    private String tipoPago;
    private int numeroCuotas;
    private int cuotasPagadas;
    private int MontoTotalPagado;
    private int MontoTotalPendiente;
    private int cuotasRetraso;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date fecha_ultimopago;

}

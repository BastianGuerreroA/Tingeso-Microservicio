package tingeso_mingeso.backendcuotasservice.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "pago")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class PagoEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true, nullable = false)
    private Long id;

    private String rut;
    private String tipo_pago;
    private String estado;
    private double descuento;
    private double interes;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date fecha_vencimiento;
    private Date fecha_pago;
    private int monto;
    private int numeroCuotasPactadas;
}

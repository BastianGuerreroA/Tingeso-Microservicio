package backendpuntajeservice.backendpuntajeservice.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PagoEntity {
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

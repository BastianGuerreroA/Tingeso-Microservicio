package backendpuntajeservice.backendpuntajeservice.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DescuentoData {
    private String rut;
    private double descuento;
    private Date fechaExamen;
}

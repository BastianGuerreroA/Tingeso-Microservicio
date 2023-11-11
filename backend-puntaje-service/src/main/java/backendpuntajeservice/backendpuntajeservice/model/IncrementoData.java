package backendpuntajeservice.backendpuntajeservice.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class IncrementoData {
    String rut;
    int monto_total;
    String tipo_pago;
    int numero_cuotas;
    int cuotas_pagadas;
    int monto_total_pagado;
    Date fecha_ultimo_pago;
}

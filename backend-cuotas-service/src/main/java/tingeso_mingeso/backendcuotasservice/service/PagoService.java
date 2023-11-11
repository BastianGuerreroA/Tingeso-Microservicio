package tingeso_mingeso.backendcuotasservice.service;

import lombok.Generated;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import tingeso_mingeso.backendcuotasservice.entity.PagoEntity;
import tingeso_mingeso.backendcuotasservice.model.StudentEntity;
import tingeso_mingeso.backendcuotasservice.repository.PagoRepository;
import tingeso_mingeso.backendcuotasservice.model.IncrementoData;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Optional;


@Service
public class PagoService {
    @Autowired
    PagoRepository pagoRepository;

    @Autowired
    RestTemplate restTemplate;

    public ArrayList<PagoEntity> obtenerPagos(){
        return (ArrayList<PagoEntity>) pagoRepository.findAll();
    }

    public StudentEntity obtenerEstudiante(Long id){
        ResponseEntity<StudentEntity> responseEntity = restTemplate.exchange(
                "http://localhost:8080/student/" + id,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<StudentEntity>() {
                });
        StudentEntity estudiante = responseEntity.getBody();
        return estudiante;
    }

    public ArrayList<PagoEntity> obtenerPagosEstudiante(Long id){
        ResponseEntity<StudentEntity> responseEntity = restTemplate.exchange(
                "http://localhost:8080/student/" + id,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<StudentEntity>() {
                });
        StudentEntity estudiante = responseEntity.getBody();
        return (ArrayList<PagoEntity>) pagoRepository.obtenerPagosEstudiante(estudiante.getRut());
    }

    public int countPagosStudent(String rut){
        return pagoRepository.verificar_pago(rut);
    }

    public Optional<PagoEntity> obtenerPorId(Long id){
        return pagoRepository.findById(id);
    }

    public void eliminarPago(String rut){
        System.out.println("rut:" + rut);
        pagoRepository.eliminarPago(rut);
    }

    public void guardarPago(PagoEntity usuario){
        PagoEntity pago =  pagoRepository.save(usuario);
    }


    //Verifica si el estudiante ya tiene un pago asociado
    public boolean verificar_pago(String rut){
        int res = pagoRepository.verificar_pago(rut);
        if(res == 0){
            return false;
        }else{
            return true;
        }
    }

    public double descuento_por_tipo_colegio(StudentEntity student){
        double res = 0;
        String type_school = student.getType_school();
        if(type_school.equals("Municipal")){
            res = 0.2;
        }else if(type_school.equals("Subvencionado")){
            res = 0.1;
        } else if(type_school.equals("Privado")){
            res = 0;
        }
        return res;
    }

    public double descuento_por_egreso(StudentEntity student){
        double res = 0;
        int actual_year = LocalDate.now().getYear();
        int year = student.getYear_of_graduation();
        int diferencia = actual_year - year;
        if( diferencia < 1){
            res = 0.15;
        }else if( diferencia == 1 || diferencia == 2){
            res = 0.08;
        } else if( diferencia == 3 || diferencia == 4){
            res = 0.04;
        } else if( diferencia >= 5){
            res = 0;
        }
        return res;
    }

    public int max_cuotas(StudentEntity student){
        int res = 0;
        String type_school = student.getType_school();
        if(type_school.equals("Municipal")){
            res = 10;
        }else if(type_school.equals("Subvencionado")){
            res = 7;
        } else if(type_school.equals("Privado")){
            res = 4;
        }
        return res;
    }

    public double descuento_total(StudentEntity student){
        double res = 0;
        res = descuento_por_tipo_colegio(student) + descuento_por_egreso(student);
        res = Math.round(res * 100.0) / 100.0;
        return res;
    }

    public int monto_matricula(StudentEntity student) {
        int monto = 1500000;
        double descuento = descuento_total(student);
        monto = (int) (monto - (monto * descuento));
        return monto;
    }

    public Date sumarMesesFecha(Date fecha, int meses) {
        LocalDate localDate = fecha.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        localDate = localDate.plusMonths(meses);
        return Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
    }

    public void generarPagos(int id_student, String tipoPago, int cantidadCuotas , String rut) {
        ResponseEntity<StudentEntity> responseEntity = restTemplate.exchange(
                "http://localhost:8080/student/" + id_student,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<StudentEntity>() {
                });
        StudentEntity estudiante = responseEntity.getBody();

        Date fechaVencimiento = new Date();
        int monto = monto_matricula(estudiante) / cantidadCuotas;


        if (tipoPago.equals("Contado")) {
            PagoEntity pago = new PagoEntity();
            pago.setRut(rut);
            pago.setTipo_pago(tipoPago);
            pago.setEstado("Pagado");
            pago.setDescuento(0.5);
            pago.setInteres(0);
            pago.setMonto(750000 + 70000);
            pago.setNumeroCuotasPactadas(1);

            Calendar calendar = Calendar.getInstance();
            calendar.setTime(fechaVencimiento);
            calendar.set(Calendar.DAY_OF_MONTH, 10);
            fechaVencimiento = calendar.getTime();

            pago.setFecha_pago(fechaVencimiento);

            pago.setFecha_vencimiento(fechaVencimiento);

            guardarPago(pago); // Guarda
        } else {
            for (int i = 0; i < cantidadCuotas; i++) {
                if(i == 0){
                    PagoEntity pago = new PagoEntity();
                    pago.setRut(rut);
                    pago.setTipo_pago(tipoPago);
                    pago.setEstado("No Pagado");
                    pago.setDescuento(descuento_total(estudiante));
                    pago.setInteres(0);
                    pago.setMonto(monto + 70000);
                    pago.setNumeroCuotasPactadas(cantidadCuotas);

                    fechaVencimiento = sumarMesesFecha(fechaVencimiento, 1);
                    //cambiar el dia de fechavencimiento al 10
                    Calendar calendar = Calendar.getInstance();
                    calendar.setTime(fechaVencimiento);
                    calendar.set(Calendar.DAY_OF_MONTH, 10);
                    fechaVencimiento = calendar.getTime();
                    pago.setFecha_vencimiento(fechaVencimiento);

                    guardarPago(pago); // Guarda
                }else {
                    PagoEntity pago = new PagoEntity();
                    pago.setRut(rut);
                    pago.setTipo_pago(tipoPago);
                    pago.setEstado("No Pagado");
                    pago.setDescuento(descuento_total(estudiante));
                    pago.setInteres(0);
                    pago.setMonto(monto);
                    pago.setNumeroCuotasPactadas(cantidadCuotas);

                    fechaVencimiento = sumarMesesFecha(fechaVencimiento, 1);
                    Calendar calendar = Calendar.getInstance();
                    calendar.setTime(fechaVencimiento);
                    calendar.set(Calendar.DAY_OF_MONTH, 10);
                    fechaVencimiento = calendar.getTime();
                    pago.setFecha_vencimiento(fechaVencimiento);

                    guardarPago(pago); // Guarda
                }
            }
        }
    }


    @Generated
    public boolean verificarFechaPago() {
        Date fecha = new Date();

        // Obtén el día del mes
        int diaDelMes = fecha.getDate();

        // Verifica si el día está entre 5 y 10
        return diaDelMes >= 5 && diaDelMes <= 10;
    }


    public int realizar_pago(Long id){
        Optional<PagoEntity> pago = obtenerPorId(id);
        PagoEntity pago2 = pago.get();
        if(verificarFechaPago()){
            pagar(pago2);
            return 1;
        }else{
            return 0;
        }
    }

    //Modificar los atributos estado y fecha de pago de la entidad PagoEntity
    public void pagar(PagoEntity pago){
        Date fechaActual = new Date();
        pago.setFecha_pago(fechaActual);
        pago.setEstado("Pagado");
        pagoRepository.save(pago);
    }

    public ArrayList<PagoEntity> obtenerPagosEstudiante2(String rut){
        return (ArrayList<PagoEntity>) pagoRepository.obtenerPagosEstudiante(rut);
    }


    //Aplica el descuento a la cuota(pago) al los meses pendientes de pago del estudiante en adelante del mens realizado
    public void descuento_por_puntaje(String rut, double nuevoDescuento, Date fechaRealizada) {
        ArrayList<PagoEntity> pagos = obtenerPagosEstudiante2(rut);

        // Obtener el mes y año de la fecha realizada
        Calendar calendarFechaRealizada = Calendar.getInstance();
        calendarFechaRealizada.setTime(fechaRealizada);
        int mesFechaRealizada = calendarFechaRealizada.get(Calendar.MONTH);
        int añoFechaRealizada = calendarFechaRealizada.get(Calendar.YEAR);

        for (PagoEntity pago : pagos) {
            // Obtener el mes y año de la fecha de vencimiento del pago
            Calendar calendarFechaVencimiento = Calendar.getInstance();
            calendarFechaVencimiento.setTime(pago.getFecha_vencimiento());
            int mesFechaVencimiento = calendarFechaVencimiento.get(Calendar.MONTH);
            int añoFechaVencimiento = calendarFechaVencimiento.get(Calendar.YEAR);

            // Verificar condiciones para aplicar el descuento
            if ((añoFechaVencimiento > añoFechaRealizada ||
                    (añoFechaVencimiento == añoFechaRealizada && mesFechaVencimiento >= mesFechaRealizada)) &&
                    "No Pagado".equals(pago.getEstado())) {

                // Aplicar descuento
                int montoAntiguo = pago.getMonto();
                int montoNuevo = (int) (montoAntiguo - (montoAntiguo * nuevoDescuento));
                pago.setMonto(montoNuevo);
                pago.setDescuento(nuevoDescuento);
                pagoRepository.save(pago);
            }
        }
    }


    // Calcula el interés basado en la diferencia de meses entre la fecha de pago y la fecha actual
    public double calcularInteres(LocalDate fechaPago) {
        // Obtener la fecha actual
        LocalDate fechaActual = LocalDate.now();

        // Si la fecha de pago es anterior a la fecha actual, se calcula el interés
        if (fechaPago.isBefore(fechaActual)) {
            // Calcular la diferencia de meses entre la fecha de pago y la fecha actual
            long mesesDiferencia = ChronoUnit.MONTHS.between(fechaPago.withDayOfMonth(1), fechaActual.withDayOfMonth(1));

            // Aplicar las tasas de interés según el número de meses de diferencia
            if (mesesDiferencia == 0) {
                return 0.0; // 0%
            } else if (mesesDiferencia == 1) {
                return 0.03; // 3%
            } else if (mesesDiferencia == 2) {
                return 0.06; // 6%
            } else if (mesesDiferencia == 3) {
                return 0.09; // 9%
            } else {
                return 0.15; // > 3 meses, 15%
            }
        } else {
            return 0.0; // Si la fecha de pago es igual o posterior a la fecha actual, se retorna 0.08
        }
    }

    public void calcularInteresPagos() {
        ArrayList<PagoEntity> pagos = obtenerPagos();
        Date fechaActual = new Date();

        for (PagoEntity pago : pagos) {
            if ("No Pagado".equals(pago.getEstado()) && "Cuotas".equals(pago.getTipo_pago()) && pago.getFecha_vencimiento().before(fechaActual)) {
                LocalDate localDate = pago.getFecha_vencimiento().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
                double interes = calcularInteres(localDate);
                pago.setInteres(interes);
                pago.setMonto((int) (pago.getMonto() + (pago.getMonto() * interes)));
                String rut_estudiante = pago.getRut();
                pagoRepository.save(pago);

                // Crear el objeto para enviar a la otra API
                IncrementoData incrementoData = new IncrementoData();
                incrementoData.setRut(rut_estudiante);

                String url = "http://localhost:8080/puntaje/reporte/incremento/";
                restTemplate.postForObject(url, incrementoData, String.class);
            }
        }
    }

    public IncrementoData actualizarReporte(IncrementoData incrementoData){
        String rut_student = incrementoData.getRut();
        int cantidad_cuotas = pagoRepository.verificar_pago(rut_student);
        incrementoData.setNumero_cuotas(cantidad_cuotas);
        if(cantidad_cuotas >0) {
            incrementoData.setMonto_total(pagoRepository.obtenerMontoTotal(rut_student));
            incrementoData.setTipo_pago(pagoRepository.obtenerTipoPago(rut_student));
            int cuotas_pagadas = pagoRepository.obtenerCantidadCuotasPagadas(rut_student);
            incrementoData.setCuotas_pagadas(cuotas_pagadas);
            if(cuotas_pagadas > 0) {
                incrementoData.setMonto_total_pagado(pagoRepository.obtenerMontoTotalPagado(rut_student));
                incrementoData.setFecha_ultimo_pago(pagoRepository.obtenerFechaUltimoPago(rut_student));
            }
            else{
                incrementoData.setMonto_total_pagado(0);
                incrementoData.setFecha_ultimo_pago(null);
            }
        }
        return incrementoData;
    }




}

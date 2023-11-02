package tingeso_mingeso.backendcuotasservice.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import tingeso_mingeso.backendcuotasservice.entity.PagoEntity;
import tingeso_mingeso.backendcuotasservice.model.StudentEntity;
import tingeso_mingeso.backendcuotasservice.repository.PagoRepository;

import java.time.LocalDate;
import java.time.ZoneId;
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

    public Optional<PagoEntity> obtenerPorId(Long id){
        return pagoRepository.findById(id);
    }

    public void eliminarPago(String rut){
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


    public void generarPagos(StudentEntity estudiante, String tipoPago, int cantidadCuotas , String rut) {
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

}

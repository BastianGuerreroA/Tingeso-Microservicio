package tingeso_mingeso.backendcuotasservice.controller;

import org.bouncycastle.asn1.ocsp.ResponseData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tingeso_mingeso.backendcuotasservice.entity.PagoEntity;
import tingeso_mingeso.backendcuotasservice.model.DescuentoData;
import tingeso_mingeso.backendcuotasservice.model.IncrementoData;
import tingeso_mingeso.backendcuotasservice.model.StudentEntity;
import tingeso_mingeso.backendcuotasservice.service.PagoService;

import java.util.Date;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/pago")
public class CuotasController {
    @Autowired
    PagoService pagoService;

    @GetMapping
    public ResponseEntity<List<PagoEntity>> getAll() {
        List<PagoEntity> pagos = pagoService.obtenerPagos();
        if(pagos.isEmpty())
            return ResponseEntity.noContent().build();
        return ResponseEntity.ok(pagos);
    }

    @PostMapping("/save/")
    public ResponseEntity<String> save(@RequestParam String id_student, @RequestParam String tipo_pago, @RequestParam String cantidadCuotas,@RequestParam String rut) {

        int id_student_int = Integer.parseInt(id_student);
        int cantidadCuotas_int = Integer.parseInt(cantidadCuotas);

        pagoService.generarPagos(id_student_int, tipo_pago, cantidadCuotas_int, rut);
        return ResponseEntity.ok("Pagos generados");
    }

    @GetMapping("/student/{id}")
    public ResponseEntity<List<PagoEntity>> getById(@PathVariable("id") Long id) {
        List<PagoEntity> pagos = pagoService.obtenerPagosEstudiante(id);
        if(pagos.isEmpty())
            return ResponseEntity.noContent().build();
        return ResponseEntity.ok(pagos);
    }

    @GetMapping("/buscar/")
    public ResponseEntity<Integer> buscar(@RequestParam String rut) {
        int pagos = pagoService.countPagosStudent(rut);
        return ResponseEntity.ok(pagos);
    }

    @GetMapping("/eliminar/")
    public ResponseEntity<String> eliminar(@RequestParam String rut) {
        System.out.println(rut);
        pagoService.eliminarPago(rut);
        return ResponseEntity.ok("Eliminado");
    }

    @GetMapping("/maxcuotas/")
    public ResponseEntity<Integer> maxcuotas(@RequestParam Long id_student) {
        StudentEntity student = pagoService.obtenerEstudiante(id_student);
        int cuotas = pagoService.max_cuotas(student);
        return ResponseEntity.ok(cuotas);
    }

    @GetMapping("/pagar/")
    public ResponseEntity<String> pagar(@RequestParam Long id) {
        int pagado = pagoService.realizar_pago(id);
        if(pagado == 1){
            return ResponseEntity.ok("Pagado");
        }else{
            return ResponseEntity.ok("No pagado");
        }
    }

    @PostMapping("/descuento")
    public ResponseEntity<String> descuento(@RequestBody DescuentoData descuentoData){

        String rut = descuentoData.getRut();
        Double descuento = descuentoData.getDescuento();
        Date fecha = descuentoData.getFechaExamen();

        pagoService.descuento_por_puntaje(rut,descuento,fecha);

        return ResponseEntity.ok("Descuento aplicado");
    }

    @PostMapping("/interes/")
    public ResponseEntity<String> interes() {
        pagoService.calcularInteresPagos();
        return ResponseEntity.ok("Interes aplicado");
    }

    @PostMapping("/actualizar/reporte")
    public ResponseEntity<IncrementoData> descuento(@RequestBody IncrementoData incrementoData){
        System.out.println(incrementoData.getRut());
        IncrementoData response = pagoService.actualizarReporte(incrementoData);
        return ResponseEntity.ok(response);
    }


}


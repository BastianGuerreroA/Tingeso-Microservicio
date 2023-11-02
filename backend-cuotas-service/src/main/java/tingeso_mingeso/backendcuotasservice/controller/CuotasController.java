package tingeso_mingeso.backendcuotasservice.controller;

import org.bouncycastle.asn1.ocsp.ResponseData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tingeso_mingeso.backendcuotasservice.entity.PagoEntity;
import tingeso_mingeso.backendcuotasservice.model.StudentEntity;
import tingeso_mingeso.backendcuotasservice.service.PagoService;

import java.util.List;
import java.util.Map;

@CrossOrigin(origins = "http://localhost:3000")
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
    public ResponseEntity<String> save(@RequestBody StudentEntity student, @RequestBody String tipo_pago, @RequestBody String cantidadCuotas, @RequestBody String rut) {
        return ResponseEntity.ok("Guardado");
    }
}


package backendpuntajeservice.backendpuntajeservice.controller;

import backendpuntajeservice.backendpuntajeservice.entity.PuntajeEntity;
import backendpuntajeservice.backendpuntajeservice.entity.ReporteEntity;
import backendpuntajeservice.backendpuntajeservice.model.IncrementoData;
import backendpuntajeservice.backendpuntajeservice.service.PuntajeService;
import backendpuntajeservice.backendpuntajeservice.service.ReporteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/puntaje")
public class PuntajeController {

    @Autowired
    PuntajeService subirData;

    @Autowired
    ReporteService reporteService;

    @PostMapping("/upload")
    public String uploadFile(@RequestParam("file") MultipartFile file) {
        subirData.guardar(file);
        String nombreArchivo = file.getOriginalFilename();
        subirData.leerCsv(nombreArchivo);
        return ResponseEntity.ok("Archivo cargado").toString();
    }

    @GetMapping("/eliminarArchivo")
    public String eliminar() {
        subirData.eliminarData();
        return ResponseEntity.ok("Archivo eliminado").toString();
    }

    @GetMapping("/calcularPromedio")
    public ResponseEntity<String> calcularPromedio() {
        if(subirData.verificarFechaPago()){
            reporteService.generarReporteBase();
            subirData.realizarCalculoPromedio();
            reporteService.actualizarRerporteFinal();
            return ResponseEntity.ok("Reporte realizado");
        }
        else{
            return ResponseEntity.ok("Reporte No realizado");
        }
    }

    @PostMapping("/reporte/incremento/")
    public ResponseEntity<String> incremento( @RequestBody IncrementoData incrementoData) {
        reporteService.incrementoRetraso(incrementoData.getRut());
        return ResponseEntity.ok("Incrementado");
    }


    @GetMapping()
    public ResponseEntity<List<PuntajeEntity>> getAll() {
        List<PuntajeEntity> puntajes = subirData.obtenerPuntajes();
        if(puntajes.isEmpty())
            return ResponseEntity.noContent().build();
        return ResponseEntity.ok(puntajes);
    }

    @GetMapping("/reporte/")
    public ResponseEntity<List<ReporteEntity>> obtenerReporte() {
        List<ReporteEntity> reporte = reporteService.obtenerReportes();
        if(reporte.isEmpty())
            return ResponseEntity.noContent().build();
        return ResponseEntity.ok(reporte);
    }


    
}

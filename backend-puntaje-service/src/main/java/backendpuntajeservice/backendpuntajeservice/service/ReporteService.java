package backendpuntajeservice.backendpuntajeservice.service;

import backendpuntajeservice.backendpuntajeservice.entity.ReporteEntity;
import backendpuntajeservice.backendpuntajeservice.model.IncrementoData;
import backendpuntajeservice.backendpuntajeservice.model.StudentEntity;
import backendpuntajeservice.backendpuntajeservice.model.PagoEntity;
import backendpuntajeservice.backendpuntajeservice.repository.ReporteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;


import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class ReporteService {
    @Autowired
    ReporteRepository reporteRepository;

    @Autowired
    RestTemplate restTemplate;

    String studentServiceUrl = "http://localhost:8080/student";

    public List<ReporteEntity> obtenerReportes(){
        return (ArrayList<ReporteEntity>) reporteRepository.findAll();
    }

    public ReporteEntity obtenerPorRut(String rut){
        return reporteRepository.findByRut(rut);
    }

    public void generarReporteBase() {

        StudentEntity[] estudiantes = restTemplate.getForObject(studentServiceUrl, StudentEntity[].class);

        for (StudentEntity estudiante : estudiantes) {
            if (reporteRepository.countByRut(estudiante.getRut()) == 0) {
                ReporteEntity reporte = new ReporteEntity();
                reporte.setRut(estudiante.getRut());
                reporte.setNombreEstudiante(estudiante.getNames());
                reporte.setExamenesRendidos(0);
                reporte.setPromedioExamenes(0);
                reporte.setMontoTotalArancel(0);
                reporte.setTipoPago("Sin Informacion");
                reporte.setNumeroCuotas(0);
                reporte.setCuotasPagadas(0);
                reporte.setMontoTotalPagado(0);
                reporte.setMontoTotalPendiente(0);
                reporte.setCuotasRetraso(0);
                reporte.setFecha_ultimopago(null);
                reporteRepository.save(reporte);
            }
        }
    }

    public void actualizarExamenesRendidos(String rut, int examenesRendidos , double promedio){
        ReporteEntity reporte = obtenerPorRut(rut);
        int examenesRendidosAnterior = reporte.getExamenesRendidos();
        reporte.setExamenesRendidos(examenesRendidosAnterior + examenesRendidos) ;
        int promedioInt = (int) promedio;
        reporte.setPromedioExamenes(promedioInt);
        reporteRepository.save(reporte);
    }


    public void actualizarRerporteFinal(){
        List<ReporteEntity> reportes = obtenerReportes();
        IncrementoData incrementoData = new IncrementoData();

        for (ReporteEntity reporte : reportes) {
            String rut = reporte.getRut();
            incrementoData.setRut(rut);
            System.out.println(incrementoData.getRut());
            IncrementoData response = restTemplate.postForObject("http://localhost:8080/pago/actualizar/reporte" , incrementoData, IncrementoData.class);

            if(response.getNumero_cuotas() > 0){
                int montoTotal = response.getMonto_total();
                reporte.setMontoTotalArancel(montoTotal - 70000);
                String tipoPago = response.getTipo_pago();
                reporte.setTipoPago(tipoPago);
                int numeroCuotas = response.getNumero_cuotas();
                reporte.setNumeroCuotas(numeroCuotas);

                int cuotasPagadas = response.getCuotas_pagadas();
                reporte.setCuotasPagadas(cuotasPagadas);
                int montoTotalPagado2 = 0;
                if(cuotasPagadas > 0) {
                    montoTotalPagado2 = response.getMonto_total_pagado();
                    reporte.setMontoTotalPagado(montoTotalPagado2);
                }
                else{
                    reporte.setMontoTotalPagado(0);
                }
                int montoTotalPendiente = montoTotal - montoTotalPagado2;
                reporte.setMontoTotalPendiente(montoTotalPendiente);
                if (cuotasPagadas > 0) {
                    Date fechaUltimoPago = response.getFecha_ultimo_pago();
                    reporte.setFecha_ultimopago(fechaUltimoPago);
                } else {
                    reporte.setFecha_ultimopago(null);
                }
                reporteRepository.save(reporte);
            }
        }
    }

    public void incrementoRetraso(String rut){
        if(reporteRepository.countByRut(rut) > 0){
            ReporteEntity reporte = obtenerPorRut(rut);
            int cuotasRetraso = reporte.getCuotasRetraso();
            reporte.setCuotasRetraso(cuotasRetraso + 1);
            reporteRepository.save(reporte);
        }
    }



}


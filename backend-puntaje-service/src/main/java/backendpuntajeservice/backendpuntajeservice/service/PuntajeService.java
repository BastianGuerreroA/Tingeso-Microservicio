package backendpuntajeservice.backendpuntajeservice.service;

import backendpuntajeservice.backendpuntajeservice.entity.PuntajeEntity;
import backendpuntajeservice.backendpuntajeservice.entity.ReporteEntity;
import backendpuntajeservice.backendpuntajeservice.model.DescuentoData;
import backendpuntajeservice.backendpuntajeservice.repository.PuntajeRepository;
import lombok.Generated;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;


import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
public class PuntajeService {

    @Autowired
    private PuntajeRepository dataRepository;

    @Autowired
    RestTemplate restTemplate;

    @Autowired
    private ReporteService reporteService;


    private final Logger logg = LoggerFactory.getLogger(PuntajeService.class);
    @Generated
    public String guardar(MultipartFile file){
        String filename = file.getOriginalFilename();
        if(filename != null){
            if(!file.isEmpty()){
                try{
                    byte [] bytes = file.getBytes();
                    Path path  = Paths.get(file.getOriginalFilename());
                    Files.write(path, bytes);
                    logg.info("Archivo guardado");
                }
                catch (IOException e){
                    logg.error("ERROR", e);
                }
            }
            return "Archivo guardado con exito!";
        }
        else{
            return "No se pudo guardar el archivo";
        }
    }



    @Generated
    public void leerCsv(String direccion) {
        String texto = "";
        BufferedReader bf = null;
        try {
            bf = new BufferedReader(new FileReader(direccion));
            String temp = "";
            String bfRead;
            int count = 1;
            while ((bfRead = bf.readLine()) != null) {
                if (count == 1) {
                    // Verificar si la primera línea contiene los encabezados esperados
                    if (!bfRead.trim().equalsIgnoreCase("Rut;Fecha;Puntaje")) {
                        System.err.println("El archivo no tiene el formato esperado.");
                        return;  // Detener la lectura si el formato no es el esperado
                    }
                    count = 0;
                } else {
                    // Verificar si la línea tiene el formato esperado antes de procesarla
                    String[] partes = bfRead.split(";");
                    if (partes.length == 3) {
                        guardarDataDB(partes[0], partes[1], partes[2]);
                        temp = temp + "\n" + bfRead;
                    } else {
                        System.err.println("Error en el formato de la línea: " + bfRead);
                    }
                }
            }
            texto = temp;
            System.out.println("Archivo leído exitosamente");
        } catch (Exception e) {
            System.err.println("No se encontró el archivo");
        } finally {
            if (bf != null) {
                try {
                    bf.close();
                } catch (IOException e) {
                    logg.error("ERROR", e);
                }
            }
        }
    }


    public void guardarData(PuntajeEntity data){
        dataRepository.save(data);
    }


    public void guardarDataDB(String rut, String fechaExamen, String puntaje){
        PuntajeEntity newData = new PuntajeEntity();
        newData.setRut(rut);
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        try {
            Date fecha = dateFormat.parse(fechaExamen);
            newData.setFechaExamen(fecha);
        } catch (ParseException e) {
            logg.error("Error al convertir la fecha", e);
        }

        try {
            int puntajeInt = Integer.parseInt(puntaje);
            newData.setPuntaje(puntajeInt);
        } catch (NumberFormatException e) {
            logg.error("Error al convertir el puntaje", e);
        }

        guardarData(newData);
    }
    public void eliminarData(){
        dataRepository.deleteAll();
    }



    public ArrayList<PuntajeEntity> obtenerData(){
        ArrayList<PuntajeEntity> datas = new ArrayList<>();
        dataRepository.findAll().forEach(datas::add);
        return datas;
    }

    public double calcularPromedioPuntajesPorRut(String rut){
        return dataRepository.calcularPromedioPuntajesPorRut(rut);
    }


    //descuento por el promedio de puntajes
    public double descuentoPromedio(double promedio){
        if (promedio >= 950 && promedio <= 1000){
            return 0.1;
        }
        else if (promedio >= 900 && promedio < 950){
            return 0.05;
        }
        else if (promedio >= 850 && promedio < 900){
            return 0.02;
        }
        else if (promedio < 850){
            return 0;
        }
        else{
            return 0;
        }
    }


    public void calcularPromedio(){
        ArrayList<PuntajeEntity> datas = obtenerData();
        ArrayList<String> ruts = new ArrayList<>();
        //obtener fecha de examen
        Date fechaExamen = datas.get(0).getFechaExamen();

        for (PuntajeEntity data : datas) {
            if (!ruts.contains(data.getRut())) {
                ruts.add(data.getRut());
            }
        }
        for (String rut : ruts) {
            double promedio = calcularPromedioPuntajesPorRut(rut);
            int examenesRendidos = dataRepository.examenesRendidos(rut);
            double descuento = descuentoPromedio(promedio);
            if(reporteService.obtenerPorRut(rut) != null){
                reporteService.actualizarExamenesRendidos(rut, examenesRendidos, promedio);
            }
            System.out.println("rut" + rut);

            DescuentoData descuentoData = new DescuentoData();
            descuentoData.setRut(rut);
            descuentoData.setDescuento(descuento);
            descuentoData.setFechaExamen(fechaExamen);

            String url = "http://localhost:8080/pago/descuento/";
            restTemplate.postForObject(url, descuentoData, String.class);


        }
    }


    public boolean verificarFechaPago() {
        Date fecha = new Date();

        int diaDelMes = fecha.getDate();

        // Verifica si el día está entre 5 y 10
        return diaDelMes < 5 || diaDelMes > 10;
    }

    public void realizarCalculoPromedio(){
        if (verificarFechaPago()) {  // Verifica si el día está entre 5 y 10
            if (dataRepository.verificarTabla() > 0) { // Verifica si hay datos en la tabla
                calcularPromedio(); // Calcula el promedio de puntajes
                eliminarData(); // Elimina los datos de la tabla
                String url = "http://localhost:8080/pago/interes/";
                restTemplate.postForLocation(url, null); // Envía la solicitud POST sin parámetros
            } else {
                String url = "http://localhost:8080/pago/interes/";
                restTemplate.postForLocation(url, null); // Envía la solicitud POST sin parámetros
                System.out.println("No hay datos en la tabla que calcular");
            }
        } else{
            System.out.println("Es el día de pago o no hay datos en la tabla que calcular");
        }
    }

    public ArrayList<PuntajeEntity> obtenerPuntajes(){
        return (ArrayList<PuntajeEntity>) dataRepository.findAll();
    }

}


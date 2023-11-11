import axios from 'axios';

const baseUrl = 'http://localhost:8080/puntaje';

class PuntajeService {
    uploadFile(file) {
        return axios.post(baseUrl + "/upload", file);
    }

    obtenerReporte() {
        return axios.get(baseUrl + "/reporte/");
    }

    obtenerPuntaje() {
        return axios.get(baseUrl);
    }

    calcularReporte() {
        return axios.get(baseUrl + "/calcularPromedio");
    }

    eliminarPuntaje() {
        return axios.get(baseUrl + "/eliminarArchivo");
    }
}

export default new PuntajeService();

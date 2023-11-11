import axios from 'axios';

const ESTUDIANTE_API_URL = "http://localhost:8080/student";

class EstudianteService {

    getEstudiantes(){
        return axios.get(ESTUDIANTE_API_URL);
    }

    getEstudianteByRut(rut){
        return axios.get(ESTUDIANTE_API_URL + rut);
    }
    createEstudiante(estudiante){
        return axios.post(ESTUDIANTE_API_URL, estudiante);
    }

    deleteEstudiante(id){
        return axios.get(ESTUDIANTE_API_URL + '/eliminar/' + id);
    }
}

export default new EstudianteService()
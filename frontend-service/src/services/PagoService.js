import axios from 'axios';

const pagos_url = "http://localhost:8080/pago";

class PagoService {

    guardarPago(id_student, tipo_pago, cantidadCuotas, rut){
        return axios.post(pagos_url + "/save/", null, {
            params: {
                id_student: id_student,
                tipo_pago: tipo_pago,
                cantidadCuotas: cantidadCuotas,
                rut: rut
            }
        });
    }

    obtenerPagos(id){
        return axios.get(pagos_url + "/student/" + id);
    }

    contienePagos(rut){
        return axios.get(pagos_url + "/buscar/", {
            params: {
                rut: rut
            }
        });
    }

    eliminarPagos(rut){
        return axios.get(pagos_url + "/eliminar/", {
            params: {
                rut: rut
            }
        });
    }

    maxCuotas(id_student){
        return axios.get(pagos_url + "/maxcuotas/", {
            params: {
                id_student: id_student
            }
        });
    }

    pagarPago(id){
        return axios.get(pagos_url + "/pagar/", {
            params: {
                id: id
            }
        });
    }

}
    
export default new PagoService()
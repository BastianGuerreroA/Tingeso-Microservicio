import React, { useState , useEffect  } from "react";
import { useNavigate } from "react-router-dom";
import { useLocation } from "react-router-dom";
import HeaderComponent from "./Headers/HeaderComponentMini";
import Swal from 'sweetalert2';
import PagoService from "../services/PagoService";

function GenerarPagos(props){

    const location = useLocation();
    const estudianteParaEditar = location.state?.estudiante || null;

    const initialState = {
        rut: "",
        names: "",
        lastnames: "",
        date_of_birth: "",
        type_school: "",
        school_name: "",
        year_of_graduation: "",
        tipo_pago: "",
        cantidadCuotas: ""
    };

    const [input, setInput] = useState(estudianteParaEditar || initialState);


    const [maxCuotas, setMaxCuotas] = useState(0);


    const navigate = useNavigate();
    const navigateHome = () => {
        navigate("/generar_cuotas");
    };
    
    const changeTipoPago = event => {
        setInput({ ...input, tipo_pago: event.target.value });
    };

    const changeCantidadCuotas = event => {
        setInput({ ...input, cantidadCuotas: event.target.value });
    };

    const guardarPago = (event) => {
        PagoService.guardarPago(input.id, input.tipo_pago, input.cantidadCuotas, input.rut)
        navigateHome();
    };

    useEffect(() => {
        if (estudianteParaEditar) {
            PagoService.maxCuotas(estudianteParaEditar.id).then((response) => {
                const maxCuotas = response.data; // O ajusta esto según cómo tu servicio devuelva el resultado.
                setMaxCuotas(maxCuotas);
            })
        }
    }, [estudianteParaEditar]);


    return(
        <div className="general">
            <HeaderComponent />
            <br></br>
            <div className="container">
                <div className="row">
                    <div className="col">
                        <h2>Ingresar Datos del Estudiante</h2>
                    </div>
                    <div className="col text-right">
                        <a href="/generar_cuotas" className="btn btn-secondary mt-2 float-end">Regresar</a>
                    </div>
                </div>
		    </div>
		    <div className="container">
                <div className="row">
                    <div className="col-md-6 mx-auto border">
                        <form method="post" onSubmit={guardarPago}>
                            <div className="form-group">
                                <label for="id">ID Estudiante</label>
                                <input  value = {input.id} type="text" className="form-control" id="id" name="id" readonly="readonly" />
                            </div>
                            <div className="form-group">
                                <label for="tipoPago">Tipo de Pago:</label>
                                <select value={input.tipo_pago} onChange={changeTipoPago} className="form-control" id="tipoPago" name="tipo_Pago">
                                    <option value="Contado">Contado</option>
                                    <option value="Cuotas">Cuotas</option>
                                </select>
                            </div>
                            <div className="form-group">
                                <label for="cantidadCuotas">Cantidad de Cuotas:</label>
                                <input onChange={changeCantidadCuotas}  type="number" className="form-control" id="cantidadCuotas" name="cantidadCuotas" min="1" max={maxCuotas} />
                            </div>
                            <button type="submit" className="btn btn-primary">Guardar</button>
                        </form>
                    </div>
                </div>
            </div>
        </div>
    )
}
    export default GenerarPagos;
import React, { useState, useEffect } from "react";
import PuntajeService from '../services/PuntajeService'
import HeaderComponent from './Headers/HeaderComponentMini'
import { useNavigate } from "react-router-dom";

function ListaArchivo() {
    const navigate = useNavigate();
    const [estudianteEntity, setEstudianteEntity] = useState([]);
    //const [input, setInput] = useState(initialState);

    useEffect(() => {
        PuntajeService.obtenerPuntaje().then((res) => {
            const estudianteArray = Array.isArray(res.data) ? res.data : [];
            setEstudianteEntity(estudianteArray);
        });
    }, []);

    const calcularReporte = () => {
        PuntajeService.calcularReporte().then( res => {
            console.log(res);
            if (res.data === "Reporte realizado") {
                alert("Reporte calculado con exito");
                window.location.reload(); // Recargar la página
            }
            else {
                alert("Reporte no calculado!!, puede que estes en fechas de pagos");
            }
        }
        );
    }

    const eliminarPuntaje = () => {
        PuntajeService.eliminarPuntaje().then( res => {
            alert("Puntajes eliminados con exito");
            window.location.reload(); // Recargar la página
        }
        );
    }

    return(
        <div className="general">
            <HeaderComponent/>
            <br></br><br></br>
            <div align = "center" className = "container my-2">
            <h1><b>Información de Puntajes.csv</b></h1>
            <table border = "1" className = "content-table">
                <thead>
                <tr>
                    <th>Rut</th>
                    <th>Fecha</th>
                    <th>Puntaje</th>
                </tr>
                </thead>
                <tbody>
                            {
                                estudianteEntity.map(
                                    student =>
                                        <tr key = {student.id}>
                                            <td>{student.rut}</td>
                                            <td>{student.fechaExamen ? student.fechaExamen.split("T")[0] : ""}</td>
                                            <td>{student.puntaje}</td>
                                        </tr>
                                )
                            }

                            </tbody>
                <tfoot>
                    <tr>
                        <td colspan="3">
                            <a className="btn btn-primary"   role="button" onClick={() => calcularReporte()}>Calcular Reporte</a>
                            <a className="btn btn-danger" role="button" style={{marginLeft: "30px"}}  onClick={() => eliminarPuntaje()} >Eliminar</a>
                        </td>
                    </tr>
                </tfoot>
            </table>
        </div>
        </div>
    )
}

export default ListaArchivo
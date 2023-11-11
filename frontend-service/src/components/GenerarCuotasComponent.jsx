import React, { useState, useEffect } from "react";
import EstudianteService from '../services/EstudianteService'
import PagoService from "../services/PagoService";
import HeaderComponent from './Headers/HeaderComponentMini'
import { useNavigate } from "react-router-dom";

function GenerarCuotasComponent() {
    const navigate = useNavigate();
    const [estudianteEntity, setEstudianteEntity] = useState([]);
    //const [input, setInput] = useState(initialState);

    useEffect(() => {
        EstudianteService.getEstudiantes().then((res) => {
            const estudianteArray = Array.isArray(res.data) ? res.data : [];
            setEstudianteEntity(estudianteArray);
        });
    }, []);

    const generarPagos = (estudiante) => {
        PagoService.contienePagos(estudiante.rut).then((response) => {
            const tienePagos = response.data; // O ajusta esto según cómo tu servicio devuelva el resultado.

            console.log("tienePagos: ", tienePagos);
            
            if (tienePagos > 0) {
                alert("El estudiante ya tiene pagos."); // Puedes mostrar un mensaje o tomar otra acción
            } else {
                navigate("/generar_pagos", { state: { estudiante } });
            }
        }).catch((error) => {
            // Manejo de errores si la verificación falla.
            console.error("Error al verificar pagos: ", error);
        });
    };

    const verPagos = (estudiante) => {

        navigate("/lista_pagos/" + estudiante.id, { state: { estudiante } });
    }

    const eliminarPagos = (estudiante) => {
        PagoService.eliminarPagos(estudiante.rut);
        alert("Pagos eliminados");
    }

    return(
        <div className="general">
            <HeaderComponent/>
            <br></br><br></br>
            <div>
                <div class ="container border">
                    <h2 className="text-center">Lista de estudiantes</h2>
                    <br></br>
                    <div className = "row">
                        <table class="table table-hover">
                            <thead>
                            <tr>
                                <th scope="col">Id</th>
                                <th scope="col">RUT</th>
                                <th scope="col">Apellidos</th>
                                <th scope="col">Nombres</th>
                                <th scope="col">Fecha Nacimiento</th>
                                <th scope="col">Tipo Colegio</th>
                                <th scope="col">Nombre Colegio</th>
                                <th scope="col">Año de egreso</th>
                                <th scope="col">Acciones</th>
                            </tr>
                            </thead>

                            <tbody>
                            {
                                estudianteEntity.map(
                                    student =>
                                        <tr key = {student.id}>
                                            <td>{student.id}</td>
                                            <td>{student.rut}</td>
                                            <td>{student.lastnames}</td>
                                            <td>{student.names}</td>
                                            <td>{student.date_of_birth.split("T")[0]}</td>
                                            <td>{student.type_school}</td>
                                            <td>{student.school_name}</td>
                                            <td>{student.year_of_graduation}</td>
                                            <td>
                                                <button className="btn btn-success" onClick={() => generarPagos(student)}>Generar Pagos</button>
                                                <button style={{marginLeft: "10px"}} className="btn btn-danger" onClick={() => eliminarPagos(student)} >Eliminar Pagos </button>
                                                <button style={{marginLeft: "20px"}} className="btn btn-secondary" onClick={() => verPagos(student)}>Pagos</button>
                                            </td>
                                        </tr>
                                )
                            }

                            </tbody>
                        </table>
                    </div>
                </div>

            </div>
        </div>
    )
}

export default GenerarCuotasComponent
import React, { useState, useEffect } from "react";
import EstudianteService from '../services/EstudianteService'
import HeaderComponent from './Headers/HeaderComponentMini'
import { useNavigate } from "react-router-dom";

function ListadoEstudianteComponent() {
    const navigate = useNavigate();
    const [estudianteEntity, setEstudianteEntity] = useState([]);
    //const [input, setInput] = useState(initialState);

    useEffect(() => {
        EstudianteService.getEstudiantes().then((res) => {
            const estudianteArray = Array.isArray(res.data) ? res.data : [];
            setEstudianteEntity(estudianteArray);
        });
    }, []);

    const deleteStudent = (id) => {
        EstudianteService.deleteEstudiante(id).then( res => {
            setEstudianteEntity(estudianteEntity.filter(student => student.id !== id));
        });
    }

    const editarEstudiante = (estudiante) => {
        navigate("/agregar_estudiante", { state: { estudiante } });
      };

    return(
        <div className="general">
            <HeaderComponent/>
            <br></br><br></br>
            <div>
                <div class ="container border">
                    <h2 className="text-center">Lista de estudiantes</h2>
                    <br></br>
                    <div className = "row">
                        <table className="table table-hover">
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
                                                <button className="btn btn-info" onClick={() => editarEstudiante(student)}>Editar</button>
                                                <button style={{marginLeft: "10px"}} className="btn btn-danger" onClick={() => deleteStudent(student.id)} >Eliminar </button>
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

export default ListadoEstudianteComponent
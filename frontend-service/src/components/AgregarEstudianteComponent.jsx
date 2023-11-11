import React, { useState } from "react";
import { useNavigate } from "react-router-dom";
import { useLocation } from "react-router-dom";
import HeaderComponent from "./Headers/HeaderComponentMini";
import Swal from 'sweetalert2';
import EstudianteService from "../services/EstudianteService";

function AgregarEstudianteComponent(props){

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
    };

    const [input, setInput] = useState(estudianteParaEditar || initialState);

    const navigate = useNavigate();
    const navigateHome = () => {
        navigate("/");
    };
    
    const changeRutHandler = event => {
        setInput({ ...input, rut: event.target.value });
    };
    const changeNombresHandler = event => {
        setInput({ ...input, names: event.target.value });
    };
    const changeApellidoHandler = event => {
        setInput({ ...input, lastnames: event.target.value });
    };
    const changeFechaNacimientoHandler = event => {
        setInput({ ...input, date_of_birth: event.target.value });
    };
    const changeAnioEgresoIDHandler = event => {
        setInput({ ...input, year_of_graduation: event.target.value });
    };
    const changeTipoColegioHandler = event => {
        setInput({ ...input, type_school: event.target.value });
    };
    const changeNombreColegioHandler = event => {
        setInput({ ...input, school_name: event.target.value });
    };

    const ingresarEstudiante2 = (event) => {
        let newEstudiante = {
            rut: input.rut,
            names: input.names,
            lastnames: input.lastnames,
            date_of_birth: input.date_of_birth,
            type_school: input.type_school,
            school_name: input.school_name,
            year_of_graduation: input.year_of_graduation
        };
        EstudianteService.createEstudiante(newEstudiante);
        navigateHome();
    };

    
    const ingresarEstudiante = (event) => {
        Swal.fire({
            title: "¿Desea registrar el estudiante?",
            text: "No podra cambiarse en caso de equivocación",
            icon: "question",
            showDenyButton: true,
            confirmButtonText: "Confirmar",
            confirmButtonColor: "rgb(68, 194, 68)",
            denyButtonText: "Cancelar",
            denyButtonColor: "rgb(190, 54, 54)",
        }).then((result) => {
            if (result.isConfirmed) {
                let newEstudiante = {
                    rut: input.rut,
                    names: input.names,
                    lastnames: input.lastnames,
                    date_of_birth: input.date_of_birth,
                    type_school: input.type_school,
                    school_name: input.school_name,
                    year_of_graduation: input.year_of_graduation
                };
                console.log(newEstudiante);
                EstudianteService.createEstudiante(newEstudiante);
                Swal.fire({
                    title: "Enviado",
                    timer: 2000,
                    icon: "success",
                    timerProgressBar: true,
                    didOpen: () => {
                        Swal.showLoading()
                      },
                    })
                navigateHome();
            }
        });
    };

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
					<a href="/" className="btn btn-secondary mt-2 float-end">Regresar</a>
				</div>
			</div>
		</div>
		<div className="container-sm border">
			<form onSubmit={ingresarEstudiante2}>
				<div className ="form-group">
					<label for="id">ID</label>
					<input value = {input.id} type="text" className="form-control" id="id" name="id" readonly="readonly" />
				</div>

				<div className="form-group">
					<label for="rut">RUT:</label>
					<input value = {input.rut} onChange={changeRutHandler} type="text" className="form-control" id="rut" name="rut" required/>
				</div>
				<div className="form-group">
					<label for="apellidos">Apellidos:</label>
					<input value = {input.lastnames} onChange={changeApellidoHandler} type="text" className="form-control" id="apellidos" name="lastnames" required/>
				</div>
				<div className="form-group">
					<label for="nombres">Nombres:</label>
					<input value = {input.names} onChange={changeNombresHandler} type="text" className="form-control" id="nombres" name="names" required/>
				</div>
				<div className="form-group">
					<label for="fecha_nacimiento">Fecha de Nacimiento:</label>
					<input value = {input.date_of_birth} onChange={changeFechaNacimientoHandler}  type="date" className="form-control" id="fecha_nacimiento" name="date_of_birth" required />
				</div>
				<div className="form-group">
					<label for="tipo_colegio">Tipo de Colegio:</label>
					<select value={input.type_school} onChange={changeTipoColegioHandler} className="form-control" id="tipo_colegio" name="type_school">
                        <option value="Municipal">Municipal</option>
                        <option value="Subvencionado">Subvencionado</option>
                        <option value="Privado">Privado</option>
                    </select>
				</div>
				<div className="form-group">
					<label for="nombre_colegio">Nombre del Colegio:</label>
					<input value = {input.school_name} onChange={changeNombreColegioHandler}  type="text" className="form-control" id="nombre_colegio" name="school_name" required/>
				</div>
				<div className="form-group">
					<label for="ano_egreso">Año de Egreso:</label>
					<input value = {input.year_of_graduation} onChange={changeAnioEgresoIDHandler} type="number" className="form-control" id="ano_egreso" name="year_of_graduation"
						   required min="1920" max="2023"/>
				</div>
				<button type="submit" className="btn btn-primary">
                        {estudianteParaEditar ? "Guardar cambios" : "Ingresar"}
                </button>
			</form>
		    </div>
        </div>
    )
}
    export default AgregarEstudianteComponent;
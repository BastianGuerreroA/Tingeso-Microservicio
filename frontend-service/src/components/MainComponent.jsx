import React from "react";
import { useNavigate } from "react-router-dom";
import "../styles/main.css";
import "../styles/listas.css";
import "../styles/subir_excel.css";
import "../styles/navbar.css";
import "../styles/agregar.css";
import "../styles/navbar.css";
import HeaderComponent from "./Headers/HeaderComponent";
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome'
import { faGraduationCap , faCoins , faUserPlus, faClipboardList , faFileArrowUp , faTrashCan} from '@fortawesome/free-solid-svg-icons'

function MainComponents() {
    const navigate = useNavigate();
    const handleClickListaEstudiantes = () => {
        navigate("/lista_estudiantes");
    };
    const handleClickGenerarCuotas = () => {
        navigate("/generar_cuotas");
    };
    const handleClickAñadirStudent = () => {
        navigate("/agregar_estudiante");
    };

    const handleClickSubirPuntaje = () => {
        navigate("/subir_puntaje");
    }

    const handleClickPlantilla = () => {
        navigate("/reporte");
    }


    return (
        <div>
            <HeaderComponent></HeaderComponent>
            <div className="container_main">
                <div className="card" onClick={handleClickListaEstudiantes}>
                <FontAwesomeIcon icon={faGraduationCap} beatFade size="2xl" fa-10x />
                    <h2>Listado de Estudiantes</h2>
                </div>
                <div className="card" onClick={handleClickAñadirStudent}>
                <FontAwesomeIcon icon={faUserPlus} size="2xl" />
                    <h2>Añadir Estudiante</h2>
                </div>
                <div className="card" onClick={handleClickGenerarCuotas}>
                <FontAwesomeIcon icon={faCoins} size="2xl" />
                    <h2>Generar Pagos</h2>
                </div>
                <div className="card" onClick={handleClickPlantilla}>
                <FontAwesomeIcon icon={faClipboardList} size="2xl" />
                    <h2>Mostrar Planilla</h2>
                </div>
                <div className="card" onClick={handleClickSubirPuntaje}>
                <FontAwesomeIcon icon={faFileArrowUp} size="2xl" />
                    <h2>Subir Puntajes</h2>
                </div>
                <div className="card" >
                <FontAwesomeIcon icon={faTrashCan} size="2xl" />
                    <h2>Eliminar Todo</h2>
                </div>
            </div>
            
        </div>
    );
}

export default MainComponents;

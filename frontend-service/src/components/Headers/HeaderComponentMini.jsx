import React from 'react'
import { useNavigate } from 'react-router-dom';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome'
import {faUserGraduate } from '@fortawesome/free-solid-svg-icons'

function HeaderComponentMini() {
    const navigate = useNavigate();
    const handleClick = () => {
        navigate("/");
    }
    return (
        <div>
            <nav class="navbar bg-dark navbar-expand-lg bg-body-tertiary" data-bs-theme="dark">
                <div class="container-fluid">
                    <FontAwesomeIcon icon={faUserGraduate} bounce size="2xl" style={{color: "#e0e0e0", marginRight:"10px"}} />
                    <a class="navbar-brand" onClick={() => handleClick()}>TopEducation</a>
                    <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarNavAltMarkup" aria-controls="navbarNavAltMarkup" aria-expanded="false" aria-label="Toggle navigation">
                    <span class="navbar-toggler-icon"></span>
                    </button>
                    <div class="collapse navbar-collapse" id="navbarNavAltMarkup">
                    <div class="navbar-nav">
                        <a class="nav-link active" aria-current="page" href="#" onClick={() => handleClick()}>Home</a>
                        <a class="nav-link" href="/lista_estudiantes">Estudiantes</a>
                        <a class="nav-link" href="/agregar_estudiante">AgregarEstudiante</a>
                        <a class="nav-link" href="/generar_cuotas">Pagos</a>
                        <a class="nav-link" href="/reporte">Reportes</a>
                        <a class="nav-link" href="/subir_puntaje">Subir Puntajes</a>
                    </div>
                    </div>
                </div>
            </nav>
        </div>
    )
}

export default HeaderComponentMini

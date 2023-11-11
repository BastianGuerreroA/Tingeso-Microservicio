import React, { useState } from 'react';
import PuntajeService from '../services/PuntajeService';
import HeaderComponent from './Headers/HeaderComponentMini';

function SubirArchivoComponent() {
    const [mensaje, setMensaje] = useState('');

    const handleFileUpload = (event) => {
        event.preventDefault();
        const file = event.target.file.files[0];
        
        if (!file) {
            setMensaje('Por favor, seleccione un archivo antes de cargarlo.');
            return;
        }

        const data = new FormData();
        data.append('file', file);

        PuntajeService.uploadFile(data)
            .then((res) => {
                setMensaje(res.data.message);
                setMensaje('¡Archivo cargado correctamente!');
                event.target.reset(); // Limpia el formulario después de enviar el archivo
            })
            .catch((error) => {
                console.error('Error al cargar el archivo:', error);
                setMensaje('Error al cargar el archivo.');
            });
    };

    return (
        <div className="general">
            <HeaderComponent />
            <br />
            <div>
                <div className="container">
                <a href="/info_archivo" class="btn btn-outline-info mt-2 float-end">Ver el último CSV cargado</a>

                    <h1><b>Cargar el archivo de exámenes</b></h1>
                    <form onSubmit={handleFileUpload}>
                        <br />
                        <input type="file" className="form-control" name="file" accept=".csv" />
                        <br />
                        <input className="btn btn-primary" type="submit" value="Cargar el archivo a la Base de Datos" />
                    </form>
                    <br />
                    <u><b><h2>{mensaje}</h2></b></u>
                </div>
            </div>
            <br />
            <hr />
            <div className="form1" style={{ backgroundColor: '#2b3035', color: "white" }}>
                <h5><b>Recuerde que debe cargar un archivo Excel de extensión .csv!</b></h5>
            </div>
        </div>
    );
}

export default SubirArchivoComponent;

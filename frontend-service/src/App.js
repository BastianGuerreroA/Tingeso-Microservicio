import React from "react";
import "./App.css";
import { BrowserRouter as Router, Route, Routes } from "react-router-dom";
import MainComponent from "./components/MainComponent";
import AgregarEstudianteComponent from "./components/AgregarEstudianteComponent";
import ListadoEstudianteComponent from "./components/ListadoEstudianteComponent";
import GenerarCuotasComponent from "./components/GenerarCuotasComponent";
import GenerarPagos from "./components/GenerarPagos";
import ListadoPagosComponent from "./components/ListadoPagosComponent";
import SubirArchivoComponent from "./components/SubirArchivoComponent";
import ListaReporteComponent from "./components/ListaReporteComponent";
import ListaArchivo from "./components/ListaArchivo";

function App() {
    return (
        <div>
            <Router>
                <Routes>
                    <Route path="/" element={<MainComponent />} />
                    <Route path="/agregar_estudiante" element={<AgregarEstudianteComponent />} />
                    <Route path="/lista_estudiantes" element={<ListadoEstudianteComponent />} />
                    <Route path="/generar_cuotas" element={<GenerarCuotasComponent />} />
                    <Route path="/generar_pagos" element={<GenerarPagos />} />
                    <Route path="/lista_pagos/:id" element={<ListadoPagosComponent />} />
                    <Route path="/subir_puntaje" element={<SubirArchivoComponent />} />
                    <Route path="/reporte" element={<ListaReporteComponent />} />
                    <Route path="/info_archivo" element={<ListaArchivo />} />

                </Routes>
                
            </Router>
        </div>
        
    );
}

export default App;

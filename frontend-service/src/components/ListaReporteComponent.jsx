import React, { useState, useEffect } from "react";
import PuntajeService from '../services/PuntajeService'
import HeaderComponent from './Headers/HeaderComponentMini'
import { useNavigate } from "react-router-dom";

function ListaReporteComponent() {
    const navigate = useNavigate();
    const [reporteEntity, setReporteEntity] = useState([]);
    //const [input, setInput] = useState(initialState);

    useEffect(() => {
        PuntajeService.obtenerReporte().then((res) => {
            const ReporteArray = Array.isArray(res.data) ? res.data : [];
            setReporteEntity(ReporteArray);
            console.log(ReporteArray);
        });
    }, []);

    return(
        <div className="general">
            <HeaderComponent/>
            <br></br><br></br>
            <div>
                <div class ="container border">
                    <h2 className="text-center">Reporte</h2>
                    <br></br>
                    <div className = "row">
                        <table class="table table-hover">
                            <thead>
                            <tr>
                                <th scope="col">RUT</th>
                                <th scope="col">Nombre</th>
                                <th scope="col">Examenes Rendidos</th>
                                <th scope="col">Promedio Examenes</th>
                                <th scope="col">Monto Arancel</th>
                                <th scope="col">Tipo Pago</th>
                                <th scope="col">Nro. Cuotas</th>
                                <th scope="col">Cuotas Pagadas</th>
                                <th scope="col">Monto Total Pagado</th>
                                <th scope="col">Fecha último pago</th>
                                <th scope="col">Por Pagar</th>
                                <th scope="col">Nro. Cuotas Atrasadas</th>
                            </tr>
                            </thead>

                            <tbody>
                            {
                                reporteEntity.map(
                                    reporte =>
                                        <tr key = {reporte.id}>
                                            <td>{reporte.rut}</td>
                                            <td>{reporte.nombreEstudiante}</td>
                                            <td>{reporte.examenesRendidos}</td>
                                            <td>{reporte.promedioExamenes}</td>
                                            <td>{reporte.montoTotalArancel}</td>
                                            <td>{reporte.tipoPago}</td>
                                            <td>{reporte.numeroCuotas}</td>
                                            <td>{reporte.cuotasPagadas}</td>
                                            <td>{reporte.montoTotalPagado}</td>
                                            <td>{reporte.fecha_ultimopago ? reporte.fecha_ultimopago.split("T")[0] : ""}</td>
                                            <td>{reporte.montoTotalPendiente}</td>
                                            <td>{reporte.cuotasRetraso}</td>
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

export default ListaReporteComponent
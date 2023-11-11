import React, { useState, useEffect } from "react";
import PagoService from "../services/PagoService";
import HeaderComponent from './Headers/HeaderComponentMini'
import { useNavigate } from "react-router-dom";
import { useLocation } from "react-router-dom";

function ListadoPagosComponent() {
    const navigate = useNavigate();
    const [pagosStudent, setPagosStudent] = useState([]);

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
        cantidadCuotas: "",
    };

    const [input, setInput] = useState(estudianteParaEditar || initialState);
    

    useEffect(() => {
        PagoService.obtenerPagos(input.id).then((res) => {
            const pagosArray = Array.isArray(res.data) ? res.data : [];
            setPagosStudent(pagosArray);
        });
    }, []);

    const pagarPago = (id) => {
        PagoService.pagarPago(id).then((res) => {
            console.log(res);
            if (res.data === "Pagado") {
                alert("Pago realizado");
                window.location.reload();
            }
            else {
                alert("Pago no realizado!!. No estas en la fecha de pago");
            }
        } );
    }


    return(
        <div className="general">
            <HeaderComponent/>
            <br></br><br></br>
            <div>
                <div class ="container border">
                    <h2 className="text-center">Lista de pagos de {input.names} {input.lastnames}</h2>
                    <div className = "row">
                    <div className="col text-right">
					    <a href="/generar_cuotas" className="btn btn-secondary mt-2 float-end">Regresar</a>
				    </div>
                        <table class="table table-hover">
                            <thead>
                            <tr>
                                <th scope="col">ID</th>
                                <th scope="col">RUT</th>
                                <th scope="col">TIPO PAGO</th>
                                <th scope="col">DESCUENTO</th>
                                <th scope="col">INTERES</th>
                                <th scope="col">ESTADO</th>
                                <th scope="col">FECHA VENCIMIENTO</th>
                                <th scope="col">FECHA DE PAGO</th>
                                <th scope="col">MONTO</th>
                                <th scope="col">Acción</th>
                            </tr>
                            </thead>

                            <tbody>
                            {
                                pagosStudent.map(
                                    pago =>
                                        <tr key = {pago.id}>
                                            <td>{pago.id}</td>
                                            <td>{pago.rut}</td>
                                            <td>{pago.tipo_pago}</td>
                                            <td>{pago.descuento}</td>
                                            <td>{pago.interes}</td>
                                            <td>{pago.estado}</td>
                                            <td>{pago.fecha_vencimiento ? pago.fecha_vencimiento.split("T")[0] : ""}</td>
                                            <td>{pago.fecha_pago ? pago.fecha_pago.split("T")[0] : ""}</td>
                                            <td>{pago.monto}</td>
                                            <td>
                                                <button className="btn btn-info" onClick={() => pagarPago(pago.id)}>Pagar</button>
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

export default ListadoPagosComponent
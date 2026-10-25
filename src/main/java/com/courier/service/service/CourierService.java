package com.courier.service.service;

import com.courier.service.dto.CourierRequest;
import com.courier.service.dto.CourierResponse;
import com.courier.service.enums.EstadoCourier;
import com.courier.service.model.Courier;

import java.util.List;
import java.util.UUID;

/**
 * Define las operaciones de servicio relacionadas con la gestión
 * de Courieres.
 * <p>
 * Proporciona métodos para registrar Courieres, consultar un
 * Courier por su identificador y obtener un listado de Courieres
 * filtrado por su estado.
 * </p>
 *
 * @author TheMiggaCool
 * @version 1.0
 * @see Courier
 * @see CourierRequest
 * @see CourierResponse
 * @see EstadoCourier
 */
public interface CourierService {

    /**
     * Registra un nuevo Courier en el sistema.
     *
     * @param courierRequest objeto que contiene los datos necesarios
     *                          para registrar al Courier.
     * @return un objeto {@link CourierResponse} con la información
     *         del Courier creado.
     */
    public CourierResponse create(CourierRequest courierRequest);

    /**
     * Busca un Courier por su identificador único.
     *
     * @param id identificador UUID del Courier que se desea consultar.
     * @return un objeto {@link CourierResponse} con la información
     *         del Courier encontrado.
     */
    public CourierResponse findById(UUID id);

    /**
     * Obtiene una lista de Courieres, opcionalmente filtrada por su estado.
     * <p>
     * Si se proporciona un estado, retorna los Courieres que coinciden
     * con dicho estado. Si el parámetro es {@code null}, retorna todos
     * los Courieres registrados en el sistema, independientemente
     * de su estado.
     * </p>
     *
     * @param estadCourier estado por el cual se desea filtrar la búsqueda;
     *                         si es {@code null}, no se aplica ningún filtro
     *                         por estado.
     * @return una lista de objetos {@link CourierResponse} que cumplen
     *         con el criterio de búsqueda, o todos los Courieres si
     *         {@code estad Courier} es {@code null}. La lista estará vacía
     *         si no existen Courieres que coincidan con el criterio.
     */
    public List <CourierResponse> findAll(EstadoCourier estadCourier);

}
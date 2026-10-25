package com.courier.service.enums;
import com.courier.service.model.Courier;
/**
 * Define los posibles estados de un repartidor dentro del sistema.
 * <p>
 * Este enumerado permite identificar si un repartidor se encuentra
 * habilitado para realizar sus actividades o si está inactivo.
 * </p>
 *
 * @author TheMiggaCool
 * @version 1.0
 * @see Courier
 */
public enum EstadoCourier {
    /**
     * Indica que el courier/repartidor se encuentra activo y habilitado
     * para realizar sus actividades de reparto.
     */
    ACTIVO,
    /**
     * Indica que el courier/repartidor se encuentra inactivo y no está
     * habilitado para realizar sus actividades de reparto.
     */
    INACTIVO
}
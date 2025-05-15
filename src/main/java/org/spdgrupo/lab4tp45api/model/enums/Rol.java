package org.spdgrupo.lab4tp45api.model.enums;

import com.fasterxml.jackson.annotation.JsonCreator;

public enum Rol {
    ADMIN,
    CLIENTE;

    @JsonCreator
    public static Rol fromString(String value) {
        for (Rol rol : Rol.values()) {
            if (rol.name().equalsIgnoreCase(value)) {
                return rol;
            }
        }
        throw new IllegalArgumentException("Rol inválido: " + value + ". Los valores permitidos son: ADMIN, CLIENTE");
    }
}

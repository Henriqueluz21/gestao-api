package com.gestao.api.excepiton;

public class EstoqueInsuficienteException extends RuntimeException {

    public EstoqueInsuficienteException(String message) {
        super(message);
    }
}
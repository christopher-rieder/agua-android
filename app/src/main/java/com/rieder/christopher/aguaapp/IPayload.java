package com.rieder.christopher.aguaapp;

import com.rieder.christopher.aguaapp.domain.Producto;
import com.rieder.christopher.aguaapp.domain.TemplateRecorrido;

import java.util.ArrayList;


interface IPayload {
    void payloadClientes(TemplateRecorrido template, ArrayList<Producto> productos);
}
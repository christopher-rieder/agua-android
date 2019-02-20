package com.rieder.christopher.aguaapp;

import com.rieder.christopher.aguaapp.DomainClasses.Producto;
import com.rieder.christopher.aguaapp.DomainClasses.TemplateRecorrido;

import java.util.ArrayList;


interface IPayload {
    void payloadClientes(TemplateRecorrido template, ArrayList<Producto> productos);
}
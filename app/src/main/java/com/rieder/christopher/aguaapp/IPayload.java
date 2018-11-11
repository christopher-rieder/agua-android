package com.rieder.christopher.aguaapp;

import com.rieder.christopher.aguaapp.DomainClasses.Producto;
import com.rieder.christopher.aguaapp.DomainClasses.TemplateRecorrido;


interface IPayload {
    void payloadClientes(TemplateRecorrido template, Producto[] productos);
}
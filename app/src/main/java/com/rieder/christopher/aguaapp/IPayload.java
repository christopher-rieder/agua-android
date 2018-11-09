package com.rieder.christopher.aguaapp;

import com.rieder.christopher.aguaapp.DomainClasses.Cliente;

import java.util.ArrayList;

interface IPayload {
    void payloadClientes(ArrayList<Cliente> clientes);
}
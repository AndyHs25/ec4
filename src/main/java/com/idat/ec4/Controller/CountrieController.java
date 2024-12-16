package com.idat.ec4.Controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.idat.ec4.Entity.Countrie;
import com.idat.ec4.Repository.CountrieRepository;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Path("/countries")
public class CountrieController {

    @Autowired
    private CountrieRepository countrieRepository;
    private ObjectMapper objectMapper = new ObjectMapper();

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getCountries() {
        try {
            List<Countrie> movies = countrieRepository.findAll();
            String json = objectMapper.writeValueAsString(movies);
            return Response.ok(json, MediaType.APPLICATION_JSON)
                    .build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Error al convertir a JSON")
                    .build();
        }
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/{name}")
    public Response getCountryByName(@PathParam("name") String name) {
        if (countrieRepository.findByName(name) != null) {
            return Response.ok(
                            countrieRepository.findByName(name),MediaType.APPLICATION_JSON)
                    .build();
        } else {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("Pais no encontrada").build();
        }
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response createCountrie(Countrie countrie) {
        if (countrieRepository.findByName(countrie.getName()) != null) {
            return Response.status(Response.Status.CONFLICT)
                    .entity("El pais ya existe").build();
        } else {
            countrieRepository.save(countrie);
            return Response.status(Response.Status.CREATED)
                    .entity("Pais Creada").build();
        }
    }

    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/{name}")
    public Response updateMovie(@PathParam("name") String name, Countrie countrie) {
        try {
            Countrie updateCountrie = countrieRepository.findByName(name);
            if (updateCountrie != null) {
                updateCountrie.setName(countrie.getName());
                updateCountrie.setContinent(countrie.getContinent());
                updateCountrie.setLenguage(countrie.getLenguage());
                countrieRepository.save(updateCountrie);
                return Response.status(Response.Status.OK)
                        .entity("Pais actualizado correctamente")
                        .type(MediaType.APPLICATION_JSON_TYPE)
                        .build();
            }
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("Pais no encontrado").build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Error al procesar solicitud")
                    .build();
        }
    }

    @DELETE
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/{name}")
    public Response deleteMovie(@PathParam("name") String name) {
        Countrie countrie = countrieRepository.findByName(name);
        if (countrie != null) {
            countrieRepository.delete(countrie);
            String responseMessage = "Pais eliminado correctamente";
            return Response.status(Response.Status.OK)
                    .entity(responseMessage)
                    .type(MediaType.APPLICATION_JSON)
                    .build();
        }
        return Response.status(Response.Status.NOT_FOUND)
                .entity("Pais no encontrado")
                .type(MediaType.APPLICATION_JSON)
                .build();
    }



}

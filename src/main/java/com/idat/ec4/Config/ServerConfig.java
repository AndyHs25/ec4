package com.idat.ec4.Config;

import com.idat.ec4.Controller.CountrieController;
import com.idat.ec4.Security.ApiKeyAuthFilter;
import org.glassfish.jersey.server.ResourceConfig;
import org.springframework.context.annotation.Configuration;

import java.rmi.registry.Registry;
@Configuration
public class ServerConfig extends ResourceConfig {

    public ServerConfig(){
        register(CountrieController.class);
        register(ApiKeyAuthFilter.class);
    }

}

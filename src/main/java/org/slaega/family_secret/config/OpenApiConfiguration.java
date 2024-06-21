package org.slaega.family_secret.config;

import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.servers.Server;

//@Configuration
public class OpenApiConfiguration {

    //@Bean
    public OpenAPI defineOpenApi() {
       Server server = new Server();
       server.setUrl("http://localhost:3000");
       server.setDescription("Development");

       Contact myContact = new Contact();
       myContact.setName("Séba Gédeon");
       myContact.setEmail("smatsoula@gmail.com");

       Info information = new Info()
               .title("Family Secret Management System API")
               .version("1.0")
               .description("This API exposes endpoints to manage employees.")
               .contact(myContact);
       return new OpenAPI().info(information).servers(List.of(server));
   }
}

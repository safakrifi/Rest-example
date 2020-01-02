
package com.example.springbootrestserver.controller;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import io.swagger.annotations.ApiOperation;
@Controller
public class DefaultController {    
    private static final Logger logger = LoggerFactory.getLogger(DefaultController.class);  
    @ApiOperation(value = "Receive Ok server Response ", response = Iterable.class)
    @GetMapping(value = "/")
    public ResponseEntity<String> pong() {
        logger.info("Démarrage des services OK .....");
        return new ResponseEntity<String>("Réponse du serveur: "+HttpStatus.OK.name(), HttpStatus.OK);
    }
}
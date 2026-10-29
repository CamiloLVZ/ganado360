/*package com.ganado.reproduccion.client;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AnimalClient {

    private final RestTemplate restTemplate;

    public boolean existeAnimal(UUID id) {
        String url = "http://localhost:8081/api/animales/" + id + "/existe";
        return restTemplate.getForObject(url, Boolean.class);
    }
}
*/
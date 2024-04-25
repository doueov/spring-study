package com.example.mvc;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.util.List;

class KanyeQuote {
    @JsonProperty("quote")
    private String quote;
}

class GitHubUser {
    @JsonProperty("login")
    private String login;

    @JsonProperty("id")
    private int id;

    @JsonProperty("site_admin")
    private boolean siteAdmin;

    // ...
}

@RestController
public class RestTemplateDemoController {
    @GetMapping(value="/kanye_quote1",
        produces = MediaType.APPLICATION_JSON_VALUE
    )
    public String kanyeQuote1() {
        RestTemplate restTemplate = new RestTemplate();
        RequestEntity<Void> requestEntity = new RequestEntity<>(
            null, null, HttpMethod.GET,
            URI.create("http://api.kanye.rest")
        );
        ResponseEntity<String> response = restTemplate.exchange(requestEntity, String.class);
        String responseBody = response.getBody();
        return responseBody;
    }

    @GetMapping("/kanye_quote2")
    public KanyeQuote kanyeQuote2() {
        RestTemplate restTemplate = new RestTemplate();
        RequestEntity<Void> requestEntity = new RequestEntity<>(
            null, null, HttpMethod.GET,
            URI.create("http://api.kanye.rest")
        );
        ResponseEntity<KanyeQuote> response = restTemplate.exchange(requestEntity, KanyeQuote.class);
        KanyeQuote responseBody = response.getBody();
        return responseBody;
    }

    @GetMapping(value = "/github/{user}",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public String githubUser(@PathVariable("user") String user) {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(List.of(MediaType.APPLICATION_JSON));
        // headers.setContentType(MediaType.APPLICATION_JSON);
        RequestEntity<Void> requestEntity = new RequestEntity<>(
            null,
            headers,
            HttpMethod.GET,
            URI.create("https://api.github.com/users/" + user)
        );
        ResponseEntity<String> response = restTemplate.exchange(requestEntity, String.class);
        String responseBody = response.getBody();

        return responseBody;
    }

    

}

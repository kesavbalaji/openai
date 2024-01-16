package com.prompt.openai.serviceImpl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;


@Service
public class PromptServiceImpl {

    private final static Logger logger = LoggerFactory.getLogger(PromptServiceImpl.class);

//    @Value("${openai.apiKey}")
//    private String apiKey;

    @Autowired
    RestTemplate restTemplate;
//
//    public PromptServiceImpl(RestTemplate restTemplate) {
//        this.restTemplate = restTemplate;
//    }

    private final WebClient webClient;
    private final String openaiApiKey;

    public PromptServiceImpl(WebClient.Builder webClientBuilder, @Value("${openai.api-key}") String openaiApiKey) {
        this.webClient = webClientBuilder.baseUrl("https://api.openai.com/v1").build();
        this.openaiApiKey = openaiApiKey;
    }

    public ResponseEntity<String> generateResponse(String prompt) {
        String input = "{\"prompt\": \"" + "Machine learning" + "\", \"max_tokens\": 100}";
        String authorization = webClient.post()
                .uri("/engines/davinci-codex/completions")
                .header("Authorization", "Bearer " + openaiApiKey)
                .bodyValue(input)
                .retrieve()
                .bodyToMono(String.class)
                .block();
        return new ResponseEntity<>(authorization, HttpStatus.OK);
    }

    public ResponseEntity<String> openAIApiCall(String prompt) {
        String openaiUrl = "https://api.openai.com/v1/engines/davinci-codex/completions";
        String requestBody = "{\"prompt\": \"" + "Machine learning" + "\", \"max_tokens\": 100}";
        String response = restTemplate.postForObject(openaiUrl, requestBody, String.class);
        return new ResponseEntity<String>(response, HttpStatus.OK);
    }
}

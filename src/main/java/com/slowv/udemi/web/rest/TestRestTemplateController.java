package com.slowv.udemi.web.rest;

import com.slowv.udemi.channel.ResourceServiceChannel;
import com.slowv.udemi.service.dto.PostDto;
import lombok.RequiredArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@RestController
@RequestMapping("/test-rest-template")
@RequiredArgsConstructor
public class TestRestTemplateController {

    private final RestTemplate restTemplate;
    private final ResourceServiceChannel resourceServiceChannel;

    @SuppressWarnings("unchecked")
    @GetMapping
    public List<PostDto> getPosts() {
        final var url = "https://jsonplaceholder.typicode.com/posts";
        final var responseEntity = restTemplate.getForEntity(url, List.class);
        return (List<PostDto>) responseEntity.getBody();
    }

    @GetMapping("/with-generic-type")
    public List<PostDto> getPosts2() {
        final var url = "https://jsonplaceholder.typicode.com/posts";
        final var responseEntity = restTemplate.exchange(url, HttpMethod.GET, new HttpEntity<>(null), new ParameterizedTypeReference<List<PostDto>>() {
        });
        return responseEntity.getBody();
    }

    @PostMapping
    public PostDto createPost(@RequestBody PostDto postDto) {
        final var url = "https://jsonplaceholder.typicode.com/posts";
        final var responseEntity = restTemplate.postForEntity(url, postDto, PostDto.class);
        return responseEntity.getBody();
    }

    @PutMapping
    public PostDto updatePost(@RequestBody PostDto postDto) {
        final var url = "https://jsonplaceholder.typicode.com/posts/1";

        final var headers = new HttpHeaders();
        headers.add("Content-Type", "application/json");
        final var httpEntity = new HttpEntity<>(postDto, headers);

        final var responseEntity = restTemplate.exchange(url, HttpMethod.PUT, httpEntity, PostDto.class);
        return responseEntity.getBody();
    }

    @DeleteMapping
    public String updatePost() {
        final var url = "https://jsonplaceholder.typicode.com/posts/1";
        restTemplate.exchange(url, HttpMethod.DELETE, new HttpEntity<>(null), Void.class);
        return "Delete successfully!!!";
    }

    @GetMapping("/get-resource")
    public String getResource() {
        return resourceServiceChannel.getResource();
    }
}


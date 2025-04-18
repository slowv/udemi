package com.slowv.udemi.web.rest;

import com.slowv.udemi.service.dto.JobDetailDto;
import com.slowv.udemi.service.dto.request.CreateJobRequest;
import com.slowv.udemi.service.dto.request.StartJobRequest;
import com.slowv.udemi.service.dto.response.Response;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/jobs")
public interface JobSchedulerController {

    @PostMapping
    Response<JobDetailDto> createJob(@RequestBody @Valid CreateJobRequest request);

    @PostMapping("/start")
    Response<String> startJob(@RequestBody @Valid StartJobRequest request);
}

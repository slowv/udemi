package com.slowv.udemi.web.rest.impl;

import com.slowv.udemi.service.JobService;
import com.slowv.udemi.service.dto.JobDetailDto;
import com.slowv.udemi.service.dto.request.CreateJobRequest;
import com.slowv.udemi.service.dto.request.JobConfigRequest;
import com.slowv.udemi.service.dto.request.StartJobRequest;
import com.slowv.udemi.service.dto.response.Response;
import com.slowv.udemi.web.rest.JobSchedulerController;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class JobSchedulerControllerImpl implements JobSchedulerController {

    private final JobService jobService;

    @Override
    public Response<JobDetailDto> createJob(final CreateJobRequest request) {
        return Response.created(jobService.create(request));
    }

    @Override
    public Response<String> startJob(final StartJobRequest request) {
        return Response.ok(jobService.start(request));
    }

    @Override
    public Response<String> pauseJob(final List<JobConfigRequest> config) {
        return Response.ok(jobService.pause(config));
    }

    @Override
    public Response<String> resumeJob(final List<JobConfigRequest> config) {
        return Response.ok(jobService.resume(config));
    }

    @Override
    public Response<String> deleteJob(final List<JobConfigRequest> config) {
        return Response.ok(jobService.delete(config));
    }

    @Override
    public Response<List<JobDetailDto>> getJobStatus(final List<JobConfigRequest> configs) {
        return Response.ok(jobService.getStatus(configs));
    }
}

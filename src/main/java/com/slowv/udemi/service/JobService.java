package com.slowv.udemi.service;

import com.slowv.udemi.service.dto.JobDetailDto;
import com.slowv.udemi.service.dto.request.CreateJobRequest;
import com.slowv.udemi.service.dto.request.JobConfigRequest;
import com.slowv.udemi.service.dto.request.StartJobRequest;

import java.util.List;

public interface JobService {
    JobDetailDto create(CreateJobRequest request);

    String start(StartJobRequest request);

    String pause(List<JobConfigRequest> keys);

    String resume(List<JobConfigRequest> config);

    String delete(List<JobConfigRequest> config);

    List<JobDetailDto> getStatus(List<JobConfigRequest> configs);
}

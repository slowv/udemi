package com.slowv.udemi.service;

import com.slowv.udemi.service.dto.JobDetailDto;
import com.slowv.udemi.service.dto.request.CreateJobRequest;
import com.slowv.udemi.service.dto.request.StartJobRequest;

public interface JobService {
    JobDetailDto create(CreateJobRequest request);

    String start(StartJobRequest request);
}

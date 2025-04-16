package com.slowv.udemi.channel;

import feign.RequestInterceptor;

public interface ResourceServiceChannel extends RequestInterceptor {
    String getResource();
}

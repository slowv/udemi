package com.slowv.udemi.component;


import org.springframework.web.multipart.MultipartFile;

public class MultipartFileContextHolder {
    private static final ThreadLocal<MultipartFile> context = new ThreadLocal<>();

    public static void set(MultipartFile file) {
        context.set(file);
    }

    public static MultipartFile get() {
        return context.get();
    }

    public static void clear() {
        context.remove();
    }
}

package com.slowv.udemi.config;


import com.slowv.udemi.config.elasticsearch.CustomElasticsearchMappingContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.convert.ReadingConverter;
import org.springframework.data.convert.WritingConverter;
import org.springframework.data.elasticsearch.config.ElasticsearchConfigurationSupport;
import org.springframework.data.elasticsearch.core.convert.ElasticsearchCustomConversions;
import org.springframework.data.elasticsearch.core.mapping.SimpleElasticsearchMappingContext;
import org.springframework.lang.NonNull;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.List;

@Configuration
public class ElasticsearchConfiguration extends ElasticsearchConfigurationSupport {

    @Bean
    @NonNull
    @Override
    public ElasticsearchCustomConversions elasticsearchCustomConversions() {
        return new ElasticsearchCustomConversions(
                List.of(
                        new ZonedDateTimeWritingConverter(),
                        new ZonedDateTimeReadingConverter(),
                        new LocalDateTimeWritingConverter(),
                        new LocalDateTimeReadingConverter(),
                        new LocalDateWritingConverter(),
                        new LocalDateReadingConverter(),
                        new LocalDateTimeToLongReadingConverter(),
                        new LocalDateTimeToLongWritingConverter()
                )
        );
    }

    @WritingConverter
    static class ZonedDateTimeWritingConverter implements Converter<ZonedDateTime, String> {
        @Override
        public String convert(ZonedDateTime source) {
            return source.toInstant().toString();
        }
    }

    @ReadingConverter
    static class ZonedDateTimeReadingConverter implements Converter<String, ZonedDateTime> {
        @Override
        public ZonedDateTime convert(@NonNull String source) {
            return Instant.parse(source).atZone(ZoneId.systemDefault());
        }
    }

    @WritingConverter
    static class LocalDateTimeToLongWritingConverter implements Converter<LocalDateTime, Long> {
        @Override
        public Long convert(LocalDateTime source) {
            return source.toInstant(ZoneOffset.UTC).toEpochMilli();
        }
    }

    @ReadingConverter
    static class LocalDateTimeToLongReadingConverter implements Converter<Long, LocalDateTime> {
        @Override
        public LocalDateTime convert(@NonNull Long source) {
            return LocalDateTime.ofInstant(Instant.ofEpochMilli(source), ZoneId.of("UTC"));
        }
    }

    @WritingConverter
    static class LocalDateTimeWritingConverter implements Converter<LocalDateTime, String> {
        @Override
        public String convert(LocalDateTime source) {
            return source.toString();
        }
    }

    @ReadingConverter
    static class LocalDateTimeReadingConverter implements Converter<String, LocalDateTime> {
        @Override
        public LocalDateTime convert(@NonNull String source) {
            return LocalDateTime.parse(source);
        }
    }

    @WritingConverter
    static class LocalDateWritingConverter implements Converter<LocalDate, String> {
        @Override
        public String convert(LocalDate source) {
            return source.toString();
        }
    }

    @ReadingConverter
    static class LocalDateReadingConverter implements Converter<String, LocalDate> {
        @Override
        public LocalDate convert(@NonNull String source) {
            return LocalDate.parse(source);
        }
    }

    @Bean
    @NonNull
    @Override
    public SimpleElasticsearchMappingContext elasticsearchMappingContext(@NonNull ElasticsearchCustomConversions elasticsearchCustomConversions) {
        CustomElasticsearchMappingContext mappingContext = new CustomElasticsearchMappingContext();
        mappingContext.setInitialEntitySet(this.getInitialEntitySet());
        mappingContext.setSimpleTypeHolder(this.elasticsearchCustomConversions().getSimpleTypeHolder());
        return mappingContext;
    }
}

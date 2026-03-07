package org.unibl.etf.configs;


import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.ReactiveRedisConnectionFactory;
import org.springframework.data.redis.core.ReactiveRedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.unibl.etf.models.dto.IncidentRedisSerializer;
import org.unibl.etf.models.dto.responses.IncidentResponse;

@Configuration
public class RedisConfig {

    @Bean
    public ReactiveRedisTemplate<String, IncidentResponse> reactiveRedisTemplate(ReactiveRedisConnectionFactory factory) {
        ObjectMapper mapper = new ObjectMapper()
                .registerModule(new com.fasterxml.jackson.datatype.jsr310.JavaTimeModule())
                .disable(com.fasterxml.jackson.databind.SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

        RedisSerializer serializer = new IncidentRedisSerializer(mapper);

        RedisSerializationContext<String, IncidentResponse> context =
                RedisSerializationContext.<String, IncidentResponse>newSerializationContext(new StringRedisSerializer())
                        .value(serializer)
                        .build();

        return new ReactiveRedisTemplate<>(factory, context);
    }
}
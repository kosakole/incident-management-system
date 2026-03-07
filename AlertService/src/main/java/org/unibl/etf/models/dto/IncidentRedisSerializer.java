package org.unibl.etf.models.dto;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.SerializationException;
import org.unibl.etf.models.dto.responses.IncidentResponse;

public class IncidentRedisSerializer implements RedisSerializer<IncidentResponse> {

    private final ObjectMapper objectMapper;

    public IncidentRedisSerializer(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @Override
    public byte[] serialize(IncidentResponse value) throws SerializationException {
        if (value == null) return new byte[0];
        try {
            return objectMapper.writeValueAsBytes(value);
        } catch (Exception e) {
            throw new SerializationException("Error writing IncidentResponse to Redis", e);
        }
    }

    @Override
    public IncidentResponse deserialize(byte[] bytes) throws SerializationException {
        if (bytes == null || bytes.length == 0) return null;
        try {
            return objectMapper.readValue(bytes, IncidentResponse.class);
        } catch (Exception e) {
            throw new SerializationException("Error reading IncidentResponse to Redis", e);
        }
    }
}

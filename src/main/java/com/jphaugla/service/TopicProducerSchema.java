package com.jphaugla.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jphaugla.domain.Transaction;
import io.confluent.kafka.schemaregistry.annotations.Schema;
import io.confluent.kafka.schemaregistry.json.JsonSchemaUtils;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.InputStream;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
@Service
@RequiredArgsConstructor
public class TopicProducerSchema {
    private static final String SCHEMA_POSTFIX_KEY = "-key.json";
    private static final String SCHEMA_POSTFIX_VALUE = "-value.json";
    private final KafkaTemplate<String, JsonNode> kafkaTemplate;
    private final ObjectMapper objectMapper;

    private final Map<String, JsonNode> topicSchemaCache = new ConcurrentHashMap<>();

    public <K, V> void send(final String topic, final String key, final V value) {

        // final JsonNode keyNode = getEnvelope(topic + SCHEMA_POSTFIX_KEY, key);
        final JsonNode valueNode = getEnvelope(topic + SCHEMA_POSTFIX_VALUE, value);
        kafkaTemplate.send(topic, key, valueNode);
    }

    private JsonNode getEnvelope(final String schemaFilePath, final Object key) {
        final JsonNode schemaNode = getOrLoadSchema(schemaFilePath);
        final JsonNode payload = objectMapper.valueToTree(key);
        return JsonSchemaUtils.envelope(schemaNode, payload);
    }

    private JsonNode getOrLoadSchema(final String schemaFilePath) {
        return topicSchemaCache.computeIfAbsent(schemaFilePath, key ->
                readFileToJsonNode(schemaFilePath));
    }

    @SneakyThrows
    private JsonNode readFileToJsonNode(final String schemaFilePath) {
        log.info("in readFileToJsonNode with schemaFilePath: " + schemaFilePath);
        InputStream jsonFile = new ClassPathResource(schemaFilePath).getInputStream();
        return objectMapper.readTree(jsonFile);
    }

}

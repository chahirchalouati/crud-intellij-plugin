package com.crcl.mongocrud.processors.impl;

import com.crcl.mongocrud.processors.RepositoryProcessor;

import java.util.HashMap;
import java.util.Map;

import static com.crcl.mongocrud.utils.OperationTypeUtils.JPA_OPERATION_REPOSITORY;
import static com.crcl.mongocrud.utils.OperationTypeUtils.MONGO_OPERATION_REPOSITORY;

public class RepositoryProcessorFactory {
    private static final Map<String, RepositoryProcessor> MAP = new HashMap<>();

    static {
        MAP.put(MONGO_OPERATION_REPOSITORY, new MongoRepositoryProcessorImpl());
        MAP.put(JPA_OPERATION_REPOSITORY, new JpaRepositoryProcessorImpl());
    }

    private RepositoryProcessorFactory() {
    }

    public static RepositoryProcessor getRepository(String repositoryName) {
        return MAP.get(repositoryName);
    }
}

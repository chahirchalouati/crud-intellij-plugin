package com.example.mongocrud.service;

import com.example.mongocrud.processors.impl.MongoRepositoryProcessorImpl;
import com.example.mongocrud.processors.RepositoryProcessor;
import com.example.mongocrud.utils.RespositoryTypeUtils;

import java.util.HashMap;
import java.util.Map;

public class RepositoryProcessorFactory {
    private RepositoryProcessorFactory() {}
    private static final Map<String, RepositoryProcessor> MAP = new HashMap<>();

    static {
        MAP.put(RespositoryTypeUtils.MONGO_REPOSITORY, new MongoRepositoryProcessorImpl());
    }

    public static RepositoryProcessor getRepository(String repositoryName) {
        return MAP.get(repositoryName);
    }
}

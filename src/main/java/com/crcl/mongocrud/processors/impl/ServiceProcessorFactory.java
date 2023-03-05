package com.crcl.mongocrud.processors.impl;

import com.crcl.mongocrud.processors.ServiceProcessor;

import java.util.HashMap;
import java.util.Map;

import static com.crcl.mongocrud.utils.OperationTypeUtils.JPA_OPERATION_SERVICE;
import static com.crcl.mongocrud.utils.OperationTypeUtils.MONGO_OPERATION_SERVICE;

public class ServiceProcessorFactory {
    private static final Map<String, ServiceProcessor> MAP = new HashMap<>();

    static {
        MAP.put(MONGO_OPERATION_SERVICE, new MongoServiceProcessorImpl());
        MAP.put(JPA_OPERATION_SERVICE, new JpaServiceProcessorImpl());
    }

    private ServiceProcessorFactory() {
    }

    public static ServiceProcessor getServiceProcessor(String serviceName) {
        return MAP.get(serviceName);
    }
}

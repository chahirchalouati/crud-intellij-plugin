package com.example.mongocrud.utils;

public class TemplateUtils {
    public static final String SETTER_TEMPLATE = "public void set${Cname}(${type} ${name}) {\n" + "this.${name} = ${name};\n" + "}";
    public static final String GETTER_TEMPLATE = "public ${type} get${Cname}() { return this.${name} ;}\n";
    private TemplateUtils() {
    }
}

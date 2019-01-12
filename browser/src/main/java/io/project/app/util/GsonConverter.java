package io.project.app.util;

import com.google.gson.GsonBuilder;

/**
 * The JSON Conversion utility
 * 
 * @author armen
 */
public class GsonConverter {
    /**
     * Gets the date format
     * 
     * @return Returns date format
     */
    private static String getDateFormat(){
        return "yyyy-MM-dd'T'HH:mm:ss.SSS";
    }
    
    /**
     * Convert the object to JSON string
     *
     * @param <T> The generic parameter
     * @param object The object to serialize
     * @return Returns serialized string
     */
    public static <T> String to(T object) {
        // create json string
        return new GsonBuilder().setPrettyPrinting().setDateFormat(getDateFormat()).enableComplexMapKeySerialization().serializeNulls().create().toJson(object);
    }
    
    /**
     * Import JSON file in to the object
     *
     * @param <T> The T parameter for input
     * @param json
     * @param classType The class type
     * @return The object
     */
    public static <T> T from(String json, Class<T> classType) {
        // return object
        return new GsonBuilder().setDateFormat(getDateFormat()).enableComplexMapKeySerialization().create().fromJson(json, classType);
    }
    
    /**
     * A fast adapter method to change types with exact same schema
     * 
     * @param <From> The source type
     * @param <To> The target type
     * @param object The object
     * @param targetType The target type
     * @return 
     */
    public static <From, To> To adapt(From object, Class<To> targetType){
        return from(to(object), targetType);
    }
}

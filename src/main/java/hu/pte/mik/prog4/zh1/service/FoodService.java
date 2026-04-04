package hu.pte.mik.prog4.zh1.service;

import hu.pte.mik.prog4.zh1.exceptions.ZH1XmlException;
import hu.pte.mik.prog4.zh1.model.Food;
import hu.pte.mik.prog4.zh1.repository.FoodRepository;
import hu.pte.mik.prog4.zh1.xml.BeforeSerialization;
import hu.pte.mik.prog4.zh1.xml.ZH1Element;
import hu.pte.mik.prog4.zh1.xml.ZH1Serializable;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;
import java.util.logging.Logger;

public class FoodService {

    private static final Logger LOGGER = Logger.getLogger(FoodService.class.toString());

    private final FoodRepository foodRepository;

    public FoodService(FoodRepository foodRepository) {
        this.foodRepository = foodRepository.getInstance();
    }

    public Food findById(Long id){
        return this.foodRepository.findById(id);
    }

    public List<Food> findAll(){
        return this.foodRepository.findAll();
    }

    public Food create(String restaurantName, String foodName, String price){
        return this.foodRepository.create(restaurantName, foodName, price);
    }

    public Food deleteById(Long id){
        return this.foodRepository.delete(id);
    }

    private String convertToXml(Object object){
        this.checkIfSerializable(object);

        try{
            StringBuilder sb = new StringBuilder("<?xml version=\"1.0\" encoding=\"UTF-8\"?>").append(System.lineSeparator());

            this.process(object, sb);

            return sb.toString();
        }
        catch(Exception e){
            throw new ZH1XmlException(e.getMessage(), e);
        }
    }

    private void checkIfSerializable(Object object){
        if (object == null){
            throw new ZH1XmlException("Object is null!");
        }

        Class<?> clazz = object.getClass();

        if(!clazz.isAnnotationPresent(ZH1Serializable.class)){
            throw new ZH1XmlException(clazz.getName() + " is not annotated with @ZH1Serializable!");
        }
    }

    private void process(Object object, StringBuilder sb) throws InvocationTargetException, IllegalAccessException {
        this.prepare(object);
        this.serialize(object, sb);
    }


    private void prepare(Object object) throws InvocationTargetException, IllegalAccessException {
        Class<?> clazz = object.getClass();

        List<Method> methods = new ArrayList<>();
        Collections.addAll(methods, clazz.getDeclaredMethods());
        this.addParentMethods(clazz, methods);

        for (Method method : methods){
            if (method.isAnnotationPresent(BeforeSerialization.class)){
                boolean isAccessible = method.canAccess(object);
                method.trySetAccessible();
                method.invoke(object);
                if(!isAccessible){
                    method.setAccessible(false);
                }
            }
        }
    }


    private void serialize(Object object, StringBuilder sb) throws IllegalAccessException, InvocationTargetException {
        Class<?> clazz = object.getClass();

        ZH1Serializable ZH1Serializable = clazz.getAnnotation(ZH1Serializable.class);
        String classKey = "".equals(ZH1Serializable.text()) ? clazz.getSimpleName().toUpperCase() : ZH1Serializable.text().toUpperCase();

        this.appendStartTag(sb, classKey);
        sb.append(System.lineSeparator());
        List<Field> fields = new ArrayList<>();
        this.addParentFields(clazz, fields);
        Collections.addAll(fields, clazz.getDeclaredFields());

        for(Field field: fields){
            if(field.isAnnotationPresent(ZH1Element.class)){
                boolean isAccessible = field.canAccess(object);
                field.trySetAccessible();
                ZH1Element ZH1Element = field.getAnnotation(ZH1Element.class);
                String key = "".equals(ZH1Element.text()) ? field.getName().toUpperCase() : ZH1Element.text().toUpperCase();
                Object value = field.get(object);

                this.appendStartTag(sb, key);
                if(value != null && value.getClass().isAnnotationPresent(ZH1Serializable.class)){
                    sb.append(System.lineSeparator());
                    this.process(value, sb);
                }
                else{
                    sb.append(value);
                }

                this.appendEndTag(sb, classKey);
                if(!isAccessible){
                    field.setAccessible(false);
                }
            }
        }
        this.appendEndTag(sb, classKey);

    }

    private void addParentMethods(Class<?> clazz, List<Method> methods){
        Class<?> superClazz = clazz.getSuperclass();
        if(superClazz.isAnnotationPresent(ZH1Serializable.class)){
            Collections.addAll(methods, superClazz.getDeclaredMethods());
            this.addParentMethods(superClazz, methods);
        }
    }

    private void appendStartTag(StringBuilder sb, String classKey){
        sb.append("<").append(classKey).append(">");
    }

    private void appendEndTag(StringBuilder sb, String classKey){
        sb.append("</").append(classKey).append(">").append(System.lineSeparator());
    }

    private void addParentFields(Class<?> clazz, List<Field> fields){
        Class<?> superClazz = clazz.getSuperclass();
        if(superClazz.isAnnotationPresent(ZH1Serializable.class)){
            Collections.addAll(fields, superClazz.getDeclaredFields());
            this.addParentFields(clazz, fields);
        }
    }
}

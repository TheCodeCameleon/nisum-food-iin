package com.nisum.foodcourt.resource.util;

import com.nisum.foodcourt.BaseModal.BaseAuditableEntity;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class ConversionUtil {

    @Autowired
    ModelMapper mapper;

    /**
     * @param entity
     * @param modelClass
     * @param <T>
     * @return <T> type class Object
     */

    public <T> T mapEntityToModel(Object entity, Class<T> modelClass) {
        return mapSourceToDestination(entity, modelClass);
    }

    /**
     * @param entityList
     * @param modelClass
     * @param <T>
     * @return <T> Type class Object List
     */

    public <T, D> List<T> mapEntityListToModelList(List<D> entityList, Class<T> modelClass) {
        return entityList.stream()
                .map(entity -> mapSourceToDestination(entity, modelClass))
                .collect(Collectors.toList());
    }

    /**
     * @param model
     * @param entityClass
     * @param <T>
     * @return <T> Type class Object List
     */

    public <T> T mapModelToEntity(Object model, Class<T> entityClass) {
        return mapSourceToDestination(model, entityClass);
    }

    /**
     * @param modelList
     * @param entityClass
     * @param <T>
     * @return <T> Type class Object List
     */

    public <T, D> List<T> mapModelListToEntityList(List<D> modelList, Class<T> entityClass) {
        return modelList.stream()
                .map(model -> mapSourceToDestination(model, entityClass))
                .collect(Collectors.toList());
    }

    public <T extends BaseAuditableEntity> T getReferentialEntity(Integer id, Class<T> entityClass) {
        try {
            T entityInstance = entityClass.getDeclaredConstructor().newInstance();
            entityInstance.setId(id);
            return entityInstance;
        } catch (ReflectiveOperationException exception) {
            throw new RuntimeException();
        }
    }

    /**
     * Here we are extracting list of ids from any type of Object
     *
     * @param listOfGenericsObject
     * @param <T>
     * @return List<Integer>
     */

    public <T> List<Integer> getIdList(List<T> listOfGenericsObject) {
        return listOfGenericsObject.stream().map(tObject -> {
            try {
                //t.getClass().getMethod("getId").getReturnType();
                return (Integer) tObject.getClass().getMethod("getId").invoke(tObject);
            } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
                throw new RuntimeException(e);
            }
        }).collect(Collectors.toList());
    }


    private <D> D mapSourceToDestination(Object source, Class<D> destination) {
        return mapper.map(source, destination);
    }

}

package br.com.todoserver.todoapp.mappers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.projection.ProjectionFactory;
import org.springframework.data.projection.SpelAwareProxyProjectionFactory;

public class ProjectionMapper {
    public static <T> T convertObject(Class<T> klass, Object object) {
        ProjectionFactory pFactory = new SpelAwareProxyProjectionFactory();
        return pFactory.createProjection(klass, object);
    }

    public static <T>List<T> convertObjectList(Class<T> klass, List<?> objectList) {
        ProjectionFactory pFactory = new SpelAwareProxyProjectionFactory();
        List<T> projectionList = new ArrayList<>();
        for (Object entity : objectList) {
            projectionList.add(pFactory.createProjection(klass, entity));
        }
        return projectionList;
    }
}

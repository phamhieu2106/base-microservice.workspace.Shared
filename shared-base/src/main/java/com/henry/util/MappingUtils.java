package com.henry.util;

import org.modelmapper.ModelMapper;

import java.util.List;
import java.util.stream.Collectors;

public class MappingUtils {

    private static final ModelMapper modelMapper = new ModelMapper();

    public static <O> O mapObject(Object sourceObject, Class<O> targetClass) {
        return modelMapper.map(sourceObject, targetClass);
    }

    public static <D> void mapObject(Object source, D des) {
        modelMapper.map(source, des);
    }

    public static <S, O> List<O> mapList(List<S> sources, Class<O> targetClass) {
        return sources.stream().map(
                source -> modelMapper.map(source, targetClass)
        ).collect(Collectors.toList());
    }
}

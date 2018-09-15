package com.ems.util;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class CommonService<I, O> {
    private ObjectMapper objectMapper = new ObjectMapper();

    public List<O> convert(List<I> inpList, Class<O> clazz) {
        return inpList.stream().map((i) -> convert(i, clazz)).collect(Collectors.toList());
    }

    public O convert(I input, Class<O> clazz) {
        return objectMapper.convertValue(input, clazz);
    }
}

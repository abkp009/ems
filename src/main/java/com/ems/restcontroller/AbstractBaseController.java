package com.ems.restcontroller;

import com.ems.util.CommonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

// F-F, E-entity
public abstract class AbstractBaseController<E, F> {

    @Autowired
    private CommonService<E, F> toFormConvertor;

    public abstract JpaRepository<E, Integer> getRepository();

    @GetMapping("/{id}")
    public E getById(@PathVariable("id") String id) {
        return getRepository().findById(Integer.valueOf(id)).get();
    }

    @GetMapping("/all")
    public List<E> getAll() {
        return getRepository().findAll();
    }
}


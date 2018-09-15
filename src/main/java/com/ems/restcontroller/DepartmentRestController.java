package com.ems.restcontroller;

import com.ems.entity.DepartmentEntity;
import com.ems.repository.DepartmentRepository;
import com.ems.response.GeneralResponse;
import com.ems.restcontroller.form.Department;
import com.ems.util.CommonService;
import com.ems.util.ValidationErrorProcessorService;
import com.splunk.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/department")
@CrossOrigin(origins = "*", methods = {RequestMethod.DELETE, RequestMethod.POST, RequestMethod.PUT,
        RequestMethod.GET})
public class DepartmentRestController extends AbstractBaseController<DepartmentEntity, Department> {
    @Autowired
    private DepartmentRepository depRepo;
    @Autowired
    private ValidationErrorProcessorService validator;

    @PostMapping
    public ResponseEntity<GeneralResponse<Department>> registerDepartment(@Valid @RequestBody Department department,
                                                                          BindingResult errors) {
        if (errors.hasErrors()) {
            throw new RuntimeException(validator.process(errors));
        }
        Service s = null;
        GeneralResponse<Department> response = new GeneralResponse<>();
        response.setResponse(new CommonService<DepartmentEntity, Department>().convert(
                depRepo.save(
                        new CommonService<Department, DepartmentEntity>().convert(department, DepartmentEntity.class)),
                Department.class));
        return ResponseEntity.ok(response);
    }

    @DeleteMapping
    public ResponseEntity<Department> deleteDepartment(@Valid @RequestBody Department department,
                                                       BindingResult errors) {
        if (errors.hasErrors()) {
            throw new RuntimeException(validator.process(errors));
        }
        DepartmentEntity entity = depRepo.findById(department.getDepartmentId())
                .orElseThrow(() -> new RuntimeException("Id doesn't exist: " + department.getDepartmentId()));
        depRepo.deleteById(department.getDepartmentId());
        return ResponseEntity.ok(new CommonService<DepartmentEntity, Department>().convert(entity, Department.class));
    }

    @GetMapping
    public ResponseEntity<List<Department>> getDepartments() {
        return ResponseEntity
                .ok(new CommonService<DepartmentEntity, Department>().convert(depRepo.findAll(), Department.class));
    }

    @DeleteMapping("/{did}")
    public ResponseEntity<Department> deleteDepartment(@PathVariable Integer did) {
        Department dept = new CommonService<DepartmentEntity, Department>().convert(
                depRepo.findById(did).orElseThrow(() -> new RuntimeException("Id doesn't exist:" + did)),
                Department.class);
        depRepo.deleteById(did);
        return ResponseEntity.ok(dept);
    }

    @Override
    public JpaRepository<DepartmentEntity, Integer> getRepository() {
        return depRepo;
    }

}

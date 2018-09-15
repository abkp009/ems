package com.ems.restcontroller;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Example;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.ems.entity.EmployeeEntity;
import com.ems.entity.FileUploadEntity;
import com.ems.form.Employee;
import com.ems.repository.EmployeeRepository;
import com.ems.repository.FileUploadRepository;
import com.ems.util.CommonService;

@RestController
@RequestMapping("/employee")
@CrossOrigin(origins = "*", methods = {RequestMethod.DELETE, RequestMethod.POST, RequestMethod.PUT,
        RequestMethod.GET})
public class EmployeeRestController {
    @Autowired
    private EmployeeRepository empRepo;
    @Autowired
    private FileUploadRepository fileRepo;
    @Autowired
    private CommonService<EmployeeEntity, Employee> svcForForm;

    @PostMapping
    public ResponseEntity<Employee> registerEmployee(@RequestBody Employee employeeRequest) {
        CommonService<Employee, EmployeeEntity> svcForEntity = new CommonService<Employee, EmployeeEntity>();
        return ResponseEntity.ok(svcForForm
                .convert(empRepo.save(svcForEntity.convert(employeeRequest, EmployeeEntity.class)), Employee.class));
    }

    @GetMapping
    public ResponseEntity<List<Employee>> getEmployees(Integer id) {
        List<EmployeeEntity> empList = null;
        if (null != id && id >= 0) {
            empList = new ArrayList<>(1);
            empList.add(empRepo.findById(id).orElse(new EmployeeEntity()));
        } else {
            empList = empRepo.findAll();
        }
        return ResponseEntity.ok(svcForForm.convert(empList, Employee.class));
    }

    @GetMapping("/{id}")
    public ResponseEntity<List<Employee>> getEmployee(@PathVariable int id) {
        return this.getEmployees(id);
    }

    @PostMapping("/search")
    public ResponseEntity<List<Employee>> searchEmployee(@RequestBody Employee employee) {
        CommonService<Employee, EmployeeEntity> svcForEntity = new CommonService<Employee, EmployeeEntity>();
        return ResponseEntity.ok(svcForForm.convert(
                empRepo.findAll(Example.of(svcForEntity.convert(employee, EmployeeEntity.class))), Employee.class));

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Employee> deleteEmployee(@PathVariable(name = "id") Integer id)
            throws CloneNotSupportedException {
        EmployeeEntity emp = new EmployeeEntity();
        emp.setId(id);
        EmployeeEntity res = (EmployeeEntity) empRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Employee Doesn't Exist"));
        empRepo.delete(emp);
        return ResponseEntity.ok(svcForForm.convert(res, Employee.class));

    }

    @DeleteMapping("/delete")
    public ResponseEntity<Employee> deleteEmployeeCritera(@RequestBody Employee employee) {
        CommonService<Employee, EmployeeEntity> svcForEntity = new CommonService<Employee, EmployeeEntity>();
        empRepo.findAll(Example.of(svcForEntity.convert(employee, EmployeeEntity.class)))
                .forEach(e -> empRepo.delete(e));
        return ResponseEntity.ok(employee);

    }

    @PostMapping(value = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Object> uploadFile(@RequestParam("file") MultipartFile file) {
        FileUploadEntity upload = new FileUploadEntity();
        upload.setFileId(new Random().nextInt(100));
        upload.setFileType(file.getContentType());
        upload.setFileName(file.getOriginalFilename());
        try {
            upload.setFileContent(file.getBytes());
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.ok(file.getOriginalFilename() + " not saved " + e.getMessage());
        }
        return ResponseEntity.ok(fileRepo.save(upload).getFileName() + " saved successfully");
    }

    @GetMapping(value = "/download/{fileId}")
    public ResponseEntity<Resource> downloadFile(@PathVariable("fileId") Integer fileId) {
        FileUploadEntity fileEntity = new FileUploadEntity();
        fileEntity.setFileId(fileId);
        fileEntity = fileRepo.findOne(Example.of(fileEntity)).orElseThrow(() -> new RuntimeException("File Not Found"));
        return ResponseEntity.ok().contentType(MediaType.parseMediaType(fileEntity.getFileType()))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + fileEntity.getFileName() + "\"")
                .body(new ByteArrayResource(fileEntity.getFileContent()));
    }
}

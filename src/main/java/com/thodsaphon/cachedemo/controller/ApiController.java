package com.thodsaphon.cachedemo.controller;

import com.thodsaphon.cachedemo.datacache.CacheStore;
import com.thodsaphon.cachedemo.model.Employee;
import com.thodsaphon.cachedemo.service.EmployeeService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
public class ApiController {

    private final EmployeeService employeeService;
    private final CacheStore<Employee> employeeCache;

    public ApiController(EmployeeService employeeService, CacheStore<Employee> employeeCache) {
        this.employeeService = employeeService;
        this.employeeCache = employeeCache;
    }

    @GetMapping("/ping")
    public String ping() {
        return "Ready...";
    }

    @GetMapping("/employee/{id}")
    public Employee searchEmployeeByID(@PathVariable String id) {

        System.out.println("Searching Employee by ID  : " + id);

        //Search Employee record in Cache
        Employee cachedEmpRecord = employeeCache.get(id);
        if(cachedEmpRecord != null) {
            System.out.println("Employee record found in cache : " + cachedEmpRecord.getName());
            return cachedEmpRecord;
        }

        //Fetch Employee details from backend service
        Employee EmpRecordFromBackendService = employeeService.getEmployeeByID(id);

        //Store Employee record in Cache
        employeeCache.add(id, EmpRecordFromBackendService);

        return EmpRecordFromBackendService;
    }
}

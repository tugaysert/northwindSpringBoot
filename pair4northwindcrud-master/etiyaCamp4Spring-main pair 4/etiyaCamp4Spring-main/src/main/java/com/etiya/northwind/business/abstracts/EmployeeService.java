package com.etiya.northwind.business.abstracts;

import com.etiya.northwind.business.requests.employeeRequests.CreateEmployeeRequest;
import com.etiya.northwind.business.requests.employeeRequests.UpdateEmployeeRequest;
import com.etiya.northwind.business.responses.PageDataResponse;
import com.etiya.northwind.business.responses.employees.EmployeeListResponse;
import com.etiya.northwind.core.results.DataResult;
import com.etiya.northwind.core.results.Result;

import java.util.List;

public interface EmployeeService {
    Result add(CreateEmployeeRequest createEmployeeRequest);
    Result update(UpdateEmployeeRequest updateEmployeeRequest);
    Result delete(int employeeId);
    DataResult<List<EmployeeListResponse>> getAll();
    DataResult<EmployeeListResponse> getById(int employeeId);

    DataResult<PageDataResponse<EmployeeListResponse>> getByPage(int pageNumber, int orderAmountInPage);

    DataResult<PageDataResponse<EmployeeListResponse>> getByPageWithSorting(int pageNumber, int orderAmountInPage, String fieldName, boolean isAsc);
}

package com.etiya.northwind.business.abstracts;

import com.etiya.northwind.business.requests.customerRequests.CreateCustomerRequest;
import com.etiya.northwind.business.requests.customerRequests.UpdateCustomerRequest;
import com.etiya.northwind.business.responses.PageDataResponse;
import com.etiya.northwind.business.responses.customers.CustomerListResponse;
import com.etiya.northwind.core.results.DataResult;
import com.etiya.northwind.core.results.Result;
import com.etiya.northwind.entities.concretes.Customer;

import java.util.List;

public interface CustomerService {
    Result add(CreateCustomerRequest createCustomerRequest);
    Result update(UpdateCustomerRequest updateCustomerRequest);
    Result delete(String customerId);
    DataResult<List<CustomerListResponse>> getAll();
    DataResult<CustomerListResponse> getById(String customerId);

    DataResult<PageDataResponse<CustomerListResponse>> getByPage(int pageNumber, int orderAmountInPage);

    DataResult<PageDataResponse<CustomerListResponse>> getByPageWithSorting(int pageNumber, int orderAmountInPage, String fieldName, boolean isAsc);

    Customer findById(String customerId);
}

package com.etiya.northwind.business.concretes;

import com.etiya.northwind.business.abstracts.CustomerService;
import com.etiya.northwind.business.requests.customerRequests.CreateCustomerRequest;
import com.etiya.northwind.business.requests.customerRequests.UpdateCustomerRequest;
import com.etiya.northwind.business.responses.PageDataResponse;
import com.etiya.northwind.business.responses.customers.CustomerListResponse;
import com.etiya.northwind.core.exceptions.BusinessException;
import com.etiya.northwind.core.mapping.ModelMapperService;
import com.etiya.northwind.core.results.DataResult;
import com.etiya.northwind.core.results.Result;
import com.etiya.northwind.core.results.SuccessDataResult;
import com.etiya.northwind.core.results.SuccessResult;
import com.etiya.northwind.dataAccess.abstracts.CustomerRepository;
import com.etiya.northwind.entities.concretes.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CustomerManager implements CustomerService {
    private CustomerRepository customerRepository;
    private ModelMapperService modelMapperService;

    @Autowired
    public CustomerManager(CustomerRepository customerRepository, ModelMapperService modelMapperService) {
        this.customerRepository = customerRepository;
        this.modelMapperService = modelMapperService;
    }


    @Override
    public Result add(CreateCustomerRequest createCustomerRequest) {
        Customer customer = this.modelMapperService.forRequest().map(createCustomerRequest, Customer.class);
        customerRepository.save(customer);
        return new SuccessResult("Added");
    }

    @Override
    public Result update(UpdateCustomerRequest updateCustomerRequest) {
        Customer customer = this.modelMapperService.forRequest().map(updateCustomerRequest, Customer.class);
        customerRepository.save(customer);
        return new SuccessResult("Updated");
    }

    @Override
    public Result delete(String customerId) {
        checkCustomerExists(customerId);
        this.customerRepository.deleteById(customerId);
        return new SuccessResult("Deleted successfully.");
    }

    @Override
    public DataResult<List<CustomerListResponse>> getAll() {
        List<Customer> result = this.customerRepository.findAll();
        List<CustomerListResponse> response = result.stream()
                .map(customer -> this.modelMapperService.forResponse()
                        .map(customer, CustomerListResponse.class))
                .collect(Collectors.toList());
        return new SuccessDataResult<>(response);
    }

    @Override
    public DataResult<CustomerListResponse> getById(String customerId) {
        Customer customer = this.customerRepository.findById(customerId).orElseThrow(() -> new BusinessException("Customer not found."));
        CustomerListResponse response = this.modelMapperService.forResponse().map(customer, CustomerListResponse.class);

        return new SuccessDataResult<>(response);
    }

    @Override
    public DataResult<PageDataResponse<CustomerListResponse>> getByPage(int pageNumber, int customerAmountInPage) {
        Pageable pageable = PageRequest.of(pageNumber-1,customerAmountInPage);
        return new SuccessDataResult<>(getPageDataResponse(pageNumber, pageable));
    }

    @Override
    public DataResult<PageDataResponse<CustomerListResponse>> getByPageWithSorting(int pageNumber, int customerAmountInPage, String fieldName, boolean isAsc) {
        Pageable pageable;
        if (isAsc){
            pageable = PageRequest.of(pageNumber-1,customerAmountInPage, Sort.by(fieldName).ascending());
        }else {
            pageable = PageRequest.of(pageNumber-1,customerAmountInPage, Sort.by(fieldName).descending());
        }
        return new SuccessDataResult<>(getPageDataResponse(pageNumber, pageable));
    }

    @Override
    public Customer findById(String customerId) {
        return this.customerRepository.findById(customerId).orElseThrow(() -> new BusinessException("Customer not found."));
    }

    private PageDataResponse<CustomerListResponse> getPageDataResponse(int pageNumber, Pageable pageable) {
        Page<Customer> pages = this.customerRepository.findAllCustomers(pageable);
        List<CustomerListResponse> response =
                pages.getContent().stream().map(customer -> this.modelMapperService.forResponse().map(customer, CustomerListResponse.class)).collect(Collectors.toList());

        return new PageDataResponse<CustomerListResponse>(response, pages.getTotalPages(), pages.getTotalElements(), pageNumber);
    }

    private void checkCustomerExists(String customerId) {
        if (!customerRepository.existsById(customerId)){
            throw new BusinessException("Customer does not exist.");
        }
    }

}

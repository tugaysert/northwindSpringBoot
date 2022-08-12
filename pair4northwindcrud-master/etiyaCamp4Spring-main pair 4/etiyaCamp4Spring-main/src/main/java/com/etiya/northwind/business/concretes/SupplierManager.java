package com.etiya.northwind.business.concretes;

import com.etiya.northwind.business.abstracts.SupplierService;
import com.etiya.northwind.business.requests.supplierRequests.CreateSupplierRequest;
import com.etiya.northwind.business.requests.supplierRequests.UpdateSupplierRequest;
import com.etiya.northwind.business.responses.PageDataResponse;
import com.etiya.northwind.business.responses.suppliers.SupplierListResponse;
import com.etiya.northwind.core.exceptions.BusinessException;
import com.etiya.northwind.core.mapping.ModelMapperService;
import com.etiya.northwind.core.results.DataResult;
import com.etiya.northwind.core.results.Result;
import com.etiya.northwind.core.results.SuccessDataResult;
import com.etiya.northwind.core.results.SuccessResult;
import com.etiya.northwind.dataAccess.abstracts.SupplierRepository;
import com.etiya.northwind.entities.concretes.Supplier;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class SupplierManager implements SupplierService {
    private SupplierRepository supplierRepository;
    private ModelMapperService modelMapperService;

    @Autowired
    public SupplierManager(SupplierRepository supplierRepository, ModelMapperService modelMapperService) {
        this.supplierRepository = supplierRepository;
        this.modelMapperService = modelMapperService;
    }


    @Override
    public Result add(CreateSupplierRequest createSupplierRequest) {
        Supplier supplier = this.modelMapperService.forRequest().map(createSupplierRequest, Supplier.class);
        supplierRepository.save(supplier);
        return new SuccessResult("Added");
    }

    @Override
    public Result update(UpdateSupplierRequest updateSupplierRequest) {
        Supplier supplier = this.modelMapperService.forRequest().map(updateSupplierRequest, Supplier.class);
        supplierRepository.save(supplier);
        return new SuccessResult("Updated");
    }

    @Override
    public Result delete(int supplierId) {
        checkSupplierExists(supplierId);
        this.supplierRepository.deleteById(supplierId);
        return new SuccessResult("Deleted successfully.");
    }

    @Override
    public DataResult<List<SupplierListResponse>> getAll() {
        List<Supplier> result = this.supplierRepository.findAll();
        List<SupplierListResponse> response = result.stream()
                .map(supplier -> this.modelMapperService.forResponse()
                        .map(supplier, SupplierListResponse.class))
                .collect(Collectors.toList());
        return new SuccessDataResult<>(response);
    }

    @Override
    public DataResult<SupplierListResponse> getById(int supplierId) {
        Supplier supplier = this.supplierRepository.findById(supplierId).orElseThrow(() -> new BusinessException("Supplier not found."));
        SupplierListResponse response = this.modelMapperService.forResponse().map(supplier, SupplierListResponse.class);

        return new SuccessDataResult<>(response);
    }

    @Override
    public DataResult<PageDataResponse<SupplierListResponse>> getByPage(int pageNumber, int supplierAmountInPage) {
        Pageable pageable = PageRequest.of(pageNumber-1,supplierAmountInPage);
        return new SuccessDataResult<>(getPageDataResponse(pageNumber, pageable));
    }

    @Override
    public DataResult<PageDataResponse<SupplierListResponse>> getByPageWithSorting(int pageNumber, int supplierAmountInPage, String fieldName, boolean isAsc) {
        Pageable pageable;
        if (isAsc){
            pageable = PageRequest.of(pageNumber-1,supplierAmountInPage, Sort.by(fieldName).ascending());
        }else {
            pageable = PageRequest.of(pageNumber-1,supplierAmountInPage, Sort.by(fieldName).descending());
        }
        return new SuccessDataResult<>(getPageDataResponse(pageNumber, pageable));
    }

    private PageDataResponse<SupplierListResponse> getPageDataResponse(int pageNumber, Pageable pageable) {
        Page<Supplier> pages = this.supplierRepository.findAllSuppliers(pageable);
        List<SupplierListResponse> response =
                pages.getContent().stream().map(supplier -> this.modelMapperService.forResponse().map(supplier, SupplierListResponse.class)).collect(Collectors.toList());

        return new PageDataResponse<SupplierListResponse>(response, pages.getTotalPages(), pages.getTotalElements(), pageNumber);
    }

    private void checkSupplierExists(int supplierId) {
        if (!supplierRepository.existsById(supplierId)){
            throw new BusinessException("Supplier does not exist.");
        }
    }

}

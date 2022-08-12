package com.etiya.northwind.api.controllers;

import com.etiya.northwind.business.abstracts.SupplierService;
import com.etiya.northwind.business.requests.supplierRequests.CreateSupplierRequest;
import com.etiya.northwind.business.requests.supplierRequests.UpdateSupplierRequest;
import com.etiya.northwind.business.responses.PageDataResponse;
import com.etiya.northwind.business.responses.suppliers.SupplierListResponse;
import com.etiya.northwind.core.results.DataResult;
import com.etiya.northwind.core.results.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/suppliers")
public class SuppliersController {
    private SupplierService supplierService;

    @Autowired
    public SuppliersController(SupplierService supplierService) {
        this.supplierService = supplierService;
    }

    @GetMapping("/getall")
    public DataResult<List<SupplierListResponse>> getAll(){
        return this.supplierService.getAll();
    }

    @PostMapping("/add")
    public Result add(@RequestBody @Valid CreateSupplierRequest createSupplierRequest){
        return this.supplierService.add(createSupplierRequest);
    }

    @PostMapping("/update")
    public Result update(@RequestBody @Valid UpdateSupplierRequest updateSupplierRequest){
        return this.supplierService.update(updateSupplierRequest);
    }

    @DeleteMapping("/delete/{supplierId}")
    public Result delete(@Valid @PathVariable int supplierId){
        return this.supplierService.delete(supplierId);
    }

    @GetMapping("/getbyid/{supplierId}")
    public DataResult<SupplierListResponse> getById(@PathVariable int supplierId){
        return this.supplierService.getById(supplierId);
    }

    @GetMapping("/getByPage/{pageNumber}/{supplierAmountInPage}")
    public DataResult<PageDataResponse<SupplierListResponse>> getByPage(@PathVariable int pageNumber, @PathVariable int supplierAmountInPage){
        return this.supplierService.getByPage(pageNumber,supplierAmountInPage);
    }

    @GetMapping("/getByPageWithSorting/{pageNumber}/{supplierAmountInPage}/{fieldName}/{isAsc}")
    public DataResult<PageDataResponse<SupplierListResponse>> getByPageWithSorting(@PathVariable int pageNumber, @PathVariable int supplierAmountInPage, @PathVariable String fieldName, @PathVariable boolean isAsc){
        return this.supplierService.getByPageWithSorting(pageNumber,supplierAmountInPage,fieldName,isAsc);
    }
}

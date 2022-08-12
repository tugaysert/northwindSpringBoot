package com.etiya.northwind.business.abstracts;

import com.etiya.northwind.business.requests.categoryRequests.CreateCategoryRequest;
import com.etiya.northwind.business.requests.categoryRequests.UpdateCategoryRequest;
import com.etiya.northwind.business.responses.PageDataResponse;
import com.etiya.northwind.business.responses.categories.CategoryListResponse;
import com.etiya.northwind.core.results.DataResult;
import com.etiya.northwind.core.results.Result;

import java.util.List;

public interface CategoryService {
    Result add(CreateCategoryRequest createCategoryRequest);
    Result update(UpdateCategoryRequest updateCategoryRequest);
    Result delete(int categoryId);
    DataResult<List<CategoryListResponse>> getAll();
    DataResult<CategoryListResponse> getById(int categoryId);

    DataResult<PageDataResponse<CategoryListResponse>> getByPage(int pageNumber, int orderAmountInPage);

    DataResult<PageDataResponse<CategoryListResponse>> getByPageWithSorting(int pageNumber, int orderAmountInPage, String fieldName, boolean isAsc);
}

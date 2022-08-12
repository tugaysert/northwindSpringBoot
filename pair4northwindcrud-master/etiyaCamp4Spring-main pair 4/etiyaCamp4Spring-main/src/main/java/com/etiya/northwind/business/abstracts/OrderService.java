package com.etiya.northwind.business.abstracts;

import com.etiya.northwind.business.requests.orderRequests.CreateOrderRequest;
import com.etiya.northwind.business.requests.orderRequests.CreateOrderRequestWithDetails;
import com.etiya.northwind.business.requests.orderRequests.UpdateOrderRequest;
import com.etiya.northwind.business.responses.PageDataResponse;
import com.etiya.northwind.business.responses.orders.OrderListResponse;
import com.etiya.northwind.core.results.DataResult;
import com.etiya.northwind.core.results.Result;

import java.util.List;

public interface OrderService {
    Result add(CreateOrderRequest createOrderRequest);
    Result update(UpdateOrderRequest updateOrderRequest);
    Result delete(int orderId);
    DataResult<List<OrderListResponse>> getAll();
    DataResult<OrderListResponse> getById(int orderId);

    DataResult<PageDataResponse<OrderListResponse>> getByPage(int pageNumber, int orderAmountInPage);

    DataResult<PageDataResponse<OrderListResponse>> getByPageWithSorting(int pageNumber, int orderAmountInPage, String fieldName, boolean isAsc);
}

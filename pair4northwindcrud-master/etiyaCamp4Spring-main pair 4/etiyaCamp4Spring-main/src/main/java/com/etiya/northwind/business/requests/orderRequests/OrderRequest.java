package com.etiya.northwind.business.requests.orderRequests;

import com.etiya.northwind.business.requests.orderDetailRequests.CreateOrderDetailInOrderRequest;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.List;

@Data
public abstract class OrderRequest {
    @NotNull
    private List<CreateOrderDetailInOrderRequest> orderDetailRequests;
}

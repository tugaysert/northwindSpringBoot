package com.etiya.northwind.business.requests;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PageSortRequest extends PageDataRequest {
    @NotBlank
    String fieldName;
    @NotNull
    boolean isAscending;
}

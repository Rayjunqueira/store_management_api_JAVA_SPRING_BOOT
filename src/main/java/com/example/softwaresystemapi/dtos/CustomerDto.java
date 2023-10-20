package com.example.softwaresystemapi.dtos;

import com.example.softwaresystemapi.models.CustomerCategoryModel;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class CustomerDto {
    @NotBlank
    private String name;
    private String email;
    private String cellphone1;
    private String cellphone2;
    private String note;
    @NotNull
    private CustomerCategoryModel customercategory;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCellphone1() {
        return cellphone1;
    }

    public void setCellphone1(String cellphone1) {
        this.cellphone1 = cellphone1;
    }

    public String getCellphone2() {
        return cellphone2;
    }

    public void setCellphone2(String cellphone2) {
        this.cellphone2 = cellphone2;
    }

    public CustomerCategoryModel getCustomercategory() {
        return customercategory;
    }

    public void setCustomercategory(CustomerCategoryModel customercategory) {
        this.customercategory = customercategory;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }
}

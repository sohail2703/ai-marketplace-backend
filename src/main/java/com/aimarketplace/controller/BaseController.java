package com.aimarketplace.controller;

import org.springframework.http.ResponseEntity;

public class BaseController {


    protected <T> ResponseEntity<T> ok(T response){

        return ResponseEntity.ok(response);

    }

}
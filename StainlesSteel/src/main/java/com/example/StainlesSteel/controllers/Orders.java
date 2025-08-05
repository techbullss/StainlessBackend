package com.example.StainlesSteel.controllers;

import com.example.StainlesSteel.Repository.OrderRepository;
import com.example.StainlesSteel.Services.OrderServices;
import com.example.StainlesSteel.Tables.OrderTable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("api/orders")
public class Orders {
    @Autowired
    private OrderServices orderServices;
    @Autowired
    OrderRepository OrderRepo;
    @PostMapping
    public ResponseEntity<?> saveOrders(@RequestBody OrderTable ordertable){

       orderServices.saveOrder(ordertable);
       return  ResponseEntity.ok("order saved");

    }
    @GetMapping
    public ResponseEntity<List<OrderTable>>getOrders(){
      List <OrderTable>order=OrderRepo.findAll();
      return ResponseEntity.ok(order);
    }
}

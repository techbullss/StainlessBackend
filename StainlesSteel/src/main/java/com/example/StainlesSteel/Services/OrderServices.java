package com.example.StainlesSteel.Services;

import com.example.StainlesSteel.Repository.OrderRepository;
import com.example.StainlesSteel.Tables.OrderTable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrderServices {
    @Autowired
    private OrderRepository orderRepo;
    public OrderTable saveOrder(OrderTable order){
        return orderRepo.save(order);
    }
}

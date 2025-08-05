package com.example.StainlesSteel.Repository;

import com.example.StainlesSteel.Tables.OrderTable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<OrderTable, Long> {
}

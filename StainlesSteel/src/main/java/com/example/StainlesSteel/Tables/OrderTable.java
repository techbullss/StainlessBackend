package com.example.StainlesSteel.Tables;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Entity
@Table(name="order_table")
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OrderTable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private  Long id;
    private String Phonenumber;
     private String email;
     private Integer quantity;
     private Double Amount;
     private String productname;

}

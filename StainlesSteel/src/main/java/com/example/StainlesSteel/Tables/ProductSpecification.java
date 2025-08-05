package com.example.StainlesSteel.Tables;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "product_specifications")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductSpecification {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 500)
    private String specText;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", nullable = false)
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Product product;


}
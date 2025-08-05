package com.example.StainlesSteel.Tables;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Entity
@Table(name="user_table")
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserTable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    @Column(unique = true)
    private String email;
 private String role;
 private String password;

}

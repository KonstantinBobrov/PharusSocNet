package ru.pharus.socnetwork.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class CarModel {
//    id INT AUTO_INCREMENT PRIMARY KEY,
    private  int id;
//    vendor_id INT NOT NULL,
    private int vendorID;
//    name VARCHAR(32),
    private String name;
}

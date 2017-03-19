package ru.pharus.socnetwork.entity;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Car {
//    id INT AUTO_INCREMENT PRIMARY KEY,
    private int id;
//    driver_id INT,
    private int userId;
//    model_id INT NOT NULL,
    private int modelId;
//    car_year YEAR,
    private java.time.Year year;
//    car_number VARCHAR(10),
    private String carNumber;
}

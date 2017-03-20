package ru.pharus.socnetwork.entity;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Car {
//    id INT AUTO_INCREMENT PRIMARY KEY,
    private int id;
//    driver_id INT,
    @NotNull
    private int userId;
//    model_id INT NOT NULL,
    @NotNull
    private int modelId;
//    car_year YEAR,
    @Size(max = 4)
    private int year;
//    car_number VARCHAR(10),
    @Size(max = 10)
    private String carNumber;
}

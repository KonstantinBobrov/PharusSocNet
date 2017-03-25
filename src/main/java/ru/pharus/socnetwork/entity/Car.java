package ru.pharus.socnetwork.entity;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

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

    //@Size(max = 4)  @Length(max = 4) выдают ошибку для поля: непонятно
    //No validator could be found for constraint 'org.hibernate.validator.constraints.Length' validating type 'java.lang.Integer'. Check configuration for 'year'
    private int year;

//    car_number VARCHAR(10),
    @Length(min=5, max=10)
    private String carNumber;
}

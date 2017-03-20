package ru.pharus.socnetwork.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;


/**
 *
 *
 * Lombok knowledge, for example:
 * Getters and Setters, Equals and Hashcode methods
 * are generated by Lombok annotations
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Model {
//    id INT AUTO_INCREMENT PRIMARY KEY,
    private  int id;
//    vendor_id INT NOT NULL,
//    @NotNull
//    private int vendorID;
//    name VARCHAR(32),
    @NotBlank(message = "Enter model name")
    @Size(max = 32)
    private String name;
}

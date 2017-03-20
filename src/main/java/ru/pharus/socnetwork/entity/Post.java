package ru.pharus.socnetwork.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Post {
//    id INT AUTO_INCREMENT PRIMARY KEY,
    private int id;
//    driver_id INT
    @NotNull
    private int userId;
//    title VARCHAR(255) NOT NULL,
    @NotBlank(message = "Enter post title")
    @Size(max = 255)
    private String title;
//    post TEXT,
    private String text;
}

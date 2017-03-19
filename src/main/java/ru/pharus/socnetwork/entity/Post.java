package ru.pharus.socnetwork.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Post {
//    id INT AUTO_INCREMENT PRIMARY KEY,
    private int id;
//    driver_id INT
    private int userId;
//    title VARCHAR(255) NOT NULL,
    private String title;
//    post TEXT,
    private String text;
}

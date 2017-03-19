package ru.pharus.socnetwork.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Message {
    //id INT AUTO_INCREMENT PRIMARY KEY,
    private int id;
    // from_user_id   INT NOT NULL DEFAULT '0',
    private int fromUser;
    //to_user_id   INT NOT NULL DEFAULT '0',
    private int toUserId;
    //message TEXT,
    private String message;
    //post_time DATETIME,
}

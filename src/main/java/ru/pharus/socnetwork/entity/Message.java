package ru.pharus.socnetwork.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Message {
    //id INT AUTO_INCREMENT PRIMARY KEY,
    private int id;
    // from_user_id   INT NOT NULL DEFAULT '0',
    @NotNull
    private int fromUser;
    //to_user_id   INT NOT NULL DEFAULT '0',
    @NotNull
    private int toUserId;
    //message TEXT,
    @NotBlank (message = "Enter message")
    private String message;
    //post_time DATETIME,
}

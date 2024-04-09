package org.example.bottelegram.Entity;


import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "user_table")
@Getter
@Setter
public class UserEntity {

    @Id
    private long id;

    private String username;

    private long score;

}

package com.barreto.todolist.user;

import com.barreto.todolist.utils.BCryptUtils;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Entity(name = "tb_users")
@NoArgsConstructor
@AllArgsConstructor
public class UserModel {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private String name;

    @Column(unique = true)
    private String username;
    private String password;

    @CreationTimestamp
    private LocalDateTime createdAt;

    public UserModel(UserCreateInDTO userCreateInDTO) {
        this.setName(userCreateInDTO.name());
        this.setUsername(userCreateInDTO.username());
        this.setPassword(userCreateInDTO.password());
    }

    public void hashPassword() {
        this.password = BCryptUtils.encrypt(this.password);
    }
}

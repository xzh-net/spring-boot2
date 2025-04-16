package net.xzh.sqlite.model;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Data
@NoArgsConstructor
@Accessors(chain = true)
@Entity
@Table(name = "user")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false, unique = true, length = 64)
    private String name;

    @Column(name = "password", nullable = false, length = 255)
    private String password;

    @Column(name = "salt", nullable = false, length = 16)
    private String salt;

    @Column(name = "status", nullable = false, length = 16, columnDefinition = "varchar(16) default 'active'")
    private String status;

    @Column(name = "created_at", nullable = false, columnDefinition = "timestamp default current_timestamp")
    private LocalDateTime createdAt;
  
    @Column(name = "updated_at", nullable = false, columnDefinition = "timestamp default current_timestamp")
    private LocalDateTime updatedAt;
}


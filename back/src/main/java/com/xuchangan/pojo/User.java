package com.xuchangan.pojo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {
    private int userId;

    // 使用validation对username进行校验
    @Pattern(regexp="^\\S{5,16}$", message = "用户名必须是5-16位非空字符")
    private String username;

    @Pattern(regexp="^\\S{5,16}$", message = "密码必须是5-16位非空字符")
    private String password;

    private String realName;
    private String gender;

    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private LocalDateTime birthDate;
    private double height;
    private String familyRole;
}

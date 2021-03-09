package com.example.utilsdemo.json;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {
    private String id;
    private String name;
    private String password;

    @Override
    public String toString() {
        return "User [id=" + id + ", name=" + name + ", password=" + password
                + "]";
    }
}
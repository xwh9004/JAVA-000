package com.example.springboot.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * <p><b>Description:</b>
 * TODO
 * <p><b>Company:</b>
 *
 * @author created by Jesse Hsu at 16:50 on 2020/11/17
 * @version V0.1
 * @classNmae User
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
public class User {

    private int id;

    private String firstName;

    private String lastName;
}

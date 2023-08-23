/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.qltc.pojo;

import java.io.Serializable;
import lombok.Data;

/**
 *
 * @author sonho
 */
@Data
public class UserAccount implements Serializable {

    private static final long serialVersionUID = 1L;

    private String name;
    private String password;
}

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.qltc.pojo;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author sonho
 */
@Getter
@Setter
@Entity
@Table(name = "employees")
public class Employee extends User implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    private Integer id;
    @Basic(optional = false)
    private String firstName;
    @Basic(optional = false)
    private String lastName;
    @Basic(optional = false)
    private String identityNumber;
    @Basic(optional = false)
    private String position;
    @OneToOne(mappedBy = "employeeId", fetch = FetchType.LAZY)
    @Basic(optional = false)
    private User userId;
    @ManyToOne(optional = false)
    @JoinColumn(name = "branchId", referencedColumnName = "id")
    @Basic(optional = false)
    private Branch branchId;
}

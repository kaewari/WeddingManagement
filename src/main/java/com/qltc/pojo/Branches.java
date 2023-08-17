/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.qltc.pojo;

import java.io.Serializable;
import java.util.Set;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
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
@Table(name = "branches")
public class Branches implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    private Integer id;
    @Basic(optional = false)
    private String province;
    @Basic(optional = false)
    private String district;
    @Basic(optional = false)
    private String ward;
    @Basic(optional = false)
    private String quarter;
    @Basic(optional = false)
    private String houseNumber;
    @Basic(optional = false)
    private Boolean isActive;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "branchId")
    private Set<DishesInBranch> dishesInBranchSet;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "branchId")
    private Set<Employees> employeeSet;
}

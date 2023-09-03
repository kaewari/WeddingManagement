package com.qltc.pojo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import lombok.Data;

@Entity
@Data
@Table(name = "branches")
public class Branch implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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
    
    private Boolean isActive = true;
    
    @JsonIgnore
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "branch", orphanRemoval = true)
    private Set<DishInBranch> dishInBranches;
    
    @JsonIgnore
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "atBranch", fetch = FetchType.LAZY)
    private Set<Employee> employees = new HashSet<>();
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "atBranch", orphanRemoval = true)
    private Set<Hall> halls = new HashSet<>();
    
    public void addHall(Hall hall) {
        halls.add(hall);
        hall.setAtBranch(this);
    }
    
    public void removeHall(Hall hall) {
        halls.remove(hall);
        hall.setAtBranch(null);
    }
    
    public void addEmployee(Employee employee) {
        employees.add(employee);
        employee.setAtBranch(this);
    }
    
    public void removeEmployee(Employee employee) {
        employees.remove(employee);
        employee.setAtBranch(null);
    }
    
    public void addDishInBranch(DishInBranch dishInBranch) {
        dishInBranches.add(dishInBranch);
        dishInBranch.setBranch(this);
    }
    
    public void removeDishInBranch(DishInBranch dishInBranch) {
        dishInBranches.remove(dishInBranch);
        dishInBranch.setBranch(null);
    }
}

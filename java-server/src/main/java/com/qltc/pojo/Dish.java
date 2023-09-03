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
@Table(name = "dishes")
public class Dish implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    
    @Basic(optional = false)
    private String name;
    
    @Basic(optional = false)
    private double price;
    
    @Basic(optional = false)
    private float discount =  0;
    
    @Basic(optional = false)
    private String unit;
    
    @Basic(optional = false)
    private String type;
    
    @Basic(optional = false)
    private Boolean wOnly = false; //Only for wedding
    
    @Basic(optional = false)
    private String image;
    
    @Basic(optional = false)
    private Boolean isAvailable = true;
    
    @JsonIgnore
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "dish", fetch = FetchType.LAZY, orphanRemoval = true)
    private Set<DishInBranch> dishInBranches = new HashSet<>();
    
    public void addDishInBranch(DishInBranch dishInBranch) {
        dishInBranches.add(dishInBranch);
        dishInBranch.setDish(this);
    }
    
    public void removeDishInBranch(DishInBranch dishInBranch) {
        dishInBranches.remove(dishInBranch);
        dishInBranch.setDish(null);
    }
}

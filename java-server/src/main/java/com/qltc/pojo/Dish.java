package com.qltc.pojo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonView;
import com.qltc.json.JsonMarkup;
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
import javax.persistence.Transient;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Entity
@Data
@Table(name = "dishes")
public class Dish implements Serializable {

    private static final long serialVersionUID = 1L;
    @JsonView(JsonMarkup.Identity.class)
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    
    @JsonView(JsonMarkup.CoreData.class)
    @Basic(optional = false)
    private String name;
    
    @JsonView(JsonMarkup.CoreData.class)
    @Basic(optional = false)
    private double price;
    
    @JsonView(JsonMarkup.CoreData.class)
    @Basic(optional = false)
    private float discount =  0;
    
    @JsonView(JsonMarkup.CoreData.class)
    @Basic(optional = false)
    private String unit;
    
    @JsonView(JsonMarkup.CoreData.class)
    @Basic(optional = false)
    private String type;
    
    @JsonView(JsonMarkup.CoreData.class)
    @Basic(optional = false)
    private Boolean wOnly = false; //Only for wedding
    
    @JsonView(JsonMarkup.CoreData.class)
    private String image;
    
    @JsonView(JsonMarkup.CoreData.class)
    @Basic(optional = false)
    private Boolean isAvailable = true;
    
    @JsonIgnore
    @Transient
    private MultipartFile file;
    
    @JsonIgnore
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "dish", fetch = FetchType.LAZY
            , orphanRemoval = true)
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

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
import lombok.Data;

@Entity
@Data
@Table(name = "branches")
public class Branch implements Serializable {

    private static final long serialVersionUID = 1L;
    @JsonView(JsonMarkup.Identity.class)
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Basic(optional = false)
    @JsonView(JsonMarkup.CoreData.class)
    private String province;

    @Basic(optional = false)
    @JsonView(JsonMarkup.CoreData.class)
    private String district;

    @Basic(optional = false)
    @JsonView(JsonMarkup.CoreData.class)
    private String ward;

    @Basic(optional = false)
    @JsonView(JsonMarkup.CoreData.class)
    private String quarter;

    @Basic(optional = false)
    @JsonView(JsonMarkup.CoreData.class)
    private String houseNumber;

    @JsonView(JsonMarkup.CoreData.class)
    private Boolean isActive = true;

    @JsonIgnore
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "branch", orphanRemoval = true)
    private Set<DishInBranch> dishInBranches;

    @JsonIgnore
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "branchId", fetch = FetchType.LAZY)
    private Set<Employee> employees = new HashSet<>();

    @JsonView(JsonMarkup.FullData.class)
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "atBranch", orphanRemoval = true
            , fetch = FetchType.LAZY)
    private Set<Hall> halls = new HashSet<>();

    public void addHall(Hall hall) {
        halls.add(hall);
        hall.setAtBranch(this);
    }

    public void removeHall(Hall hall) {
        halls.remove(hall);
        hall.setAtBranch(null);
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

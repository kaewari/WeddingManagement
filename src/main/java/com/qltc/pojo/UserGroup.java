package com.qltc.pojo;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
import lombok.Data;


@Entity
@Data
@Table(name = "user_groups")
public class UserGroup implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    
    @Basic(optional = false)
    private String name;
    
    @JsonIgnore
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "userGroup", orphanRemoval = true)
    private Set<UserInGroup> userInGroups;
    
    @JsonIgnore
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "group", orphanRemoval = true)
    private Set<UserGroupPermission> userGroupPermissions;
    
    public void addUserInGroup(UserInGroup userInGroup) {
        userInGroups.add(userInGroup);
        userInGroup.setUserGroup(this);
    }
    
    public void removerUserInGroup(UserInGroup userInGroup) {
        userInGroups.remove(userInGroup);
        userInGroup.setUserGroup(null);
    }
    
    public void addUserGroupPermission(UserGroupPermission userGroupPermission) {
        userGroupPermissions.add(userGroupPermission);
        userGroupPermission.setGroup(this);
    }
    
    public void removeUserGroupPermission(UserGroupPermission userGroupPermission) {
        userGroupPermissions.remove(userGroupPermission);
        userGroupPermission.setGroup(null);
    }
}

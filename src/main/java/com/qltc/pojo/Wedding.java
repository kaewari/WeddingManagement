/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.qltc.pojo;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author sonho
 */
@Getter
@Setter
@Entity
@Table(name = "wedding")
public class Weddings implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    private Integer id;
    @Basic(optional = false)
    private Long totalLeft;
    @Basic(optional = false)
    private Long deposit;
    @Basic(optional = false)
    private String paidVia;
    @Basic(optional = false)
    private Boolean isCompleted;
    @Basic(optional = false)
    private int tableNumber;
    @Basic(optional = false)
    private int guestNumber;
    @Basic(optional = false)
    private String description;
    @Basic(optional = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date celebrityDate;
    @Basic(optional = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdDate;
    @JoinColumn(name = "orderId", referencedColumnName = "id")
    @ManyToOne(optional = false)
    @Basic(optional = false)
    private Orders orderId;
    @JoinColumn(name = "createdBy", referencedColumnName = "id")
    @ManyToOne(optional = false)
    @Basic(optional = false)
    private Users userId;
    @JoinColumn(name = "customerId", referencedColumnName = "id")
    @ManyToOne(optional = false)
    @Basic(optional = false)
    private Users customerId;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "weddingId")
    private Set<WeddingPictures> weddingPicturesSet;
}

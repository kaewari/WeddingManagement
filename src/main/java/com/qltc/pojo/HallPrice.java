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
@Table(name = "hall_price")
public class HallPrice implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    private Integer id;
    @Basic(optional = false)
    private String period;
    @Basic(optional = false)
    private Long price;
    @Basic(optional = false)
    private Float discount;
    @Temporal(TemporalType.DATE)
    private Date createdDate;
    @JoinColumn(name = "hallId", referencedColumnName = "id", nullable = false)
    @ManyToOne(optional = false)
    private Hall hallId;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "hallPricesId")
    private Set<OrderHallDetails> orderDetailsHallsSet;
}

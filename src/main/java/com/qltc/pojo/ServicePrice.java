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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
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
@Table(name = "service_price")
public class ServicePrice implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    private Integer id;
    @Basic(optional = false)
    private String period;
    @Basic(optional = false)
    private Long price;
    @Basic(optional = false)
    private Boolean isAvailable;
    @JoinColumn(name = "serviceId", referencedColumnName = "id", nullable = false)
    @ManyToOne(optional = false)
    private WeddingServices weddingservicesId;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "servicePriceId")
    private Set<OrderDetailsServices> orderDetailsServicesSet;
}

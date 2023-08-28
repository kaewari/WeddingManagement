/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.qltc.pojo;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
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
@Table(name = "order_dishes_details")
public class OrderDishDetails implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    private Integer id;
    @Basic(optional = false)
    private Long price;
    @Basic(optional = false)
    private Integer quantity;
    @Basic(optional = false)
    private Float discount;
    @Basic(optional = false)
    private String note;
    @JoinColumn(name = "orderId", referencedColumnName = "id", nullable = false)
    @ManyToOne(optional = false)
    private Order orderId;
    @JoinColumn(name = "dishId", referencedColumnName = "id", nullable = false)
    @ManyToOne(optional = false)
    private Dish dishId;

}

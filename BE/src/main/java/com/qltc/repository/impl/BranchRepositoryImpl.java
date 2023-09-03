/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.qltc.repository.impl;

import com.qltc.pojo.Branch;
import com.qltc.repository.BranchRepository;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author sonho
 */
@Repository
@Transactional
public class BranchRepositoryImpl implements BranchRepository {

    @Autowired
    private LocalSessionFactoryBean factory;

    @Override
    public Branch getBranchById(int id) {
        Session s = this.factory.getObject().getCurrentSession();
        return s.get(Branch.class, id);
    }
}

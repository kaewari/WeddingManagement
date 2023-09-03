package com.qltc.service;

import com.qltc.pojo.Branch;
import java.util.List;
import java.util.Map;

public interface BranchService {

    public List<Branch> findAll();

    public Branch findById(int id);

    public List<Branch> find(Map<String, Object> findArgs);

    public boolean addOrUpdateBranch(Branch branch);

    public boolean deactivateBranchById(int id);

    public boolean deactivateBranch(Branch branch);

    public boolean activateBranchById(int id);

    public boolean activateBranch(Branch branch);

    public boolean deleteBranchById(int id);

    public boolean deleteBranch(Branch branch);
}

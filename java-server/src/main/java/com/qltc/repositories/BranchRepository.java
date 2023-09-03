package com.qltc.repositories;

import com.qltc.pojo.Branch;
import java.util.List;
import java.util.Map;


public interface BranchRepository {
    public List<Branch> findAll(boolean isActive);
    public Branch findById(int id);
    public List<Branch> find(Map<String, Object> findArgs);
    public boolean addOrUpdateBranch(Branch branch);
    public boolean deactivateOrActivateBranchById(int id, boolean isActive);
    public boolean deactivateOrActivateBranch(Branch branch, boolean isActive);
    public boolean deleteBranchById(int id);
    public boolean deleteBranch(Branch branch);
}

package com.qltc.services.impl;

import com.qltc.pojo.Branch;
import com.qltc.repositories.BranchRepository;
import com.qltc.services.BranchService;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class BranchServiceImpl implements BranchService {
    
    @Autowired
    private BranchRepository branchRepo;

    @Override
    public List<Branch> findAll() {
        return branchRepo.findAll(true);
    }

    @Override
    public Branch findById(int id) {
        return branchRepo.findById(id);
    }

    @Override
    public List<Branch> find(Map<String, Object> findArgs) {
        return branchRepo.find(findArgs);
    }

    @Override
    public boolean addOrUpdateBranch(Branch branch) {
        return branchRepo.addOrUpdateBranch(branch);
    }

    @Override
    public boolean deactivateBranchById(int id) {
        return branchRepo.deactivateOrActivateBranchById(id, false);
    }

    @Override
    public boolean deactivateBranch(Branch branch) {
        return branchRepo.deactivateOrActivateBranch(branch,false);
    }

    @Override
    public boolean activateBranchById(int id) {
        return branchRepo.deactivateOrActivateBranchById(id, true);
    }

    @Override
    public boolean activateBranch(Branch branch) {
        return branchRepo.deactivateOrActivateBranch(branch, true);
    }

    @Override
    public boolean deleteBranchById(int id) {
        return branchRepo.deleteBranchById(id);
    }

    @Override
    public boolean deleteBranch(Branch branch) {
        return branchRepo.deleteBranch(branch);
    }

    
    
}

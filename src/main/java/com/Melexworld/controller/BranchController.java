package com.Melexworld.controller;


import com.Melexworld.exceptions.UserException;
import com.Melexworld.model.Branch;
import com.Melexworld.model.User;
import com.Melexworld.payload.dto.BranchDTO;
import com.Melexworld.payload.response.ApiResponse;
import com.Melexworld.service.BranchService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api/branches")
public class BranchController {
    private final BranchService branchService;

    @PostMapping
    public ResponseEntity<BranchDTO> createBranch(@RequestBody BranchDTO branchDTO) throws UserException {
        BranchDTO createdBranch = branchService.createBranch(branchDTO);

        return ResponseEntity.ok(createdBranch);

    }

    @GetMapping("/{id}")
    public ResponseEntity<BranchDTO>getBranchById(@PathVariable Long id) throws Exception {

        BranchDTO fetchedBranch = branchService.getBranchById(id);
        return  ResponseEntity.ok(fetchedBranch);
    }


    @GetMapping("/store/{storeId}")
    public ResponseEntity<List<BranchDTO>> getAllBranchesByStoreId(@PathVariable Long storeId){

        List<BranchDTO> fetchedBranchesByStoreId = branchService.getAllBranchesByStoreId(storeId);
        return ResponseEntity.ok(fetchedBranchesByStoreId);
    }

    @PutMapping("/{id}")
    public ResponseEntity<BranchDTO> updateBranch(@PathVariable Long id, @RequestBody BranchDTO branchDTO) throws Exception {

        BranchDTO updatedBranch = branchService.updateBranch(id,branchDTO);

        return ResponseEntity.ok(updatedBranch);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse> deleteBranchById(@PathVariable Long id) throws Exception {
        branchService.deleteBranch(id);
        ApiResponse apiResponse = new ApiResponse();
         apiResponse.setMessage("Branch Deleted Successfully");
         return ResponseEntity.ok(apiResponse);
    }
}

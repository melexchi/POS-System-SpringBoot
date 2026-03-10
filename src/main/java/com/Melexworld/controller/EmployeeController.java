package com.Melexworld.controller;


import com.Melexworld.domain.UserRole;
import com.Melexworld.model.User;
import com.Melexworld.payload.dto.UserDTO;
import com.Melexworld.payload.response.ApiResponse;
import com.Melexworld.service.EmployeeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/employees")
public class EmployeeController {

    private final EmployeeService employeeService;

    @PostMapping("/store/{storeId}")
    public ResponseEntity<UserDTO>createStoreEmployee(
            @PathVariable Long storeId,
            @RequestBody UserDTO userDTO ) throws Exception {

        UserDTO employee = employeeService.createStoreEmployee(userDTO,storeId);

        return ResponseEntity.ok(employee);
    }

    @PostMapping("/branch/{branchId}")
    public ResponseEntity<UserDTO>createBranchEmployee(@PathVariable Long branchId, @RequestBody UserDTO userDTO) throws Exception {

        UserDTO employee = employeeService.createBranchEmployee(userDTO,branchId);

        return ResponseEntity.ok(employee);

    }

    @PutMapping("/{id}")
    public ResponseEntity<User> updateEmployee(
            @PathVariable Long id,
            @RequestBody UserDTO userDTO
    ) throws Exception {

        User updatedEmployee = employeeService.updateEmployee(id, userDTO);

        return  ResponseEntity.ok(updatedEmployee);

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse> deleteEmployee(@PathVariable Long id) throws Exception {

         employeeService.deleteEmployee(id);
        ApiResponse apiResponse = new ApiResponse();
        apiResponse.setMessage("Employee deleted successfully");
        return ResponseEntity.ok(apiResponse);
    }

    @GetMapping("/store/{id}")
    public ResponseEntity<List<UserDTO>> storeEmployee(
            @PathVariable Long id,
            @RequestParam(required = false) UserRole role
            ) throws Exception {

        List<UserDTO> storeEmployee = employeeService.findStoreEmployees(id, role);

        return  ResponseEntity.ok(storeEmployee);
    }


    @GetMapping("/branch/{id}")
    public ResponseEntity<List<UserDTO>> branchEmployee(@PathVariable Long id, @RequestParam(required = false) UserRole role) throws Exception {

        List<UserDTO> branchEmployee= employeeService.findBranchEmployees(id, role);

        return ResponseEntity.ok(branchEmployee);

    }

}

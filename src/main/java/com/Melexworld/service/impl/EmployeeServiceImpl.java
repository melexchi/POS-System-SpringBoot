package com.Melexworld.service.impl;

import com.Melexworld.domain.UserRole;
import com.Melexworld.mapper.UserMapper;
import com.Melexworld.model.Branch;
import com.Melexworld.model.Store;
import com.Melexworld.model.User;
import com.Melexworld.payload.dto.UserDTO;
import com.Melexworld.repository.BranchRepository;
import com.Melexworld.repository.StoreRepository;
import com.Melexworld.repository.UserRepository;
import com.Melexworld.service.EmployeeService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class EmployeeServiceImpl implements EmployeeService {
    private final UserRepository userRepository;
    private final StoreRepository storeRepository;
    private final BranchRepository branchRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public UserDTO createStoreEmployee(UserDTO employee, Long storeId) throws Exception {
        Store store = storeRepository.findById(storeId).orElseThrow(
                ()-> new Exception("Store not found")
        );
        Branch branch = null;

        if(employee.getRole()==UserRole.ROLE_BRANCH_MANAGER){
            if(employee.getBranchId()==null){
                throw new Exception("Branch Id is required to create Branch Manager");
            }
            branch= branchRepository.findById(employee.getBranchId()).orElseThrow(
                    ()-> new Exception("Branch not found")
            );
        }
        User user = UserMapper.toEntity(employee);
        user.setStore(store);
        user.setBranch(branch);
        user.setPassword(passwordEncoder.encode(employee.getPassword()));

        User savedEmployee = userRepository.save(user);
        if(employee.getRole()==UserRole.ROLE_BRANCH_MANAGER && branch!=null){
            branch.setManager(savedEmployee);
            branchRepository.save(branch);
        }
        return UserMapper.toDTO(savedEmployee);
    }

    @Override
    public UserDTO createBranchEmployee(UserDTO employee, Long branchId) throws Exception {

      Branch branch = branchRepository.findById(branchId).orElseThrow(
              ()-> new Exception("Branch not found")
      );
      if(employee.getRole()==UserRole.ROLE_BRANCH_CASHIER || employee.getRole()==UserRole.ROLE_BRANCH_MANAGER){

          User user = UserMapper.toEntity(employee);
          user.setBranch(branch);
          user.setPassword(passwordEncoder.encode(employee.getPassword()));

          return UserMapper.toDTO(userRepository.save(user));

      }
        throw new Exception("Branch role not supported");
    }

    @Override
    public User updateEmployee(Long employeeId, UserDTO employeeDetails) throws Exception {

        User existingEmployee = userRepository.findById(employeeId).orElseThrow(
                ()-> new Exception("Employee with given 'id' does not exist")
        );

        Branch branch =branchRepository.findById(employeeDetails.getBranchId()).orElseThrow(
                ()-> new Exception("Branch not found")
        );

        existingEmployee.setEmail(employeeDetails.getEmail());
        existingEmployee.setFullName(employeeDetails.getFullName());
        existingEmployee.setPassword(employeeDetails.getPassword());
        existingEmployee.setRole(employeeDetails.getRole());
        existingEmployee.setBranch(branch);

        return userRepository.save(existingEmployee);
    }

    @Override
    public void deleteEmployee(Long employeeId) throws Exception {

        User employee=userRepository.findById(employeeId).orElseThrow(
                ()-> new Exception("Employee with given 'id' does not exist")
        );
            userRepository.delete(employee);
    }

    @Override
    public List<UserDTO> findStoreEmployees(Long storeId, UserRole role) throws Exception {

        Store store =storeRepository.findById(storeId).orElseThrow(
                ()-> new Exception("Store not found")
        );

        return  userRepository.findByStore(store)
                .stream().filter(
                        user -> role ==null || user.getRole() == role
                ).map(UserMapper::toDTO).collect(Collectors.toList());
    }


    @Override
    public List<UserDTO> findBranchEmployees(Long branchId, UserRole role) throws Exception {

        Branch branch =branchRepository.findById(branchId).orElseThrow(
                ()-> new Exception("Branch not found")
        );


        return userRepository.findByBranchId(branchId).stream().filter(
                user -> role ==null || user.getRole() == role
        ).map(UserMapper::toDTO).collect(Collectors.toList());

    }
}

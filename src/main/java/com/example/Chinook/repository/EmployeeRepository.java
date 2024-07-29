package com.example.Chinook.repository;

import com.example.Chinook.model.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Integer> {

    List<Employee> findByReportsToEmployeeId(Integer managerId);

    @Query("SELECT e FROM Employee e LEFT JOIN FETCH e.subordinates WHERE e.employeeId = :employeeId")
    Optional<Employee> findByIdWithSubordinates(@Param("employeeId") Integer employeeId);

    @Query("SELECT e FROM Employee e WHERE e.reportsTo IS NULL")
    List<Employee> findAllManagers();

    List<Employee> findByTitleContainingIgnoreCase(String title);
}
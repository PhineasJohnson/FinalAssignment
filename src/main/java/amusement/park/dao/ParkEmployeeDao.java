package amusement.park.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import amusement.park.entity.Employee;

public interface ParkEmployeeDao extends JpaRepository<Employee, Long> {

}

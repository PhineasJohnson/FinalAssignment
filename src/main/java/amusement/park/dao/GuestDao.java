package amusement.park.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import amusement.park.entity.Guest;

public interface GuestDao extends JpaRepository<Guest, Long>{

}

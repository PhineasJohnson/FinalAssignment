package amusement.park.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import amusement.park.entity.AmusementPark;

public interface AmusementParkDao extends JpaRepository<AmusementPark, Long>{

}

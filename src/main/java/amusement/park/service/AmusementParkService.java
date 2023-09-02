package amusement.park.service;

import java.util.LinkedList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import amusement.park.controller.model.AmusementParkData;
import amusement.park.controller.model.AmusementParkData.AmusementParkEmployee;
import amusement.park.controller.model.AmusementParkData.AmusementParkGuest;
import amusement.park.dao.AmusementParkDao;
import amusement.park.dao.GuestDao;
import amusement.park.dao.ParkEmployeeDao;
import amusement.park.entity.AmusementPark;
import amusement.park.entity.Employee;
import amusement.park.entity.Guest;

@Service
public class AmusementParkService {
	@Autowired
	private AmusementParkDao amusementParkDao;
	
	public AmusementParkData saveAmusementPark(AmusementParkData amusementParkData) {
		Long amusementParkId = amusementParkData.getAmusementParkId();
		AmusementPark amusementPark = findOrCreateAmusementPark(amusementParkId);
		
		copyAmusementParkFields(amusementPark, amusementParkData);
		return new AmusementParkData(amusementParkDao.save(amusementPark));
	}
	
	private void copyAmusementParkFields(AmusementPark amusementPark, AmusementParkData amusementParkData) {
		amusementPark.setAmusementParkId(amusementParkData.getAmusementParkId());
		amusementPark.setParkName(amusementParkData.getParkName());
		amusementPark.setParkAttractions(amusementParkData.getParkAttractions());
		amusementPark.setParkStars(amusementParkData.getParkStars());
		amusementPark.setParkSafetyScore(amusementParkData.getParkSafetyScore());
		amusementPark.setParkRegion(amusementParkData.getParkRegion());
	}
	
	private AmusementPark findOrCreateAmusementPark(Long amusementParkId) {
		AmusementPark amusementPark;
		
		if(Objects.isNull(amusementParkId)) {
			amusementPark = new AmusementPark();
		} else {
			amusementPark = findAmusementParkById(amusementParkId);
		}
		
		return amusementPark;
	}
	
	private AmusementPark findAmusementParkById(Long amusementParkId) {
		return amusementParkDao.findById(amusementParkId).orElseThrow(() 
				-> new NoSuchElementException("Amusement park with ID=" + amusementParkId + " does not exist."));
	}
	
	@Autowired
	private ParkEmployeeDao employeeDao;
	
	@Transactional(readOnly = false)
	public AmusementParkEmployee saveEmployee(Long amusementParkId, AmusementParkEmployee amusementParkEmployee) {
		AmusementPark amusementPark = findAmusementParkById(amusementParkId);
		Long employeeId = amusementParkEmployee.getEmployeeId();
		
		Employee employee = findOrCreateEmployee(employeeId, amusementPark);
		
		copyEmployeeFields(employee, amusementParkEmployee);
		
		employee.setAmusementPark(amusementPark);
		amusementPark.getEmployees().add(employee);
		
		return new AmusementParkEmployee(employeeDao.save(employee));
	}
	
	private void copyEmployeeFields(Employee employee, AmusementParkEmployee amusementParkEmployee) {
		employee.setEmployeeId(amusementParkEmployee.getEmployeeId());
		employee.setEmployeeFirstName(amusementParkEmployee.getEmployeeFirstName());
		employee.setEmployeeLastName(amusementParkEmployee.getEmployeeLastName());
		employee.setEmployeePhone(amusementParkEmployee.getEmployeePhone());
		employee.setEmployeePosition(amusementParkEmployee.getEmployeePosition());
	}
	
	private Employee findOrCreateEmployee(Long employeeId, AmusementPark amusementPark) {
		if(Objects.isNull(employeeId)) {
			return new Employee();
		} else {
			return findEmployeeById(employeeId, amusementPark);
		}
	}

	private Employee findEmployeeById(Long employeeId, AmusementPark amusementPark) {
		return employeeDao.findById(employeeId).orElseThrow(()
				-> new NoSuchElementException("Employee with ID=" + employeeId + " was not found."));
	}
	
	@Autowired
	private GuestDao guestDao;
	
	@Transactional(readOnly = false)
	public AmusementParkGuest saveGuest(Long amusementParkId, AmusementParkGuest amusementParkGuest) {
		AmusementPark amusementPark = findAmusementParkById(amusementParkId);
		Long guestId = amusementParkGuest.getGuestId();
		
		Guest guest = findOrCreateGuest(guestId, amusementParkId);
		
		copyGuestFields(guest, amusementParkGuest);
		
		amusementPark.getGuests().add(guest);
		
		return new AmusementParkGuest(guestDao.save(guest));
	}

	private void copyGuestFields(Guest guest, AmusementParkGuest amusementParkGuest) {
		guest.setGuestId(amusementParkGuest.getGuestId());
		guest.setGuestFirstName(amusementParkGuest.getGuestFirstName());
		guest.setGuestLastName(amusementParkGuest.getGuestLastName());
		guest.setGuestPhone(amusementParkGuest.getGuestPhone());
	}
	
	private Guest findOrCreateGuest(Long guestId, Long amusementParkId) {
		if (Objects.isNull(guestId)) {
			return new Guest();
		} else {
			return findGuestById(guestId, amusementParkId);
		}
	}

	private Guest findGuestById(Long guestId, Long amusementParkId) {
		Guest guest = guestDao.findById(guestId).orElseThrow(()
				-> new NoSuchElementException("Guest with ID=" + guestId + " was not found."));
		
		boolean found = false;
		
		for(AmusementPark amusementParks : guest.getAmusementParks()) {
			if(amusementParks.getAmusementParkId() == amusementParkId) {
				found = true;
			}
		}
		
		if(!found) {
			throw new IllegalArgumentException("Guest for the amusement park with ID=" + amusementParkId + " was not found.");
		} else {
			return guest;
		}
	}
	
	public List<AmusementParkData> getAllAmusementParks() {
		List<AmusementParkData> result = new LinkedList<>();
		List<AmusementPark> amusementParks = amusementParkDao.findAll();
		
		for(AmusementPark amusementPark : amusementParks) {
			AmusementParkData apd = new AmusementParkData(amusementPark);
			apd.getGuests().clear();
			apd.getEmployees().clear();
			
			result.add(apd);
		}
		
		return result;
	}
	
	public AmusementParkData getAmusementParkById(Long amusementParkId) {
		AmusementPark amusementPark = amusementParkDao.findById(amusementParkId).orElseThrow(()
				-> new NoSuchElementException("Amusement park with ID=" + amusementParkId + " was not found."));
		
		return new AmusementParkData(amusementPark);
	}
	
	public void deleteAmusementParkById(Long amusementParkId) {
		AmusementPark amusementPark = findAmusementParkById(amusementParkId);
		amusementParkDao.delete(amusementPark);
	}
}
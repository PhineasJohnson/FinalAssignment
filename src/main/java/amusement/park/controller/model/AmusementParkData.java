package amusement.park.controller.model;

import java.util.HashSet;
import java.util.Set;

import amusement.park.entity.AmusementPark;
import amusement.park.entity.Employee;
import amusement.park.entity.Guest;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class AmusementParkData {
	private Long amusementParkId;
	
	private String parkName;
	private Long parkAttractions;
	private Long parkStars;
	private Long parkSafetyScore;
	private String parkRegion;
	
	private Set<AmusementParkGuest> guests = new HashSet<>();
	private Set<AmusementParkEmployee> employees = new HashSet<>();
	
	public AmusementParkData(AmusementPark amusementPark) {
		amusementParkId = amusementPark.getAmusementParkId();
		parkName = amusementPark.getParkName();
		parkAttractions = amusementPark.getParkAttractions();
		parkStars = amusementPark.getParkStars();
		parkSafetyScore = amusementPark.getParkSafetyScore();
		parkRegion = amusementPark.getParkRegion();
		
		for(Guest guest : amusementPark.getGuests()) {
			guests.add(new AmusementParkGuest(guest));
		}
		
		for(Employee employee : amusementPark.getEmployees()) {
			employees.add(new AmusementParkEmployee(employee));
		}
	}
	
	@Data
	@NoArgsConstructor
	public static class AmusementParkGuest {
		private Long guestId;
		private String guestFirstName;
		private String guestLastName;
		private String guestPhone;
		
		public AmusementParkGuest(Guest guest) {
			guestId = guest.getGuestId();
			guestFirstName = guest.getGuestFirstName();
			guestLastName = guest.getGuestLastName();
			guestPhone = guest.getGuestPhone();
		}
	}
	
	@Data
	@NoArgsConstructor
	public static class AmusementParkEmployee {
		private Long employeeId;
		private String employeeFirstName;
		private String employeeLastName;
		private String employeePhone;
		private String employeePosition;
		
		public AmusementParkEmployee(Employee employee) {
			employeeId = employee.getEmployeeId();
			employeeFirstName = employee.getEmployeeFirstName();
			employeeLastName = employee.getEmployeeLastName();
			employeePhone = employee.getEmployeePhone();
			employeePosition = employee.getEmployeePosition();
		}
	}
}

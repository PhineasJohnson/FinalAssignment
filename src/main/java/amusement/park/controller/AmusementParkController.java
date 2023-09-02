package amusement.park.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import amusement.park.controller.model.AmusementParkData;
import amusement.park.controller.model.AmusementParkData.AmusementParkEmployee;
import amusement.park.controller.model.AmusementParkData.AmusementParkGuest;
import amusement.park.service.AmusementParkService;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/amusement_park")
@Slf4j
public class AmusementParkController {
	
	@Autowired
	private AmusementParkService amusementParkService;
	
	@PostMapping
	@ResponseStatus(code = HttpStatus.CREATED)
	public AmusementParkData saveAmusementPark(
			@RequestBody AmusementParkData amusementParkData) {
		
		log.info("Created", amusementParkData);
		return amusementParkService.saveAmusementPark(amusementParkData);
	}
	
	@PutMapping("/{amusementParkId}")
	public AmusementParkData updateAmusementPark(
			@PathVariable Long amusementParkId,
			@RequestBody AmusementParkData amusementParkData) {
		
		amusementParkData.setAmusementParkId(amusementParkId);
		
		log.info("Updated amusement park {}", amusementParkData);
		return amusementParkService.saveAmusementPark(amusementParkData);
	}
	
	@GetMapping
	public List<AmusementParkData> listAllAmusementParks() {
		return amusementParkService.getAllAmusementParks();
	}
	
	@GetMapping("/{amusementParkId}")
	public AmusementParkData getAmusementParkById(
			@PathVariable("amusementParkId") Long amusementParkId) {
		return amusementParkService.getAmusementParkById(amusementParkId);
	}
	
	@DeleteMapping("/{amusementParkId}/delete")
	public Map<String, String> deleteAmusementParkById(
			@PathVariable Long amusementParkId) {
		log.info("Deleting amusement park with ID={}", amusementParkId);
		
		return Map.of("message", "Successfully deleted amusement park with ID={}" + amusementParkId);
	}
	
	@PostMapping("/{amusementParkId}/guest")
	@ResponseStatus(code = HttpStatus.CREATED)
	public AmusementParkGuest addGuest(
			@PathVariable("amusementParkId") Long amusementParkId,
			@RequestBody AmusementParkGuest guest) {
		log.info("Adding guest to amusement park with ID={}", amusementParkId);
		return amusementParkService.saveGuest(amusementParkId, guest);
	}
	
	@PostMapping("/{amusementParkId}/employee")
	@ResponseStatus(code = HttpStatus.CREATED)
	public AmusementParkEmployee addEmployee(
			@PathVariable("amusementParkId") Long amusementParkId,
			@RequestBody AmusementParkEmployee employee) {
		log.info("Adding employee to amusement park with ID={}", amusementParkId);
		return amusementParkService.saveEmployee(amusementParkId, employee);
	}
}

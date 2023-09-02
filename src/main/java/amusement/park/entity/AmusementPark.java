package amusement.park.entity;

import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Entity
@Data
public class AmusementPark {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long amusementParkId;
	
	private String parkName;
	
	private Long parkAttractions;
	
	private Long parkStars;
	
	private Long parkSafetyScore;
	
	private String parkRegion;
	
	@EqualsAndHashCode.Exclude
	@ToString.Exclude
	@ManyToMany(cascade = CascadeType.PERSIST)
	@JoinTable(name = "park_guest", 
		joinColumns = @JoinColumn(name = "amusement_park_id"),
		inverseJoinColumns = @JoinColumn(name = "guest_id"))
	private Set<Guest> guests = new HashSet<>();
	
	@EqualsAndHashCode.Exclude
	@ToString.Exclude
	@OneToMany(mappedBy = "amusementPark", cascade = CascadeType.ALL, orphanRemoval = true)
	private Set<Employee> employees = new HashSet<>();
}

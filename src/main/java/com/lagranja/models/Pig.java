package com.lagranja.models;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@SuppressWarnings("unused")
@Entity
@Table(name = "pig")
public class Pig {
	
	
    public Pig() {
		super();
	}
    
    

	public Pig(Breed breed, int age, double weight, Client client) {
		super();
		this.breed = breed;
		this.age = age;
		this.weight = weight;
		this.client = client;
	}



	public Pig(Long id, Breed breed, int age, double weight, Client client, List<PigFeeding> feedings) {
		super();
		this.id = id;
		this.breed = breed;
		this.age = age;
		this.weight = weight;
		this.client = client;
		this.feedings = feedings;
	}


	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Breed breed;
    
    @Column(nullable = false)
    private int age;
    
    @Column(nullable = false)
    private double weight;
    
    @ManyToOne
    @JoinColumn(name = "client_id")
    @JsonBackReference
    private Client client;
    
    @OneToMany(mappedBy = "pig", cascade = CascadeType.ALL)
    private List<PigFeeding> feedings;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Breed getBreed() {
		return breed;
	}

	public void setBreed(Breed breed) {
		this.breed = breed;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public double getWeight() {
		return weight;
	}

	public void setWeight(double weight) {
		this.weight = weight;
	}

	public Client getClient() {
		return client;
	}

	public void setClient(Client client) {
		this.client = client;
	}

	public List<PigFeeding> getFeedings() {
		return feedings;
	}

	public void setFeedings(List<PigFeeding> feedings) {
		this.feedings = feedings;
	}
	
}
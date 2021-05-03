package it.polito.tdp.poweroutages.model;

import java.time.LocalDateTime;

public class PowerOutage {
	
	private Integer id;
	private Integer customersAffected;
	private Integer nerc_id;
	private LocalDateTime dataInizio;
	private LocalDateTime dataFine;
	
	public PowerOutage(Integer id, Integer customersAffected, LocalDateTime dataInizio, LocalDateTime dataFine, Integer nerc_id) {
		this.id = id;
		this.customersAffected = customersAffected;
		this.dataInizio = dataInizio;
		this.dataFine = dataFine;
		this.nerc_id=nerc_id;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getCustomersAffected() {
		return customersAffected;
	}

	public void setCustomersAffected(Integer customersAffected) {
		this.customersAffected = customersAffected;
	}

	public Integer getNerc_id() {
		return nerc_id;
	}

	public void setNerc_id(Integer nerc_id) {
		this.nerc_id = nerc_id;
	}

	public LocalDateTime getDataInizio() {
		return dataInizio;
	}

	public void setDataInizio(LocalDateTime dataInizio) {
		this.dataInizio = dataInizio;
	}

	public LocalDateTime getDataFine() {
		return dataFine;
	}

	public void setDataFine(LocalDateTime dataFine) {
		this.dataFine = dataFine;
	}

	public Integer getOreDisservizio() {
		
		Integer durata = dataFine.getHour()-dataInizio.getHour();
		
		return durata;
	}
	
	public String toString() {
		return this.id+ " "+ this.getOreDisservizio();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		PowerOutage other = (PowerOutage) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	
	
	
}

package it.polito.tdp.poweroutages.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import it.polito.tdp.poweroutages.DAO.PowerOutageDAO;

public class Model {
	
	private PowerOutageDAO podao;
	private List<PowerOutage> sequenzaMigliore;
	
	public Model() {
		podao = new PowerOutageDAO();
		
	}
	
	public List<Nerc> getNercList() {
		return podao.getNercList();
	}
	
	public List<PowerOutage> powerList(String nomeNerc) {
		return podao.getPowerForNerc(nomeNerc);
	}
	
	public List<PowerOutage> trovaSequenza (int anni, int ore,String nomeNerc) {
		
		List<PowerOutage> parziale = new ArrayList<>();
		sequenzaMigliore = null;
		
		cerca(parziale,0,anni,ore,nomeNerc);
		
		return sequenzaMigliore;
		
	}
	
	class ComparatoreParziale implements Comparator <PowerOutage> {
		@Override
		public int compare(PowerOutage p1, PowerOutage p2) {
			
			return p1.getDataInizio().getYear()-p2.getDataInizio().getYear();
		}
	}
	
	private void cerca (List<PowerOutage> parziale, int livello,int anni, int ore, String nomeNerc) {
		
		Integer totPersone = calcolaTot(parziale);
		if (sequenzaMigliore==null || totPersone>calcolaTot(sequenzaMigliore)) 
			sequenzaMigliore= new ArrayList<>(parziale);
		else {
			for (PowerOutage prova: podao.getPowerForNerc(nomeNerc)) {
				if (aggiungi(prova,parziale,anni,ore))
					parziale.add(prova);
					cerca(parziale,livello+1,anni,ore,nomeNerc);
					parziale.remove(parziale.size()-1);
			}
			
		}
		
	}
	
	public Integer calcolaTot(List<PowerOutage> parziale) {
		int sum =0;
		for (PowerOutage p: parziale)
			sum+=p.getCustomersAffected();
		
		return sum;
	}

	private boolean aggiungi (PowerOutage prova,List<PowerOutage> parziale, int anni, int ore) {
		int sum=0;
		Collections.sort(parziale, new ComparatoreParziale());
		if ((parziale.get(parziale.size()-1).getDataInizio().getYear()-parziale.get(1).getDataInizio().getYear())>anni)
			return false;
		for (PowerOutage p: parziale) {
			sum+= p.getOreDisservizio()+ prova.getOreDisservizio();
		}
		if (sum>ore)
			return false;
		
		return true;
	}

	
}

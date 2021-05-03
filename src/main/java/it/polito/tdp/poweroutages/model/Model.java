package it.polito.tdp.poweroutages.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import it.polito.tdp.poweroutages.DAO.PowerOutageDAO;

public class Model {
	
	private PowerOutageDAO podao;
	private List<PowerOutage> sequenzaMigliore;
	private List<PowerOutage> partenza;
	
	int totPersone;
	
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
		partenza = this.powerList(nomeNerc);
		Collections.sort(partenza, new ComparatoreParziale());
		sequenzaMigliore = null;
		totPersone =0;
		cerca(parziale,anni,ore);
		
		return sequenzaMigliore;
		
	}
	
	class ComparatoreParziale implements Comparator <PowerOutage> {
		@Override
		public int compare(PowerOutage p1, PowerOutage p2) {
			
			return p1.getDataInizio().getYear()-p2.getDataInizio().getYear();
		}
	}
	
	private void cerca (List<PowerOutage> parziale,int anni, int ore) {
		
		if (calcolaTot(parziale)>totPersone) {
			totPersone=calcolaTot(parziale);
			sequenzaMigliore = new ArrayList<>(parziale);
		}
		
		for (PowerOutage prova: partenza) {
			if (!parziale.contains(prova)) {
				parziale.add(prova);
			if (aggiungi(parziale,anni,ore)) {
					cerca(parziale,anni,ore);
				}
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

	private boolean aggiungi (List<PowerOutage> parziale, int anni, int ore) {
		int sum=0;
		
		if (parziale.size()>=2) {
		if ((parziale.get(parziale.size()-1).getDataInizio().getYear()-parziale.get(0).getDataInizio().getYear())>anni)
			return false;}
		for (PowerOutage p: parziale) {
			sum+= p.getOreDisservizio();
		}
		if (sum>ore)
			return false;
		
		return true;
	}

	
}

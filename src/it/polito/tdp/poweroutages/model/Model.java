package it.polito.tdp.poweroutages.model;

import java.time.Duration;
import java.time.Period;
import java.util.ArrayList;
import java.util.List;

import it.polito.tdp.poweroutages.db.PowerOutagesDAO;

public class Model {

	PowerOutagesDAO podao;
	private List<PowerOutage> powerOutages;
	private List<PowerOutage> best;
	private int totPeopleAffected;
	
	public Model() {
		podao = new PowerOutagesDAO();
	}
	
	public List<Nerc> getNercList() {
		return podao.getNercList();
	}

	public List<PowerOutage> findWorstCase(int nercId, int x, int y) {
		List<PowerOutage> parziale = new ArrayList<PowerOutage>();
		this.best = new ArrayList<PowerOutage>();
		totPeopleAffected = 0;
		
		powerOutages = podao.getPowerOutagesListByNerc(nercId);
		
		recursive(parziale, x, y);
	
		return best;
	}
	
	private void recursive(List<PowerOutage> parziale, int x, int y) {
		if(totPeopleAffected <= countPeopleAffected(parziale)) {
			best = new ArrayList<PowerOutage>(parziale);
			totPeopleAffected = countPeopleAffected(parziale);
		}
			
		for(PowerOutage prova : powerOutages) {
			if(!parziale.contains(prova)) {
				if(countYears(parziale) <= x && countHours(parziale) <= y) {
					parziale.add(prova);
					recursive(parziale, x, y);
					parziale.remove(parziale.size()-1);
				}
			}
		}
	}

	private int countPeopleAffected(List<PowerOutage> parziale) {
		int count = 0;
		
		for(PowerOutage po : parziale)
			count += po.getCustomersAffected();
		
		return count;
	}

	private int countHours(List<PowerOutage> parziale) {
		int count = 0;
		
		if(parziale.size() >= 2) {
			for(PowerOutage po : parziale)
				count += (int)(Duration.between(po.getDateEventBegan(), po.getDateEventFinished()).getSeconds()/3600);
		}
		
		return count;
	}

	private int countYears(List<PowerOutage> parziale) {
		if(parziale.size() >= 2)
			return Period.between(parziale.get(0).getDateEventBegan().toLocalDate(), parziale.get(parziale.size()-1).getDateEventBegan().toLocalDate()).getYears();
		else
			return 0;
	}
}

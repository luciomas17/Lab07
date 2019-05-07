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
	
	public Model() {
		podao = new PowerOutagesDAO();
	}
	
	public List<Nerc> getNercList() {
		return podao.getNercList();
	}

	public List<PowerOutage> findWorstCase(int nercId, int x, int y) {
		List<PowerOutage> parziale = new ArrayList<PowerOutage>();
		this.best = new ArrayList<PowerOutage>();
		
		powerOutages = podao.getPowerOutagesListByNerc(nercId);
		
		recursive(parziale, 0, x, y);
	
		return best;
	}
	
	private void recursive(List<PowerOutage> parziale, int level, int x, int y) {
		if(best == null || countPeopleAffected(best) < countPeopleAffected(parziale)) {
			best = new ArrayList<PowerOutage>(parziale);
			return;
		}
		
		for(PowerOutage prova : powerOutages) {
			if(!parziale.contains(prova)) {
				if(countYears(parziale, prova) <= x && countHours(parziale, prova) <= y) {
					parziale.add(prova);
					recursive(parziale, level+1, x, y);
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

	private int countHours(List<PowerOutage> parziale, PowerOutage prova) {
		int count = 0;
		
		if(parziale.size() > 0) {
			for(PowerOutage po : parziale)
				count += Duration.between(po.getDateEventBegan(), po.getDateEventFinished()).getSeconds()/3600;
		}
		
		count += Duration.between(prova.getDateEventBegan(), prova.getDateEventFinished()).getSeconds()/3600;
		
		return count;
	}

	private int countYears(List<PowerOutage> parziale, PowerOutage prova) {
		if(parziale.size() > 0)
			return Period.between(parziale.get(0).getDateEventBegan().toLocalDate(), prova.getDateEventBegan().toLocalDate()).getYears();
		else
			return 0;
	}
}

package it.polito.tdp.poweroutages.model;

import java.util.List;

import it.polito.tdp.poweroutages.db.PowerOutagesDAO;

public class Model {

	PowerOutagesDAO podao;
	
	public Model() {
		podao = new PowerOutagesDAO();
	}
	
	public List<Nerc> getNercList() {
		return podao.getNercList();
	}

}

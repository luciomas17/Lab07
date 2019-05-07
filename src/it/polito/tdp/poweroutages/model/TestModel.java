package it.polito.tdp.poweroutages.model;

import java.util.List;

public class TestModel {

	public static void main(String[] args) {
		
		Model model = new Model();
		
		List<PowerOutage> list = model.podao.getPowerOutagesListByNerc(8);
		
		for(PowerOutage po : list)
			System.out.println(po.toString());
	}
}

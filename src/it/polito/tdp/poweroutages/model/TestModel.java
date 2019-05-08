package it.polito.tdp.poweroutages.model;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.Period;
import java.util.List;

public class TestModel {

	public static void main(String[] args) {
		
		Model model = new Model();
		
		/*
		List<PowerOutage> list = model.podao.getPowerOutagesListByNerc(8);
		 
		for(PowerOutage po : list)
			System.out.println(po.toString());
		
		
		LocalDateTime inizio = list.get(0).getDateEventBegan();
		LocalDateTime fine = list.get(65).getDateEventBegan();
		
		int hours = (int) (Duration.between(inizio, fine).getSeconds()/3600);
		System.out.println("Ore: " + hours);
		
		int years = Period.between(inizio.toLocalDate(), fine.toLocalDate()).getYears();
		System.out.println("Anni: " + years);
		*/
		
		List<PowerOutage> powerOutages = model.findWorstCase(8, 4, 200);
		for(PowerOutage po : powerOutages)
			po.toString();
	}
}

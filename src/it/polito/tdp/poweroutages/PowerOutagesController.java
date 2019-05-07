package it.polito.tdp.poweroutages;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import it.polito.tdp.poweroutages.model.Model;
import it.polito.tdp.poweroutages.model.Nerc;
import it.polito.tdp.poweroutages.model.PowerOutage;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class PowerOutagesController {
	
	Model model = new Model();
	List<Nerc> nercs;

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private ComboBox<String> boxNerc;

    @FXML
    private TextField txtYears;

    @FXML
    private TextField txtHours;

    @FXML
    private TextArea txtResult;

    @FXML
    void doAnalysis(ActionEvent event) {
    	this.txtResult.clear();
    	
    	if(!this.boxNerc.getSelectionModel().isEmpty()) {
    		String nercValue = this.boxNerc.getSelectionModel().getSelectedItem();
    		int nercId = -1;
    		for(Nerc n : nercs) {
    			if(n.getValue().equals(nercValue)) {
    				nercId = n.getId();
    				break;
    			}
    		}
    		
    		try {
				List<PowerOutage> powerOutages = model.findWorstCase(nercId, Integer.parseInt(this.txtYears.getText()), 
						Integer.parseInt(this.txtHours.getText()));
				for(PowerOutage po : powerOutages)
					po.toString();
					
			} catch (NumberFormatException e) {
				this.txtResult.setText("Set correct input values!");
				e.printStackTrace();
			}
    		
    	} else
    		this.txtResult.setText("Select a NERC!");
    }

    @FXML
    void initialize() {
        assert boxNerc != null : "fx:id=\"boxNerc\" was not injected: check your FXML file 'PowerOutages.fxml'.";
        assert txtYears != null : "fx:id=\"txtYears\" was not injected: check your FXML file 'PowerOutages.fxml'.";
        assert txtHours != null : "fx:id=\"txtHours\" was not injected: check your FXML file 'PowerOutages.fxml'.";
        assert txtResult != null : "fx:id=\"txtResult\" was not injected: check your FXML file 'PowerOutages.fxml'.";
    }

	public void setModel(Model model) {
		this.model = model;

		// setting items for boxNerc
		nercs = new ArrayList<Nerc>();
		for(Nerc n : this.model.getNercList()) {
			nercs.add(new Nerc(n.getId(), n.getValue()));
			this.boxNerc.getItems().add(n.getValue());
		}
	}
	
	
}
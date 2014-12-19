package components;

import fr.seyara.ces.Component;

public class ExampleComponent extends Component {
	private String name = "Undefined";
	
	public ExampleComponent(String name){
		this.name = name;
	}
	
	public String getName(){
		return this.name;
	}
}

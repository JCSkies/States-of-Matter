import java.util.Scanner;

public class temperature {
	
	public static void main (String[] args) {
			 
		Scanner reader = new Scanner (System.in);
		
		System.out.println("Enter the mass of water, in g");
		double g = reader.nextDouble();		
		System.out.println("Enter the initial temperature, in C");
		double IT = reader.nextDouble();
		if (IT < -273.14)
			IT = -273.14;			
		System.out.println("Enter the final temperature, in C");
		double ET = reader.nextDouble();
		if (ET < -273.14)
			ET = -273.14;
			
		String IP = "liquid"; //what the string will way
		if (IT < 0)			//depending on the temperature
			IP = "solid";  //ip = initialphase ep = endingphase
		if (IT > 100)
			IP = "vapor";
		String EP = "liquid";
		if (ET < 0)
			EP = "solid";
		if (ET > 100)
			EP = "vapor";
		
			
		System.out.println("Mass: "+ g + "g");
		System.out.println("Starting temperature: " + IT + "C");
		System.out.println("Ending temperature: " + ET + "C");
		
		//booleans are good for two possibilities
		boolean endothermic = false;
		if (ET > IT)
			endothermic = true;	
			
		//! (testing string content), != (testing variable content) = not, the opposite of !=
				
		double HeatEnergy = 0; //total energy
		
		//IP = solid
		if(IP.equals("solid")) {a
			HeatEnergy += tempChangeSolid(g, IT, ET, EP, endothermic); //you don't need to call the type because it's declared in the main, needs to be redeclared in the support
			//the += adds to the value of HeatEnergy and reassigns
		
		if(!EP.equals("solid")) {
			HeatEnergy += fusion (g, endothermic); 
			HeatEnergy += tempChangeLiquid(g, 0, ET, EP, endothermic); //0 cause solidification
		}
		
		if(EP.equals("vapor")) {
			HeatEnergy += vaporization(g, endothermic);
			HeatEnergy += tempChangeVapor(g, 100, ET, EP, endothermic); //100 cause after boiling
		}
	}
		 //end of IP = solid
		
		//IP = liquid
		if(IP.equals("liquid")) {
			HeatEnergy += tempChangeLiquid(g, IT, ET, EP, endothermic);
		
		if(EP.equals("solid")) {
			HeatEnergy += fusion (g, endothermic);
			HeatEnergy += tempChangeSolid (g, 0, ET, EP, endothermic);
		}
		
		if(EP.equals("vapor")) {
			HeatEnergy += vaporization(g, endothermic);
			HeatEnergy += tempChangeVapor(g, 100, ET, EP, endothermic);
		}
	}
		
		//InitialPhase = vapor
		if(IP.equals("vapor")) {
			HeatEnergy += tempChangeVapor(g, IT, ET, EP, endothermic);
		
		if(!EP.equals("vapor")) {
			HeatEnergy += vaporization (g, endothermic);
			HeatEnergy += tempChangeLiquid(g, 100, ET, EP, endothermic);
			
		}
		
		if(EP.equals("solid")) {
			HeatEnergy += fusion(g, endothermic);
			HeatEnergy += tempChangeSolid(g, 0, ET, EP, endothermic);
	}	
}
		
		
		System.out.println("Total Heat Energy: " + HeatEnergy + "kJ"); //kJ is a string variable so it needs to be "kJ"
				
	}  //main ends here 
	//parameters are wahat you plug in, return is what you are solving for
	
	//public static creates supporting methods for the main
	
	public static double tempChangeSolid(double g, double initialTemp, double endingTemp, String endingPhase, boolean endothermic){ //these values pass through// 
		if(!endingPhase.equals("solid"))					//^^ this is if final phase is solid
			endingTemp = 0; //not solid ice means it's in melting phase 0C
		double energyChange = round(g*0.002108*(endingTemp	- initialTemp));	//() for precedence	
			if(endothermic)	//we don't need = true because boolean
				System.out.println("Heating (solid): " + energyChange + "kJ");
			else
				System.out.println("Cooling (solid): " + energyChange + "kJ");
			return energyChange; //after print we return 

	}
	
	public static double tempChangeVapor(double g, double initialTemp, double endingTemp, String endingPhase, boolean endothermic){ //these values pass through// 
		if(!endingPhase.equals("vapor"))					
			endingTemp = 100; 
		double energyChange = round(g*0.001996*(endingTemp	- initialTemp));	
			if(endothermic)	
				System.out.println("Heating (vapor): " + energyChange + "kJ");
			else
				System.out.println("Cooling (vapor): " + energyChange + "kJ");
			return energyChange; 
		
	}
		
	public static double tempChangeLiquid(double g, double initialTemp, double endingTemp, String endingPhase, boolean endothermic){ //these values pass through// 
		if(endingPhase.equals("solid"))					
			endingTemp = 0; 
		if(endingPhase.equals("vapor"))			//if endingPhase = liquid, it will be whatever value was plugged into the method				
			endingTemp = 100;						
		double energyChange = round(g*0.004184*(endingTemp	- initialTemp));	
			if(endothermic)	
				System.out.println("Heating (Liquid): " + energyChange + "kJ");
			else
				System.out.println("Cooling (Liquid): " + energyChange + "kJ");
			return energyChange; 
			
	}
	
	public static double fusion(double g, boolean endothermic) { 
		double energyChange; 	//every other energyChange is specifcally for their domain, hence {}
		if(endothermic) {  //same as if(endothermic)
			energyChange = round(0.333*g);
			System.out.println("Melting: " + energyChange + "kJ");
		} //covers everything subject ot the if, if there's more than one line needed
		else {
			energyChange = round(-0.333*g);
			System.out.println("Freezing: " + energyChange + "kJ");
		} 
		return energyChange;
	}
	
		public static double vaporization(double g, boolean endothermic) { 
		double energyChange; 	//every other energyChange is specifcally for their domain, hence {}
		if(endothermic) {  //same as if(endothermic)
			energyChange = round(2.257*g);
			System.out.println("Evaporation: " + energyChange + "kJ");
		} //covers everything subject ot the if, if there's more than one line needed
		else {
			energyChange = round(-2.257*g);
			System.out.println("Condensation: " + energyChange + "kJ");
		} 
		return energyChange;
	}			
	
	public static double round (double x) {
	x *= 1000;
	if(x > 0)
		return (int)(x + 0.5)/1000.0; //this is the roundm method
	else
		return (int)(x - 0.5)/1000.0;
		
	}
										
	//we have to use .equals to check a string, not ==//
	//supporting methods here
		}
 //class ends here



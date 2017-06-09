package search_algorithm;

import java.io.IOException;

import binary_predictor.PredictEnviroment;

import system_identification.ModelingBehaviour;
import trail_tracker.TrackTheTrail;

public class ProblemDescriptions {
   
   public static SearchProblem abRecognizer() throws IOException{
       SearchProblem sp = new ModelingBehaviour(3,
		SearchProblem.MOORE_MACHINE, true,
		"trainingData\\ab_moore.txt", "abRecognizer");
       return sp;
   }
   
   
   public static SearchProblem aabRecognizer() throws IOException{
       SearchProblem sp = new ModelingBehaviour(4,
		SearchProblem.MOORE_MACHINE, true,
		"trainingData\\aab_recognizer_Moore.txt", "aabRecognizer");
       return sp;
   }
   public static SearchProblem upDownCounter() throws IOException{
       SearchProblem sp = new ModelingBehaviour(4,
		SearchProblem.MOORE_MACHINE,false,
		"trainingData\\up_down_counter_Moore.txt", "UpDownCounter");
       //for S-CSR visualization is to huge
       return sp;
   }
   
   
   public static SearchProblem halves() throws IOException{
       SearchProblem sp = new ModelingBehaviour(4,
		SearchProblem.MOORE_MACHINE, true,
		"trainingData\\halves_moore.txt", "div2");
       return sp;
   }
  
   public static SearchProblem parityChecker() throws IOException{
       SearchProblem sp = new ModelingBehaviour(2,
		SearchProblem.MEALY_MACHINE,true,
		"trainingData\\parity_Checker_Mealy.txt", "parityChecker");
       return sp;
   }
   public static SearchProblem twoUnitDelay() throws IOException{
       SearchProblem sp = new ModelingBehaviour(4,
		SearchProblem.MEALY_MACHINE,true,
		"trainingData\\two_unit_delay_Mealy.txt", "twoUnitDelay");
       return sp;
   }
   
   
   
   public static SearchProblem ant1() throws IOException{
       SearchProblem sp =new TrackTheTrail(6, "trainingData\\JohnMuirTrail.txt","antJM");
       return sp;
   }
   public static SearchProblem ant2() throws IOException{
       SearchProblem sp =new TrackTheTrail(7, "trainingData\\SantaFeTrail.txt","antSF");
       return sp;
   }
   
   
   public static SearchProblem predictor11100() throws IOException{
       SearchProblem sp =new PredictEnviroment(4,"11100","11100Predictor");
       return sp;
   }
   
   public static SearchProblem predictor001111() throws IOException{
       SearchProblem sp =new PredictEnviroment(4,"001111","001111Predictor");
       return sp;
   }
   public static SearchProblem predictor1111010010111101001() throws IOException{
       SearchProblem sp =new PredictEnviroment(7," 1111010010111101001"," 1111010010111101001Predictor");
       return sp;
   }
   
   public static SearchProblem predictorMohhamed100() throws IOException{
       SearchProblem sp =new PredictEnviroment(5," 1"," Mohhamed100");
       return sp;
   }
   
  
   

}
package binary_predictor;

import java.io.IOException;

import representation.MeFST_S_CSR;
import search_algorithm.Point;
import search_algorithm.SearchProblem;


public class PredictEnviroment extends SearchProblem {
    BitMask mask;
    String environment = "";
    
    
    public PredictEnviroment (int n, String maskString, String name)  {
  	super();
  	mask = new BitMask(maskString);
  	this.environment=mask.generateWordWithLength(maskString.length()*2);
  	//this.environment=mask.generateWordWithLength(100);
  	//this.environment="10001111111110100011011001111000111110110011011100100100100011001000110111111001111011110111001001110101";
  	this.setName(name);
  	this.setN(n);
  	this.setAlpha(Predictor.inputs);
  	this.setBeta(Predictor.outputs);
  	this.setK(Predictor.inputs.getSizeOfAlphabet());
  	this.setType(SearchProblem.MEALY_MACHINE);
  	this.setM(Predictor.outputs.getSizeOfAlphabet());


      }
    @Override
    public double getPointScore(Point p) {
	int[] transition =p.coordinatesT;
	int[] output =p.coordinatesO;
	
	
	MeFST_S_CSR predictorRepresentation=	new MeFST_S_CSR(this.getN(),this.getK()) ;
	predictorRepresentation.setTransitionFunction(transition);
	predictorRepresentation.setOutputFunction(output);
	
	Predictor predictor=new Predictor();
	predictor.createPredictor(predictorRepresentation.decode(predictor.getInputAlphabet(), predictor.getOutputAlphabet()));
	predictor.evaluate(this.environment);

	return predictor.getScore();
    }

    @Override
    public void printSolution(Point p) throws IOException {
	int[] transition =p.coordinatesT;
	int[] output = p.coordinatesO;
	System.out.println("Printing "+p);
	
	MeFST_S_CSR predictorRepresentation=new MeFST_S_CSR(this.getN(),this.getK()) ;
	predictorRepresentation.setTransitionFunction(transition);
	predictorRepresentation.setOutputFunction(output);
	
	Predictor predictor=new Predictor();
	predictor.createPredictor(predictorRepresentation.decode(predictor.getInputAlphabet(), predictor.getOutputAlphabet()));
	predictor.evaluate(this.environment);
	System.out.println("Printing "+predictor.score);
	System.out.println("Printing "+predictor.getScore());

	predictor.printToFile("results\\"+ this.getName()+".html", this.environment);
	
    }
    
    

    public static void main(String[] args) throws IOException {
	PredictEnviroment predict = new PredictEnviroment(3,"11100","11100Predictor");

	int[] transition = { 0, 1, 0,2,2,1 };
	int[] output = { 1,1,0,1,1,0 };
	
	MeFST_S_CSR predictorRepresentation=	new MeFST_S_CSR(3,2) ;
	predictorRepresentation.setTransitionFunction(transition);
	predictorRepresentation.setOutputFunction(output);
	
	Predictor predictor11100=new Predictor();
	predictor11100.createPredictor(predictorRepresentation.decode(predictor11100.getInputAlphabet(), predictor11100.getOutputAlphabet()));
	predictor11100.evaluate(predict.environment);
	
	predictor11100.printToFile("results\\predictor11100.html", predict.environment);

    }
    public String getEnvironment() {
        return environment;
    }
    public void setEnvironment(String environment) {
        this.environment = environment;
    }

}

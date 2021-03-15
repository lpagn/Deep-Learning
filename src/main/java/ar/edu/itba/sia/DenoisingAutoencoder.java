package ar.edu.itba.sia;

import java.io.FileWriter;
import java.io.IOException;

public class DenoisingAutoencoder {
	private int nInputs, nHidden, nEpochs;
    private Neuron[] hiddenPerceptrons, inputPerceptrons,output;
    private double learning_rate;
    
    public double[] diff;
    public double[] out;

    public Neuron[] getOutput() {
        return output;
    }
    public DenoisingAutoencoder(int nInputs, int nHidden, int nEpochs, double learning_rate, double beta){
        this.nHidden = nHidden;
        this.nInputs = nInputs;
        this.nEpochs = nEpochs;
        this.learning_rate = learning_rate;

        hiddenPerceptrons   = new Neuron[nHidden];
        inputPerceptrons    = new Neuron[nInputs];
        output              = new Neuron[nInputs];
        //output = new Neuron(AutoencoderType.OUTPUT, nInputs, nHidden, beta);

        for(int i = 0;i<hiddenPerceptrons.length; i++){
            hiddenPerceptrons[i] = new Neuron(AutoencoderType.HIDDEN, nInputs, nHidden, beta);
        }
        for(int i = 0;i<inputPerceptrons.length;i++){
            inputPerceptrons[i] = new Neuron(AutoencoderType.INPUT, nInputs, nHidden, beta);
        }
        for(int i = 0;i<inputPerceptrons.length;i++){
            output[i] = new Neuron(AutoencoderType.OUTPUT, nInputs, nHidden, beta);
        }
    }
    public void train(double[] input){
        double sumOfWeights;
        for(int i = 0;i<inputPerceptrons.length;i++){
            inputPerceptrons[i].setOutput(input[i]);
        }

        for(int i = 0;i<hiddenPerceptrons.length;i++){
            sumOfWeights = dotProduct(hiddenPerceptrons[i].getWeights(),inputPerceptrons);
            hiddenPerceptrons[i].activationFunction(sumOfWeights);
        }

        for(int i = 0;i<output.length;i++){
            sumOfWeights = dotProduct(output[i].getWeights(),hiddenPerceptrons);
            output[i].activationFunction(sumOfWeights);
        }
    }

    private double dotProduct(double[] weights, Neuron[] perceptrons) {
        double sum = 0;

        for(int i = 0 ; i < perceptrons.length ; i++ ) {
            sum += weights[i] * perceptrons[i].getOutput();
        }

        return sum;
    }
    public void updateError(double[] expectedResult) {
        for (int i = 0; i < output.length; i++) {
            output[i].setError((expectedResult[i] - output[i].getOutput()) * (output[i].derivative()));
            output[i].setThreshold(output[i].getThreshold() + (learning_rate * output[i].getError()));
        }
        for (int j = 0;j<output.length;j++) {
            for (int i = 0; i < hiddenPerceptrons.length; i++) {
                output[j].updateWeights(i, learning_rate * output[j].getError() * hiddenPerceptrons[i].getOutput());
            }
        }

        for (int k = 0;k<output.length;k++) {
            for (int i = 0; i < hiddenPerceptrons.length; i++) {
                hiddenPerceptrons[i].setError(output[k].getWeights()[i] * output[k].getError() * hiddenPerceptrons[i].derivative());
                hiddenPerceptrons[i].setThreshold(hiddenPerceptrons[i].getThreshold() + (learning_rate * hiddenPerceptrons[i].getError()));
                for (int j = 0; j < inputPerceptrons.length; j++) {
                    hiddenPerceptrons[i].updateWeights(j, learning_rate * hiddenPerceptrons[i].getError() * inputPerceptrons[j].getOutput());
                }

            /*hiddenPerceptrons[i].updateWeights(learning_rate * hiddenPerceptrons[i].getError() * inputPerceptrons[0].getOutput(),
                    learning_rate * hiddenPerceptrons[i].getError() * inputPerceptrons[1].getOutput());*/
            }
        }

    }
    private void printArray(double[] input){
        for(int i = 0;i<input.length;i++){
            System.out.printf("%.2f ",input[i]);
        }
        System.out.println();
    }
    public boolean run(double[] input){
    	boolean hasLearned = true;
        //Print input
//        System.out.println("Input:");
//        printArray(input);

        double sumOfWeights;
        for(int i = 0;i<inputPerceptrons.length;i++){
            inputPerceptrons[i].setOutput(input[i]);
        }

        for(int i = 0;i<hiddenPerceptrons.length;i++){
            sumOfWeights = dotProduct(hiddenPerceptrons[i].getWeights(),inputPerceptrons);
            hiddenPerceptrons[i].activationFunction(sumOfWeights);
        }

        double[] outputValues = new double[35];
        for(int i = 0;i<output.length;i++){
            sumOfWeights = dotProduct(output[i].getWeights(),hiddenPerceptrons);
            outputValues[i] = output[i].activationFunction(sumOfWeights);
        }

        //print output
//        System.out.println("Output:");
//        printArray(outputValues);
        
        //System.out.println("Diff");
        double[] diffValues = new double[35];
        for(int i = 0;i<output.length;i++){
            diffValues[i] = Math.abs(outputValues[i] - input[i]);
            if(diffValues[i] > 0.09) {
            	hasLearned = false;
            }
        }
        diff = diffValues;
        out = outputValues;
//        printArray(diffValues);
        
        return hasLearned;
    }
    
    public String espacioLatente() {
    	
    	StringBuilder frame = new StringBuilder();
    	
    	StringBuilder a = new StringBuilder();
    	StringBuilder b = new StringBuilder();
    	
    	int N = this.hiddenPerceptrons.length;
    	int M = this.hiddenPerceptrons[0].weights.length;
    	
    	MatrixManager matrixManager = new MatrixManager(N,M);
    	
    	for(int i = 0 ; i < N ; i++) {
    		matrixManager.europe[i] = this.hiddenPerceptrons[i].weights;
    	}
    	
    	matrixManager.standarize();
    	
    	a.append(N * M).append("\n//\n");
    	b.append(N * M).append("\n\n");
    	//System.out.println(N + " " + M);
    	for(int i = 0 ; i < N ; i++) {
    		for(int j = 0 ; j < M ; j++) {
    			//System.out.print(matrixManager.europeStandarized[i][j] + "\t");
    			double d = matrixManager.europeStandarized[i][j];
    			b.append(i).append(" ").append(j).append(" ").append(d).append("\n");
    			String c;
    			if(d < 0.4) {
    				c = " 0 0 1";
    			}
    			else if(d > 0.6){
    				c = " 1 0 0";
    			}
    			else {
    				c = " 0 1 0";
    			}
    			a.append(i).append(" ").append(j).append(" ").append(c).append("\n");
    		}
    		//System.out.println();
    	}
    	
    	//System.out.println(a.toString());
    	write("a.txt",a.toString());
    	write("b.txt",b.toString());
    	//frame.append("//");
    	frame.append(a.toString());
    	//System.out.println(a.toString());
    	return frame.toString();
    }
    
    private void difference(double[] a,double[] b){
        for(int i = 0;i<a.length;i++){
            System.out.printf("%.2f ",Math.abs(a[i]-b[i]));
        }
        System.out.println();
    }
    
    static void write (String filename , String value) {
     	try {
   	      FileWriter myWriter = new FileWriter(filename);
   	      myWriter.write(value);
   	      myWriter.close();
   	    } catch (IOException e) {
   	      System.out.println("An error occurred.");
   	      e.printStackTrace();
   	    }
     }
}

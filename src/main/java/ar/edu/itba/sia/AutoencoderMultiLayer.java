package ar.edu.itba.sia;

public class AutoencoderMultiLayer {
    private int nInputs, nHidden, nEpochs;
    private Neuron[] hiddenPerceptrons, inputPerceptrons,output;
    private double learning_rate;

    private Neuron[] nHiddenL, nHidderR;

    public Neuron[] getOutput() {
        return output;
    }
    public AutoencoderMultiLayer(int nInputs, int nHidden,int nMid, int nEpochs, double learning_rate, double beta){
        this.nHidden = nHidden;
        this.nInputs = nInputs;
        this.nEpochs = nEpochs;
        this.learning_rate = learning_rate;

        hiddenPerceptrons   = new Neuron[nHidden];
        inputPerceptrons    = new Neuron[nInputs];
        output              = new Neuron[nInputs];

        for(int i = 0;i<hiddenPerceptrons.length; i++){
            hiddenPerceptrons[i] = new Neuron(AutoencoderType.HIDDEN, nInputs, nHidden,nMid, beta);
        }
        for(int i = 0;i<inputPerceptrons.length;i++){
            inputPerceptrons[i] = new Neuron(AutoencoderType.INPUT, nInputs, nHidden,nMid, beta);
        }
        for(int i = 0;i<inputPerceptrons.length;i++){
            output[i] = new Neuron(AutoencoderType.OUTPUT, nInputs, nHidden,nMid, beta);
        }

        nHiddenL    = new Neuron[nMid];
        nHidderR    = new Neuron[nMid];

        for(int i = 0;i<nHiddenL.length; i++){
            nHiddenL[i] = new Neuron(AutoencoderType.HIDDENL, nInputs, nHidden,nMid, beta);
        }
        for(int i = 0;i<nHidderR.length;i++){
            nHidderR[i] = new Neuron(AutoencoderType.HIDDENR, nInputs, nHidden,nMid, beta);
        }
    }
    public void train(double[] input){
        double sumOfWeights;
        //input = normalize(input);
        for(int i = 0;i<inputPerceptrons.length;i++){
            inputPerceptrons[i].setOutput(input[i]);
        }

        for(int i = 0;i<nHiddenL.length;i++){
            sumOfWeights = dotProduct(nHiddenL[i].getWeights(),inputPerceptrons);
            nHiddenL[i].activationFunction(sumOfWeights);
        }

        for(int i = 0;i<hiddenPerceptrons.length;i++){
            sumOfWeights = dotProduct(hiddenPerceptrons[i].getWeights(),nHiddenL);
            hiddenPerceptrons[i].activationFunction(sumOfWeights);
        }
        for(int i = 0;i<nHidderR.length;i++){
            sumOfWeights = dotProduct(nHidderR[i].getWeights(),hiddenPerceptrons);
            nHidderR[i].activationFunction(sumOfWeights);
        }

        for(int i = 0;i<output.length;i++){
            sumOfWeights = dotProduct(output[i].getWeights(),nHidderR);
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
        //expectedResult = normalize(expectedResult);
        for (int i = 0; i < output.length; i++) {
            output[i].setError((expectedResult[i] - output[i].getOutput()) * (output[i].derivative()));
            for (int j = 0; j < nHidderR.length; j++) {
                output[i].updateWeights(j, learning_rate * output[i].getError() * nHidderR[j].getOutput());
            }
        }

        for (int k = 0;k<output.length;k++) {
            for (int i = 0; i < nHidderR.length; i++) {
                nHidderR[i].setError(output[k].getWeights()[i] * output[k].getError() * nHidderR[i].derivative());
                for (int j = 0; j < hiddenPerceptrons.length; j++) {
                    nHidderR[i].updateWeights(j, learning_rate * nHidderR[i].getError() * hiddenPerceptrons[j].getOutput());
                }
            }
        }

        for (int k = 0;k<nHidderR.length;k++) {
            for (int i = 0; i < hiddenPerceptrons.length; i++) {
                hiddenPerceptrons[i].setError(nHidderR[k].getWeights()[i] * nHidderR[k].getError() * hiddenPerceptrons[i].derivative());
                for (int j = 0; j < nHiddenL.length; j++) {
                    hiddenPerceptrons[i].updateWeights(j, learning_rate * hiddenPerceptrons[i].getError() * nHiddenL[j].getOutput());
                }
            }
        }

        for (int k = 0;k<hiddenPerceptrons.length;k++) {
            for (int i = 0; i < nHiddenL.length; i++) {
                nHiddenL[i].setError(hiddenPerceptrons[k].getWeights()[i] * hiddenPerceptrons[k].getError() * nHiddenL[i].derivative());
                for (int j = 0; j < inputPerceptrons.length; j++) {
                    nHiddenL[i].updateWeights(j, learning_rate * nHiddenL[i].getError() * inputPerceptrons[j].getOutput());
                }
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
        //input = normalize(input);
        boolean hasLearned = true;
        //Print input
        //System.out.println("Input:");
        //printArray(input);

        double sumOfWeights;
        for(int i = 0;i<inputPerceptrons.length;i++){
            inputPerceptrons[i].setOutput(input[i]);
        }

        for(int i = 0;i<nHiddenL.length;i++){
            sumOfWeights = dotProduct(nHiddenL[i].getWeights(),inputPerceptrons);
            nHiddenL[i].activationFunction(sumOfWeights);
        }

        for(int i = 0;i<hiddenPerceptrons.length;i++){
            sumOfWeights = dotProduct(hiddenPerceptrons[i].getWeights(),nHiddenL);
            hiddenPerceptrons[i].activationFunction(sumOfWeights);
        }
        for(int i = 0;i<nHidderR.length;i++){
            sumOfWeights = dotProduct(nHidderR[i].getWeights(),hiddenPerceptrons);
            nHidderR[i].activationFunction(sumOfWeights);
        }

        double[] outputValues = new double[35];
        for(int i = 0;i<output.length;i++){
            sumOfWeights = dotProduct(output[i].getWeights(),nHidderR);
            outputValues[i] = output[i].activationFunction(sumOfWeights);
        }

        //print output
        //System.out.println("Output:");
        //printArray(outputValues);

        //System.out.println("Diff");
        double[] diffValues = new double[35];
        for(int i = 0;i<output.length;i++){
            diffValues[i] = Math.abs(outputValues[i] - input[i]);
            if(diffValues[i] > 0.09) {
                hasLearned = false;
            }
        }
        printArray(diffValues);
        return hasLearned;
    }

    public void espacioLatente() {

        StringBuilder a = new StringBuilder();

        for(int i = 0 ; i < this.hiddenPerceptrons.length ; i++) {
            for(int j = 0 ; j < this.hiddenPerceptrons[i].weights.length ; j++) {
                System.out.print(this.hiddenPerceptrons[i].weights[j] + "\t");
                a.append(i).append(" ").append(j).append(" ").append(this.hiddenPerceptrons[i].weights[j]).append("\n");
            }
            System.out.println();
        }

        System.out.println(a.toString());

    }
    private void difference(double[] a,double[] b){
        for(int i = 0;i<a.length;i++){
            System.out.printf("%.2f ",Math.abs(a[i]-b[i]));
        }
        System.out.println();
    }

    private double[] normalize(double[] matrix){
        double[] array = new double[matrix.length];
        for(int i = 0;i<matrix.length;i++){
            if(matrix[i] == 0.0)
                array[i] = -1.0;
            else
                array[i] = matrix[i];
        }
        return array;
    }

    public void generateRandom(){

    }
}

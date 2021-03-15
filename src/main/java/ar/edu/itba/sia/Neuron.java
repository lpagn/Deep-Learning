package ar.edu.itba.sia;

import java.util.Random;

public class Neuron {
    private AutoencoderType type;
    public double[]  weights;
    private double threshold = .1, error = 0, output = 0, beta;

    public Neuron(AutoencoderType type, int nInput, int nHidden, double beta){
        this.type = type;
        this.beta = beta;
        int size=1;
        switch (type){
            case OUTPUT:
                size = nHidden;
                weights = new double[size];
                break;
            case HIDDEN:
                size = nInput;
                weights = new double[size];
                break;
            case INPUT:
            default:
                size = 2;
                weights = new double[size];
        }

        for (int i = 0;i< size;i++){
            weights[i] = 0.0 - Math.random();
        }
    }

    public Neuron(AutoencoderType type, int nInput, int nHidden,int nMid, double beta){
        this.type = type;
        this.beta = beta;
        int size=1;
        switch (type){
            case OUTPUT:
            case HIDDEN:
                size = nMid;
                weights = new double[size];
                break;
            case HIDDENR:
                size = nHidden;
                weights = new double[size];
                break;
            case HIDDENL:
                size = nInput;
                weights = new double[size];
                break;
            case INPUT:
            default:
                size = 2;
                weights = new double[size];
        }

        for (int i = 0;i< size;i++){
            weights[i] = Math.random();
        }
    }

    public double derivative(){
        return beta*(1-Math.pow(Math.tanh(beta*output),2));
        //return derivadaSigmoide(output);
    }
    public double[] getWeights() {
        return weights;
    }
    public double getThreshold() {
        return threshold;
    }
    public void setThreshold(double threshold) {
        this.threshold = threshold;
    }
    public double getError() {
        return error;
    }
    public void setError(double error) {
        this.error = error;
    }
    public double getOutput() {
        return output;
    }
    public void setOutput(double output) {
        this.output = output;
    }
    public double activationFunction(double sum){
        output = Math.tanh(beta * sum);
        //output = sigmoide(sum);
        //output = Math.max(0,sum);
        //output = 2 * beta * (sigmoide(sum) * (1 - sigmoide(sum)));
        return output;
    }
    public void updateWeights(int index, double deltaW){
        weights[index] += deltaW;
    }
    private double sigmoide(double sum){
        return 1.0/(1+Math.exp(-2*sum*beta));
    }
    private double derivadaSigmoide(double sum){
        double xx = sigmoide(sum);
        //return xx * (1-xx);
        return 2 * beta * (sigmoide(sum) * (1 - sigmoide(sum)));
        /*if(sum <0)
            return 0;
        return 1;*/

    }
    private double sacudida(double deltaW){
        double random = Math.random();
        if(random < 0.1){
            //Cambio
            if(deltaW>0){
                return 0.1;
            }
            return -0.1;
        }
        return 0;
    }

}

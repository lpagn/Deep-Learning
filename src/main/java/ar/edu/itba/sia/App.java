package ar.edu.itba.sia;

import java.io.FileWriter;
import java.io.IOException;
import static java.lang.Math.round;

/**
 * Hello world!
 *
 */
public class App {

	//Max 32
	private static int TRAINING_SET = 32;
    private static int MAX_ITERATIONS = 100000;
	private static boolean skip = true;
	static FileWriter myWriter;

    static {
        try {
            myWriter = new FileWriter("movie.txt");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main( String[] args ) {
        System.out.println( "SIATP5");
        System.out.println("Autoencoder");
        System.out.println("Training set = " + TRAINING_SET);

        Fonts fonts = new Fonts();
        double [] arr = fonts.print(1);
        
        printArray(arr);
        long start = System.currentTimeMillis();

        //fonts.printAll();
        //Autoencoder autoencoder = new Autoencoder(35, 10,300000, 0.2,0.1);
        Autoencoder autoencoder = new Autoencoder(35, 15,1000, 1,0.5);
        int j;
        for (j = 0 ;finishCondition(j);j++){
            boolean[] learnedArray = new boolean[TRAINING_SET];
            for (int i = 0;i<TRAINING_SET;i++){
                autoencoder.train(fonts.print(i));
                autoencoder.updateError(fonts.print(i));
                //multiLayerPerceptronGeneral.updateError(data[i][1][0]);
            }
            //append("movie.txt",autoencoder.espacioLatente());
            //autoencoder.espacioLatente();
            for (int i = 0;i<TRAINING_SET;i++){
                learnedArray[i] = autoencoder.run(fonts.print(i));
            }
            if(learned(learnedArray)){
                changeCondition(false);
                printArray(learnedArray);
            }
        }
        
        
        int correctos = 0;
        
        for (int i = 0;i<TRAINING_SET;i++){
            //printArray(fonts.print(i));
            boolean ret = autoencoder.run(fonts.print(i));
            System.out.println(ret);
            if(ret) {
            	correctos ++;
            }
            //multiLayerPerceptronGeneral.updateError(data[i][1][0]);
        }
        
        double precision = correctos / TRAINING_SET;
        System.out.println("Correctos: " + correctos);
        System.out.println("Training Set: " + TRAINING_SET);
        System.out.println("Precision: " + precision);
        System.out.println("Cantidad de pasos: " + j);

        autoencoder.espacioLatente();
        long elapsedTime = System.currentTimeMillis() - start;
        System.out.println("Time spent: " + elapsedTime);
        //autoencoder.run(fonts.print(0));

        //Una vez que aprendio
        for(int i = 0;i<TRAINING_SET;i++){
            autoencoder.updateRangeHidden(fonts.print(i));
        }
        printArray(denormalize(autoencoder.generateRandom()));
        printArray(denormalize(autoencoder.generateRandom()));
        printArray(denormalize(autoencoder.generateRandom()));
        printArray(denormalize(autoencoder.generateRandom()));


    }

    private static double[] denormalize(double[] input){
        double[] ret = new double[input.length];
        for(int i = 0;i<input.length;i++){
            if(input[i] < 0.5)
                ret[i] = 0;
            else
                ret[i] = 1;
        }
        return ret;
    }

    private static void printMatrix(double[] normal) {
        for(int i = 0 ; i < normal.length ; i++) {
            System.out.print((int)normal[i] + " ");
            if(i % 5 == 4) {
                System.out.println();
            }
        }
        System.out.println();
    }

    private static boolean learned(boolean[] learnedArray) {
        for(int i = 0;i<learnedArray.length;i++){
            if(!learnedArray[i])
                return false;
        }
        return true;
    }
    private static boolean finishCondition(int iteration){
        /*if(iteration<MAX_ITERATIONS)
            return true;
        return false;*/
        return skip;
    }
    private static void changeCondition(boolean val){
        skip = val;
    }

    private static void printArray(double[] input){
        for(int i = 0;i<input.length;i++){
            if(i%5 == 0)
                System.out.println();
            System.out.printf("%d ",round(input[i]));
        }
        System.out.println();
    }
    private static void printArray(boolean[] input){
        for(int i = 0;i<input.length;i++){
            System.out.print(input[i] + " ");
        }
        System.out.println();
    }
    private static void printArray(int[] input){
        for(int i = 0;i<input.length;i++){
            System.out.print(input[i] + " ");
        }
        System.out.println();
    }
    
    static void append (String filename , String value) {
     	try {
   	      myWriter.append(value);
   	      //myWriter.close();
   	    } catch (IOException e) {
   	      System.out.println("An error occurred.");
   	      e.printStackTrace();
   	    }
     }
    
}

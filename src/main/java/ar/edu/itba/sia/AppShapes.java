package ar.edu.itba.sia;

import java.io.FileWriter;
import java.io.IOException;

import static java.lang.Math.round;

public class AppShapes {
    private static int TRAINING_SET = 11;

    public static void main( String[] args ) {
        System.out.println( "SIATP5");
        System.out.println("Autoencoder");
        Shapes fonts = new Shapes();

        long start = System.currentTimeMillis();

        //fonts.printAll();
        //Autoencoder autoencoder = new Autoencoder(35, 10,300000, 0.2,0.1);
        Autoencoder autoencoder = new Autoencoder(25, 10,1000, 0.5,0.5);
        boolean skip = true;
        for (int j = 0 ;skip;j++){
            boolean[] learnedArray = new boolean[TRAINING_SET];
            for (int i = 0;i<TRAINING_SET;i++){
                autoencoder.train(fonts.getShape(i));
                autoencoder.updateError(fonts.getShape(i));
                //append("movie.txt",autoencoder.espacioLatente());
                //multiLayerPerceptronGeneral.updateError(data[i][1][0]);
            }
            for (int i = 0;i<TRAINING_SET;i++){
                learnedArray[i] = autoencoder.run(fonts.getShape(i));
            }
            if(learned(learnedArray)){
                skip=false;
                printArray(learnedArray);
            }
        }


        int correctos = 0;

        for (int i = 0;i<TRAINING_SET;i++){
            //printArray(fonts.print(i));
            boolean ret = autoencoder.run(fonts.getShape(i));
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

        autoencoder.espacioLatente();
        long elapsedTime = System.currentTimeMillis() - start;
        System.out.println("Time spent: " + elapsedTime);

        //Una vez que aprendio
        printArray(denormalize(autoencoder.generateRandom()));
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


}

package ar.edu.itba.sia;

import static java.lang.Math.round;

public class AppMultiLayer {

    private static int TRAINING_SET = 2;

    public static void main( String[] args ) {
        System.out.println( "SIATP5");
        System.out.println("Autoencoder");
        Fonts fonts = new Fonts();
        double [] arr = fonts.print(1);
        long start = System.currentTimeMillis();

        //fonts.printAll();
        //Autoencoder autoencoder = new Autoencoder(35, 10,300000, 0.2,0.1);
        AutoencoderMultiLayer autoencoder = new AutoencoderMultiLayer(35, 15,10,1000, 1,1);
        boolean skip = true;
        for (int j = 0 ;skip;j++){
            boolean[] learnedArray = new boolean[TRAINING_SET];
            for (int i = 0;i<TRAINING_SET;i++){
                autoencoder.train(fonts.print(i));
                autoencoder.updateError(fonts.print(i));
                //append("movie.txt",autoencoder.espacioLatente());
                //multiLayerPerceptronGeneral.updateError(data[i][1][0]);
            }
            for (int i = 0;i<TRAINING_SET;i++){
                learnedArray[i] = autoencoder.run(fonts.print(i));
            }
            if(learned(learnedArray)){
                skip=false;
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

        autoencoder.espacioLatente();
        long elapsedTime = System.currentTimeMillis() - start;
        System.out.println("Time spent: " + elapsedTime);

        //Una vez que aprendio
        //printArray(autoencoder.generateRandom());
        //printArray(autoencoder.generateRandom());
        //printArray(autoencoder.generateRandom());
        //printArray(autoencoder.generateRandom());



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
    private static boolean learned(boolean[] learnedArray) {
        for(int i = 0;i<learnedArray.length;i++){
            if(!learnedArray[i])
                return false;
        }
        return true;
    }
}

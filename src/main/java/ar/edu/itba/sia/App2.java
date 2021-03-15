package ar.edu.itba.sia;

import java.io.IOException;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Hello world!
 *
 */
public class App2 
{

	//Max 32
	private static int TRAINING_SET = 3;
	
	static FileWriter myWriter;

    static {
        try {
            myWriter = new FileWriter("movie.txt");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main( String[] args )
    {
        System.out.println( "SIATP5");
        Fonts fonts = new Fonts();
        double [] arr = fonts.print(1);

        long start = System.currentTimeMillis();

        //fonts.printAll();
        //Autoencoder autoencoder = new Autoencoder(35, 10,300000, 0.2,0.1);
        AutoencoderMultiLayer autoencoder = new AutoencoderMultiLayer(35, 20,30,1000, 2,2);
        boolean skip = true;
        for (int j = 0 ;skip;j++){
            boolean[] learnedArray = new boolean[TRAINING_SET];
            for (int i = 0;i<TRAINING_SET;i++){
                autoencoder.train(fonts.print(i));
                autoencoder.updateError(fonts.print(i));
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

        System.out.println("DenoisingAutoencoder");
        //double [] arr = fonts.print(1);
        
        //printArray(arr);
        
        //fonts.printAll();
        //Autoencoder autoencoder = new Autoencoder(35, 10,300000, 0.2,0.1);
        DenoisingAutoencoder denoisingautoencoder = new DenoisingAutoencoder(35, 32,1000, 0.5,0.8);
        
        StringBuilder movie = new StringBuilder();
        for (int j = 0 ;j<10000;j++){
            for (int i = 0;i<TRAINING_SET;i++){
            	denoisingautoencoder.train(fonts.print(i));
            	denoisingautoencoder.updateError(fonts.print(i));
                //append("movie.txt",autoencoder.espacioLatente());
                //multiLayerPerceptronGeneral.updateError(data[i][1][0]);
            }
        }

        for (int i = 0;i<TRAINING_SET;i++){
            //printArray(fonts.print(i));
            boolean ret = denoisingautoencoder.run(fonts.print(i));
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

        //autoencoder.espacioLatente();
        long elapsedTime = System.currentTimeMillis() - start;
        System.out.println("Time spent: " + elapsedTime);

        denoisingautoencoder.espacioLatente();
        
        //----------------------------------//
        System.out.println("Denoiser");
        double [] normal = fonts.print(1);
        printMatrix(normal);
        double [] ruido  = fonts.printWithNoise(1);
        System.out.println("Ruido");
        printMatrix(ruido);
        boolean ret = denoisingautoencoder.run(ruido);
        
        System.out.println(ret);
        
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

    private static void printArray(double[] input){
        for(int i = 0;i<input.length;i++){
            System.out.print(input[i] + " ");
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
private static boolean learned(boolean[] learnedArray) {
        for(int i = 0;i<learnedArray.length;i++){
        if(!learnedArray[i])
        return false;
        }
        return true;
        }
}

package ar.edu.itba.sia;

public class MatrixManager {
	double [][] europe;
	
	double[][] aux;
	
	double[][] europeStandarized;
	
	int N;
	int M;
	
	public MatrixManager(int N , int M) {
		this.N = N;
		this.M = M;
		europe = new double[N][M];
		aux = new double[M][N];
		europeStandarized = new double[N][M];
	}
	
	public void standarize() {
		
		for(int i = 0 ; i < M ; i++) {
			for(int j = 0 ; j < N ; j++) {
				aux[i][j] = europe[j][i];
			}
		}
		
		for(int i = 0 ; i < M ; i++) {
			double max = max(aux[i]); 
			double min = min(aux[i]);
			for(int j = 0 ; j < N ; j++) {
				aux[i][j] = (aux[i][j] - min) / (max - min);
			}
		}
		
		for(int i = 0 ; i < N ; i++) {
			for(int j = 0 ; j < M ; j++) {
				europeStandarized[i][j] = aux[j][i];
			}
		}
		
	}
	
	public void standarize2() {
		for(int i = 0 ; i < M ; i++) {
			for(int j = 0 ; j < N ; j++) {
				aux[i][j] = europe[j][i];
			}
		}
		
		for(int i = 0 ; i < M ; i++) {
			double mean   = mean(aux[i]); 
			double stddev = stddev(aux[i]);
			for(int j = 0 ; j < N ; j++) {
				aux[i][j] = (aux[i][j] - mean) /stddev;
			}
		}
		
		for(int i = 0 ; i < N ; i++) {
			for(int j = 0 ; j < M ; j++) {
				europeStandarized[i][j] = aux[j][i];
			}
		}
	}
	
	public double mean(double x[]) {
		double sum = 0;
		for(int i = 0 ; i < x.length ; i++){
			sum += x[i];
		}
		return sum/x.length;
	}
	
	public double stddev(double x[]) {
		double sum = 0;
		double u = mean(x);
		for(int i = 0 ; i < x.length ; i++) {
			sum += Math.pow((x[i]-u),2);
		}
		double ret = Math.sqrt(sum/x.length);
		return ret;
	}
	
	public double max(double x[]) {
		double max = 0;
		for(int i = 0 ; i < x.length ; i++) {
			if(x[i] > max) {
				max = x[i];
			}
		}
		return max;
	}
	
	public double min(double x[]) {
		double min = Double.MAX_VALUE;
		for(int i = 0 ; i < x.length ; i++) {
			if(x[i] < min) {
				min = x[i];
			}
		}
		return min;
	}
		
	public String print() {
		StringBuilder ret = new StringBuilder();
		
		for(int i = 0 ; i < europe.length ; i++) {
			for(int j = 0 ; j < europe[i].length ; j++) {
				ret.append(europe[i][j]);
				ret.append("\t");
			}
			ret.append("\n");
		}
		
		return ret.toString();
	}
	
	public String printStandarized() {
		StringBuilder ret = new StringBuilder();
		
		for(int i = 0 ; i < europeStandarized.length ; i++) {
			for(int j = 0 ; j < europeStandarized[i].length ; j++) {
				ret.append(String.format("%.2f",europeStandarized[i][j]));
				ret.append("\t");
			}
			ret.append("\n");
		}
		
		return ret.toString();
	}
}

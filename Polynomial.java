import java.io.*;
import java.util.*;
public class Polynomial {
    double[] coefficients;
	int[] exponents;

    public Polynomial(){
        this.coefficients = new double[] {0};
		this.exponents = new int[] {0};
	}
	
	public Polynomial(double[] coefficients, int[] exponents){
            this.coefficients = coefficients;
			this.exponents = exponents;
	}
	
    public Polynomial add(Polynomial p){
		int max_exponent = Math.max(this.exponents[this.exponents.length - 1], p.exponents[p.exponents.length - 1]);
		double[] sum_coefficients = new double[max_exponent + 1];
		int j = 0, k = 0;
		for (int i = 0; i <= max_exponent; i++) {
			double this_coefficient = 0;
			double p_coefficient = 0;
			if (j < this.exponents.length && this.exponents[j] == i) {
				this_coefficient = this.coefficients[j];
				j++;
			}
			if (k < p.exponents.length && p.exponents[k] == i) {
				p_coefficient = p.coefficients[k];
				k++;
			}
			sum_coefficients[i] = this_coefficient + p_coefficient;
		}
		int count = 0;
		for (double coefficient : sum_coefficients) {
			if (coefficient != 0) 
				count++;
		}
		double[] final_coefficients = new double[count];
		int[] final_exponents = new int[count];
		for (int i = 0, l = 0; i <= max_exponent; i++) {
			if (sum_coefficients[i] != 0) {
				final_coefficients[l] = sum_coefficients[i];
				final_exponents[l] = i;
				l++;
			}
		}
		return new Polynomial(final_coefficients, final_exponents);
    }

    public double evaluate(double x){
		double result = 0;
        for (int i = 0; i < coefficients.length; i++){
            result += coefficients[i]*(Math.pow(x,exponents[i]));
        }
		if (result == (int) result) 
			return (int) result;
		return result;
    }

    public boolean hasRoot(double root){
        return evaluate(root) == 0;
    }

	public Polynomial multiply(Polynomial p){
		HashMap<Integer, Double> result = new HashMap<Integer, Double>();
		for (int i = 0; i < this.coefficients.length; i++){
			for (int j = 0; j < p.coefficients.length; j++){
				int new_exponent = this.exponents[i] + p.exponents[j];
				double new_coefficient = this.coefficients[i] * p.coefficients[j];
				result.put(new_exponent, result.getOrDefault(new_exponent, 0.0) + new_coefficient);
			}		
		}
		int length = result.size();
		int[] new_exponents = new int[length];
		double[] new_coefficients = new double[length];
		int i = 0;
		for (Integer j : result.keySet()){
			new_exponents[i] = j;
			new_coefficients[i] = result.get(j);
			i++;
		}
		return new Polynomial(new_coefficients, new_exponents);
	}

	public Polynomial(File file) throws IOException {
        BufferedReader input = new BufferedReader(new FileReader(file));
        String line = input.readLine();
        input.close();
        ArrayList<Double> coefficients_list = new ArrayList<Double>();
        ArrayList<Integer> exponents_list = new ArrayList<Integer>();
        String[] terms = line.split("(?=[+-])");
        for (String term : terms) {
            if (term.contains("x")) {
                String[] parts = term.split("x");
				double coefficient;
				if (parts[0].isEmpty() || parts[0].equals("+")){
					coefficient = 1;
				} else if (parts[0].equals("-")){
					coefficient = -1;
				} else {
					coefficient = Double.parseDouble(parts[0]);
				}
				int exponent;
				if (parts.length == 2){
					exponent = Integer.parseInt(parts[1]);
				} else {
					exponent = 1;
				}
                coefficients_list.add(coefficient);
                exponents_list.add(exponent);
            } else {
                coefficients_list.add(Double.parseDouble(term));
                exponents_list.add(0);
            }
        }
        this.coefficients = coefficients_list.stream().mapToDouble(Double::doubleValue).toArray();
        this.exponents = exponents_list.stream().mapToInt(Integer::intValue).toArray();
    }

    public void saveToFile(String fileName) throws IOException {
        BufferedWriter writer = new BufferedWriter(new FileWriter(fileName));
        StringBuilder line = new StringBuilder();
        for (int i = 0; i < coefficients.length; i++) {
            double coefficient = coefficients[i];
            int exponent = exponents[i];
            if (coefficient > 0 && i != 0) { 
                line.append("+");
            }
            if (coefficient == -1 && exponent != 0) {
                line.append("-");
            } else if (coefficient != 1 || exponent == 0) {
                if (coefficient == (int) coefficient) {
					line.append((int) coefficient);
				} else {
					line.append(coefficient);
				}
            }
            if (exponent > 0) {
                line.append("x");
                if (exponent > 1) {
                    line.append(exponent);
                }
            }
        }
        writer.write(line.toString());
        writer.close();
	}

	public void get_coefficients() {
		System.out.print("coefficients: ");
		for (double coefficient : coefficients) {
			if (coefficient == (int) coefficient) {
				System.out.print((int) coefficient + " ");
			} else {
				System.out.print(coefficient + " ");
			}
		}
		System.out.println();
	}
	
	public void get_exponents() {
		System.out.print("Exponents: ");
		for (int exponent : exponents) {
			System.out.print(exponent + " ");
		}
		System.out.println();
	}
}

public class Polynomial {
    double[] coefficients;

    public Polynomial(){
        this.coefficients = new double[] {0};
	}
	
	public Polynomial(double[] coefficients){
            this.coefficients = coefficients;
	}
	
    public Polynomial add(Polynomial p){
	int len = Math.max(this.coefficients.length, p.coefficients.length);
        double[] sum_coefficients = new double[len];
        double this_coefficient = 0;
        double p_coefficient = 0;
        for (int i = 0; i < len; i++){
            if (i < this.coefficients.length){
                this_coefficient = this.coefficients[i];
            } else {
				this_coefficient = 0;
			}
            if (i < p.coefficients.length){
                p_coefficient = p.coefficients[i];
            } else {
				p_coefficient = 0;
			}
            sum_coefficients[i] = this_coefficient + p_coefficient;
        }
        return new Polynomial(sum_coefficients);
    }

    public double evaluate(double x){
	double result = 0;
        for (int i = 0; i < coefficients.length; i++){
            result += coefficients[i]*(Math.pow(x,i));
        }
	return result;
    }

    public boolean hasRoot(double root){
        return evaluate(root)== 0;
    }
}
import java.lang.Math;
import java.lang.String;
import java.util.Arrays;
import java.util.ArrayList;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

@ManagedBean(name = "calculadoraBean")
@SessionScoped
public class BackingBean{

    private String list;
    private double[] values;
    private double variance;
    private double standardDeviation;
    private double mean;
    private double mode;
    private ArrayList<String> backup;

    public BackingBean(){
        list="";
        variance = 0;
        standardDeviation = 0;
        mode = 0;
        mean = 0;
        values = new double[]{0};
        backup = new ArrayList<String>();
    }

    public double calculateMean(double values[]){
        mean = 0;
        for(int i=0; i<values.length; i++){
            mean += values[i];
        }
        mean = mean/values.length;
        return mean;
    }

    public double calculateStandardDeviation(double values[]){
        standardDeviation = 0;
        double backUpMean = mean;
        double prom = calculateMean(values);
        for(int i=0; i<values.length; i++){
            standardDeviation += (values[i]-prom)*(values[i]-prom);
        }
        standardDeviation = standardDeviation/(values.length-1);
        mean = backUpMean;
        return standardDeviation;
    }

    public double calculateVariance(double values[]){
        double backUpMean = mean, backUpDeviation = standardDeviation;
        variance = Math.sqrt(calculateStandardDeviation(values));
        mean = backUpMean;
        standardDeviation = backUpDeviation;
        return variance;
    }

    public double calculateMode(double values[]){
        Arrays.sort(values);
        mode = values[0];
        int maxCounter = 1, counter = 1;
        for(int i=1; i<values.length; i++){
            if (values[i] == values[i-1]){
                counter += 1;
            }
            else if(counter>maxCounter){
                maxCounter = counter;
                mode = values[i-1];
            }
        }
        return mode;
    }

    public void restart(){
        variance = 0;
        standardDeviation = 0;
        mode = 0;
        mean = 0;
        values = new double[]{0};
        list = "";
        backup.clear();
    }

    public double[] getValues(){
        return values;
    }

    public void setList(String values){
        list = values;
        if(backup.size()==0 || !values.equals(backup.get(backup.size()-1))) backup.add(values);
        String[] parts = values.split(";");
        this.values = new double[parts.length];
        for(int i=0; i<parts.length; i++){
            this.values[i] = Integer.parseInt(parts[i]);
        }
    }

    public String getList(){
        return list;
    }

    public double getVariance(){
        return variance;
    }

    public double getMean(){
        return mean;
    }

    public double getStandardDeviation(){
        return standardDeviation;
    }

    public double getMode(){
        return mode;
    }

    public ArrayList<String> getBackup(){
        return backup;
    }

    public String passing(String value){
        return value;
    }
}
package part1;


import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;


public abstract class GeneticAlgorithm
{
    /**
     * holds a list of the chromosomes
     */
    protected List<Chromosome> population;
    /**
     * holss a list of the parents
     */
    protected List<Chromosome> parent;

    /**
     * holds reference to number of chromosomes(population size)
     */
    protected int numberPopulation=10;

    private int max_x=5;
    private int min_x=0;

    /**
     * This function computes the genetic algorithm.
     */
    public final void computeGeneticAlgorithm(){
        generateInitialPopulation();
        computeFitness(population);
        int i=0;
        while (i<100){
            selection();
            if (!continueCompute())
                break;
            crossover();
            mutation();
            computeFitness(parent);
            survivorSelection();
            i++;
        }
        displayResults();
    }

    private void displayResults() {
        int i=0;
        for(Chromosome ch:population)
            System.out.println((i++)+"-) " + ch.fitness);
    }

    /**
     * hook operation for stop the genetic algorithm loop.
     * @return
     */
    protected boolean continueCompute() {
        return true;
    }

    /**
     * It selects the childs according to subjects which are given in the homework pdf, then replace the selecred childs with the
     * least fittest chromosomes of the population.
     */
    private void survivorSelection() {

        List<Chromosome> p_copy=new ArrayList<>(population);
        Collections.sort(p_copy);
        for (int i=0;i<parent.size();++i){
            double x1=stringToDouble(parent.get(i).x1);
            double x2=stringToDouble(parent.get(i).x2);
            if( x1>5 || x1<0 || (x1+x2)>5 || (x1+x2)<0 || x2>5 || x2<0 || Double.isNaN(x1) || Double.isNaN(x2))
            {
                parent.remove(i);
                i--;
            }
        }
        for (int i=0;i<parent.size();++i){
            population.set(population.indexOf(p_copy.get(i)),parent.get(i));
        }
    }

    /**
     * This function mutates the childs binary value. Binary values will be selected randomly as the numberPopulation value.
     * Then the 1 bit will be replaced with the 0 bit ,and the 0 will be replacaed with the 1 bit.
     */
    private final void mutation(){
        Random rand = new Random();
        for(int i=0;i<parent.size();++i){
            StringBuffer buf = new StringBuffer(parent.get(i).chromosome);
            for(int j=0;j<numberPopulation;++j){
                int index = rand.nextInt(parent.get(i).chromosome.length());
                if (parent.get(i).chromosome.charAt(index)=='1')
                    buf.setCharAt(index,'0');
                else
                    buf.setCharAt(index,'1');
                parent.get(i).chromosome=buf.toString();
            }
            parent.get(i).x1 = parent.get(i).chromosome.substring(0,parent.get(i).x1.length());
            parent.get(i).x2 = parent.get(i).chromosome.substring(parent.get(i).x1.length());
        }

    }



    protected abstract void crossover();

    protected abstract void selection();

    /**
     * Computes the fitness function for fiven list
     * @param liste
     */
    final void computeFitness(List<Chromosome> liste){
        for(Chromosome ch: liste){
            double x=fitness(stringToDouble(ch.x1),stringToDouble(ch.x2));
            ch.fitness=x;
        }
    }

    /**
     * This methods generates the population. For each iteration of the loop it creates 2 random double
     * number and creates chromosome with theme. Then add this chromosome to population list.
     */
    final void generateInitialPopulation(){
        double random1=0;
        double random2=0;
        for(int i=0;i<numberPopulation;++i){
            Random r = new Random();
            random1 = min_x+ r.nextDouble() * (max_x );
            random2 = min_x+ r.nextDouble() * (max_x-random1);
            population.add(new Chromosome(doubleToString(random1),doubleToString(random2)));
        }
    }
    /**
     * This method takes two number x1, and x2 and calculates fitness function with these parameters
     * @param x1 number 1
     * @param x2 number 2
     
	 * @return result of fitness function for given 2 numbers
     */
    final double fitness(double x1, double x2) {
        return (20*x1*x2)+(16*x2)-(2*x1*x1)-(x2*x2)-((x1+x2)*(x1+x2));
    }

    /**
     * Helper method convert double to binary string
     * @param x double number
     * @return binary representation of the given number
     */
    private String doubleToString(double x){
       String temp= Long.toBinaryString(Double.doubleToLongBits(x));
       if (temp.length()==62)
           return '0'+temp;
       else
           return temp;
    }

    /**
     * Helper method convert binary string to double
     * @param x binary representation of a double number
     * @return double represenrtation of the given string
     */
    private double stringToDouble(String x) {
        return Double.longBitsToDouble(new BigInteger(x, 2).longValue());
    }
}

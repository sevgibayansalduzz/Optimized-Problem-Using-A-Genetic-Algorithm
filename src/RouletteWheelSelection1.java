package part1;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;
public class RouletteWheelSelection1 extends GeneticAlgorithm {

    public RouletteWheelSelection1() {
        population=new ArrayList<Chromosome>();
    }

    /**
     * 1 point crossover
     * 1 point selected randomly
     * then parents values ,which are between first element and selected point, will be swaped
     */
    @Override
    protected void crossover() {
        Random rand = new Random();
        int point = rand.nextInt(parent.get(0).chromosome.length());
        StringBuffer buf = new StringBuffer(parent.get(0).chromosome);

        String temp=parent.get(0).chromosome.substring(0,point);
        buf.replace(0, point, parent.get(1).chromosome.substring(0,point));
        parent.get(0).chromosome=buf.toString();

        buf = new StringBuffer(parent.get(1).chromosome);
        buf.replace(0, point, temp);
        parent.get(1).chromosome=buf.toString();

        //change the genes
        for(int i=0;i<2;++i){
            parent.get(i).x1=parent.get(i).chromosome.substring(0,parent.get(i).x1.length());
            parent.get(i).x2=parent.get(i).chromosome.substring(parent.get(i).x1.length());
        }
    }

    /**
     * This function calls rouletteSelection two times for getting 2 parents
     */
    @Override
    protected void selection() {
        parent=new ArrayList<Chromosome>();
        double sumFitness=0;
        for(Chromosome ch: population)
            sumFitness+=Math.abs(ch.fitness);
        int i=0;
        while (parent.size()<2 && i<40){
            rouletteSelection(sumFitness);
            ++i;
        }
    }
    /**
     * if algorithm is stuck when searching parents, algorithm will be ended by this hook method.
     * @return
     */
    @Override
    protected boolean continueCompute(){
        if (parent.size()<2)
            return false;
        else
            return true;
    }

    /**
     *This generate numbers between 0 and sum of the fitness values, then generates a random value.
     * Then parent will be selected, according to which chromosome in the population is exceeded by the random value
     * @param sumFitness
     */
    private void rouletteSelection(double sumFitness) {

        Random r=new Random();
        double random=0 + r.nextDouble() * (sumFitness - 0);
        double partialSum=0;
        for(Chromosome ch: population)
        {
            partialSum+=Math.abs(ch.fitness);
            if(partialSum>=random) {
                parent.add(new Chromosome(ch.x1,ch.x2));
                break;
            }
        }
    }
}

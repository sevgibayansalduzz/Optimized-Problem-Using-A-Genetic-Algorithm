package part1;

import java.util.ArrayList;
import java.util.Random;

public class TournamentSelection1 extends GeneticAlgorithm {


    public TournamentSelection1() {
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
     * choose k (the tournament size) individuals from the population at random
     choose the best individual from the tournament with probability p
     choose the second best individual with probability p*(1-p)
     choose the third best individual with probability p*((1-p)^2)
     and so on
     */
    @Override
    protected void selection() {
        parent=new ArrayList<Chromosome>();
        Random r=new Random();
        int k= 2 + r.nextInt(population.size()/3);

        int i=0;
        while (parent.size()<2)
        {
            tournamentSelection(k);
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

    private void tournamentSelection(int k) {
        Random rand = new Random();
        Chromosome max = population.get(rand.nextInt(population.size()));
        for(int i=1;i<k;++i)
        {
            Chromosome randomElement = population.get(rand.nextInt(population.size()));
            if(max.compareTo(randomElement)==-1)
                max=randomElement;
        }
        parent.add(new Chromosome(max.x1,max.x2));
    }
}

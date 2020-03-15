package part1;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class RankSelection2 extends GeneticAlgorithm {

    public RankSelection2() {
        population=new ArrayList<Chromosome>();
    }

    /**
     * 2 point crossover
     * two point selected randomly
     * then parents values between 2 points will be swaped
     */
    @Override
    protected void crossover()
    {
        Random rand = new Random();
        int point1 = rand.nextInt(parent.get(0).chromosome.length());
        int point2 = rand.nextInt(parent.get(0).chromosome.length());

        if (point1>point2)
        {
            int temp=point1;
            point1=point2;
            point2=temp;
        }

        StringBuffer buf = new StringBuffer(parent.get(0).chromosome);
        String temp=parent.get(0).chromosome.substring(point1,point2);
        buf.replace(point1, point2, parent.get(1).chromosome.substring(point1,point2));
        parent.get(0).chromosome=buf.toString();

        buf = new StringBuffer(parent.get(1).chromosome);
        buf.replace(point1, point2, temp);
        parent.get(1).chromosome=buf.toString();

        //change the genes
        for(int i=0;i<2;++i){
            parent.get(i).x1=parent.get(i).chromosome.substring(0,parent.get(i).x1.length());
            parent.get(i).x2=parent.get(i).chromosome.substring(parent.get(i).x1.length());
        }
    }

    /**
     *So for a population of N solutions the best solution gets rank N, the second best rank N-1, etc.
     * The worst individual has rank 1. Now use the roulette wheel and start selecting.
     */
    @Override
    protected void selection() {
        parent=new ArrayList<Chromosome>();

        List<Chromosome> p_copy=new ArrayList<>(population);
        int rank[]=new int[numberPopulation];

        Collections.sort(p_copy);

        int rank_p=1;
        for(Chromosome ch:p_copy){
            rank[population.indexOf(ch)]=rank_p;
            rank_p++;
        }
        rank_p=((rank_p-1)*(rank_p))/2;

        int i=0;
        while (parent.size()<2 && i<40){
            rankSelection(rank_p,rank);
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
     * Rank selection first ranks the population and then every chromosome receives fitness from this ranking.
     * The worst will have fitness 1, second worst 2 etc. and the best will have fitness N (number of chromosomes in population).
     * Then applies roulette whell algorithm
     * @param rank_p
     * @param rank
     */
    private void rankSelection(int rank_p, int[] rank) {
        Random r = new Random();
        double random = r.nextDouble()*(rank_p);
        int partial_rank = 0;

        for (int i = 0; i < rank.length; ++i) {
            partial_rank += rank[i];
            if (partial_rank >= random) {
                parent.add(new Chromosome(population.get(i).x1,population.get(i).x2));
                break;
            }
        }
    }
}
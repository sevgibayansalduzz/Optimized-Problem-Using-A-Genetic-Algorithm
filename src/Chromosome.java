package part1;


/**
 * This class represents a chromosome.
 */
public class Chromosome implements Comparable {
    /**
     * x1 holds reference to first gene of chromosome
     */
    public String x1;
    /**
     * x2 holds reference to second gene of chromosome
     */
    public String x2;
    /**
     * chromosome holds the concatenation of the genes x1 and x2
     */
    public String chromosome;
    /**
     * this data fields hold a reference to fitness value of the chromosome
     */
    public double fitness;

    /**
     * Constructor for creating a chromosome instance
     * @param x1 first gene
     * @param x2 second gene
     */
    public Chromosome(String x1, String x2) {
        this.x1 = x1;
        this.x2 = x2;
        chromosome=x1+x2;
    }

    /**
     * This data function compares two chromosome according to  their fitness value
     * @param o a Chromosome object
     * @return 0 if they are equal, 1 if this object bigger than given object, else -1
     */
    @Override
    public int compareTo(Object o) {
        Chromosome other=(Chromosome) o;
        if(this.fitness>other.fitness)
            return 1;
        else if(this.fitness<other.fitness)
            return -1;
        else
            return 0;
    }
    @Override
    public String toString(){
        return "fitness: "+Double.toString(this.fitness);
    }
}

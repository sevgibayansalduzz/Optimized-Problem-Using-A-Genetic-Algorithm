package part1;

public class Main {
    public static void main(String[] args) {
        System.out.println("RouletteWheelSelection1");
        RouletteWheelSelection1 rouletteWheelSelection1=new RouletteWheelSelection1();
        rouletteWheelSelection1.computeGeneticAlgorithm();

        System.out.println("RankSelection2");
        RankSelection2 rankSelection2=new RankSelection2();
        rankSelection2.computeGeneticAlgorithm();

        System.out.println("TournamentSelection1");
        TournamentSelection1 tournamentSelection1=new TournamentSelection1();
        tournamentSelection1.computeGeneticAlgorithm();
    }
}

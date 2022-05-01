package process.singleObject;

import databean.Order;
import databean.Vehicle;
import process.multiObject.SetDataMap;

import java.text.ParseException;
import java.util.*;


public class GeneticAlgorithm {


    private int populationSize;
    private double mutationRate;
    private double crossoverRate;
    private int elitismCount;
    private int maxSame=10;
    private int sameCount=0;
    private double tmpFitness=Double.POSITIVE_INFINITY;
    private int maxRate=1;
    /**
     * A new property we've introduced is the size of the population used for
     * tournament selection in crossover.
     */
    protected int tournamentSize;

    public GeneticAlgorithm(int populationSize, double mutationRate, double crossoverRate, int elitismCount,
                            int tournamentSize) {

        this.populationSize = populationSize;
        this.mutationRate = mutationRate;
        this.crossoverRate = crossoverRate;
        this.elitismCount = elitismCount;
        this.tournamentSize = tournamentSize;
    }

    /**
     * Initialize population
     *
     * @param chromosomeLength
     *            The length of the individuals chromosome
     * @return population The initial population generated
     */
    public Population initPopulation(int chromosomeLength) throws ParseException {
        // Initialize population
        return new Population(this.populationSize, chromosomeLength);
    }

    /**
     * Calculate fitness for an individual.
     *
     * This fitness calculation is a little more involved than chapter2's. In
     * this case we initialize a new Robot class, and evaluate its performance
     * in the given maze.
     *
     * @param individual
     *            the individual to evaluate
     * @return double The fitness value for individual
     */
    public double calcFitness(Individual individual, List<Order> orderList, SetDataMap setDataMap) {
        // Get individual's chromosome
        int[] chromosome = individual.getChromosome();
//        simpleRepeatableTest(orderList,setDataMap);
        // Get fitness
        Vehicle vehicle=new Vehicle();
        individual.vehicleList=vehicle.run(chromosome,orderList,setDataMap);
        double fitness =0;
        for (Vehicle v:individual.vehicleList) {
            fitness+=v.fitness+v.FC;
        }
        // Store fitness
//        individual.setFitness(individual.vehicleList.size());
        individual.setFitness(fitness);

//        return individual.vehicleList.size();
        return individual.getFitness();

    }

    public void reconstructChromosome(Individual individual,List<Order> orderList, SetDataMap setDataMap){
//        getReconstructChromosome(individual,orderList,setDataMap);
        int[] reconstructedChromosome=getChromosomeFromVehicleList(individual.vehicleList);
        List<Vehicle> tmpVehicleList=new ArrayList<>();
        tmpVehicleList=new Vehicle().run(reconstructedChromosome,orderList,setDataMap);
        double reconstructedFitness=0;
        for (Vehicle v:tmpVehicleList) {
            reconstructedFitness+=v.fitness+v.FC;
        }
        int[] newChromosome=getChromosomeFromVehicleList(tmpVehicleList);

        if(individual.getFitness()>reconstructedFitness){
            int index=0;
            for (int i = 0; i < reconstructedChromosome.length; i++) {
                individual.setGene(i,reconstructedChromosome[i]);
            }
            individual.vehicleList=tmpVehicleList;
            individual.setFitness(reconstructedFitness);
            if(!Arrays.equals(reconstructedChromosome,newChromosome)) {
                reconstructChromosome(individual,orderList,setDataMap);
            }
        }
    }

    public int[] getChromosomeFromVehicleList(List<Vehicle> vehicleList){
        int index=0;
        List<Integer> integerList=new ArrayList<>();
//        Map<String,Integer> vehicleTypeVehicleMap=new TreeMap<>();
//        List<Vehicle> tmpVehicleList=new ArrayList<>();
//        for (Vehicle vehicle:vehicleList) {
//            vehicleTypeVehicleMap.merge(vehicle.vehicleDetailedType, 1, Integer::sum);
//        }
//        for (String key:vehicleTypeVehicleMap.keySet()) {
//            for (Vehicle vehicle:vehicleList) {
//                if(vehicle.vehicleDetailedType.equals(key)){
//                    tmpVehicleList.add(vehicle);
//                }
//            }
//        }
//        for (Vehicle v:tmpVehicleList) {
//            integerList.addAll(v.chromosome);
//        }
        for (Vehicle v:vehicleList) {
            integerList.addAll(v.chromosome);
        }
        int[] chromosome=new int[integerList.size()];
        for (Integer integer:integerList) {
            chromosome[index++]=integer;
        }
        return chromosome;
    }


    /**
     * Evaluate the whole population
     */
    public void evalPopulation(Population population, List<Order> orderList, SetDataMap setDataMap) {
        double populationFitness = 0;
        // Loop over population evaluating individuals and suming population
        // fitness
        int individualIndex=0;
        for (Individual individual : population.getIndividuals()) {
//            long startTime=System.currentTimeMillis();
            populationFitness += this.calcFitness(individual, orderList,setDataMap);
//            reconstructChromosome(individual);
            if(individualIndex<this.elitismCount){
                reconstructChromosome(individual,orderList,setDataMap);
            }
//            reconstructChromosome(individual,orderList,setDataMap);

            individualIndex++;
        }

        population.setPopulationFitness(populationFitness);
    }

    /**
     * Check if population has met termination condition
     *
     * We don't actually know what a perfect solution looks like for the robot
     * controller problem, so the only constraint we can give to the genetic
     * algorithm is an upper bound on the number of generations.
     *
     * @param generationsCount
     *            Number of generations passed
     * @param maxGenerations
     *            Number of generations to terminate after
     * @return boolean True if termination condition met, otherwise, false
     */
    public boolean isTerminationConditionMet(int generationsCount, int maxGenerations) {
        return (generationsCount > maxGenerations);
    }

    /**
     * Selects parent for crossover using tournament selection
     *
     * Tournament selection works by choosing N random individuals, and then
     * choosing the best of those.
     *
     * @param population
     * @return The individual selected as a parent
     */
    public Individual selectParent(Population population) {
        // Create tournament
        Population tournament = new Population(this.tournamentSize);

        // Add random individuals to the tournament
        population.shuffle();
        for (int i = 0; i < this.tournamentSize; i++) {
            Individual tournamentIndividual = population.getIndividual(i);
            tournament.setIndividual(i, tournamentIndividual);
        }

        // Return the best
        return tournament.getFittest(0);
    }

    public Population adaptedMutate(Population population) {
        double mutationRate=1;
        // Initialize new population
        Population newPopulation = new Population(this.populationSize);

        // Loop over current population by fitness
        for (int populationIndex = 0; populationIndex < population.size(); populationIndex++) {
            //Individual individual = population.getFittest(populationIndex);
            Individual individual = population.get(populationIndex);
            if(populationIndex<population.size()/2){
                mutationRate=this.mutationRate;
//                mutationRate=0;
//                mutationRate= maxRate*populationIndex *Math.pow(5*populationSize,-1);
            }
            else {
                mutationRate=0.1*maxRate;
            }
            // Skip mutation if this is an elite individual
            if (populationIndex >= this.elitismCount) {
                // Loop over individual's genes
                for (int geneIndex = 0; geneIndex < individual.getChromosomeLength(); geneIndex++) {
                    // Does this gene need mutation?
                    if (mutationRate > Math.random()) {
                        // Get new gene
                        // Get the first index
                        // Get the second index
                        // Change them;
                        //int newGene = 1;
                        int newGeneIndex =-1;
                        do{
                            newGeneIndex= (int) Math.floor(Math.random() * (individual.getChromosomeLength()));
                        }while (individual.getGene(geneIndex) ==individual.getGene(newGeneIndex) );
                        int value=individual.getGene(geneIndex);
                        int newValue=individual.getGene(newGeneIndex);
                        individual.setGene(geneIndex, newValue);
                        individual.setGene(newGeneIndex, value);
                    }
                }
            }
            // Add individual to population
            newPopulation.setIndividual(populationIndex, individual);
        }
        // Return mutated population
        return newPopulation;
    }

    public Population adaptedCrossover(Population population){
        adaptControl(population);
        // Create new population
        Population newPopulation = new Population(population.size());

        // Loop over current population by fitness
        for (int populationIndex = 0; populationIndex < population.size(); ) {
            Individual parent1 = population.getFittest(populationIndex);

            // Apply crossover to this individual?
            if (this.crossoverRate > Math.random() && populationIndex >= this.elitismCount){
                // Find second parent
                Individual parent2 = this.selectParent(population);
                // Initialize offspring
                Individual offspring1 = new Individual(parent1.getChromosomeLength());
                Individual offspring2 = new Individual(parent2.getChromosomeLength());

                int parent1Start = (int) Math.floor (Math.random() * (parent1.vehicleList.size()));
                int parent2Start = (int) Math.floor (Math.random() * (parent2.vehicleList.size()));

                int[] tmpChromosome1 = new int[parent1.vehicleList.get(parent1Start).chromosome.size()];

                for (int i = 0; i<tmpChromosome1.length ; i++) {
                    tmpChromosome1[i]=parent1.vehicleList.get(parent1Start).chromosome.get(i);
                    offspring2.setGene(i,tmpChromosome1[i]);
                }

                int[] tmpChromosome2 = new int[parent2.vehicleList.get(parent2Start).chromosome.size()];
                for (int i = 0; i<tmpChromosome2.length ; i++) {
                    tmpChromosome2[i]=parent2.vehicleList.get(parent2Start).chromosome.get(i);
                    offspring1.setGene(i,tmpChromosome2[i]);
                }
                deleteRepeatedChromosome(parent1.getChromosome(),tmpChromosome2,offspring1);
                deleteRepeatedChromosome(parent2.getChromosome(),tmpChromosome1,offspring2);
//                    offspring1
                // Add offspring to new population
                newPopulation.setIndividual(populationIndex++, offspring1);
                //                    offspring2
                // Add offspring to new population
                if(populationIndex<population.size()){
                    newPopulation.setIndividual(populationIndex++, offspring2);
                }
            } else {
                // Add individual to new population without applying crossover
                    newPopulation.setIndividual(populationIndex++, parent1);
            }
        }
        return newPopulation;
    }

    public void adaptControl(Population population){
        if (tmpFitness==population.get(0).getFitness()){
            sameCount++;
            System.out.println("sameCount:"+sameCount);
            if(sameCount>maxSame){
                maxRate=20;
            }
        }
        else {
            if(tmpFitness<population.get(0).getFitness()){
                System.out.println("something wrong.");
            }
            tmpFitness=population.get(0).getFitness();
            System.out.println("tmpFitness:"+tmpFitness);
            sameCount=0;
        }
    }

    public void deleteRepeatedChromosome(int[] parentChromosome,int[] tmpChromosome,Individual individual){
        int[] chromosome=new int[parentChromosome.length-tmpChromosome.length];
        List<Integer> parentList=new ArrayList<>();
        for (int j : parentChromosome) {
            parentList.add(j);
        }
        for (int j : tmpChromosome) {
            parentList.remove((Integer) j);
        }
        if(chromosome.length!=parentList.size()){
            System.out.println("In deleteRepeatedChromosome,bug appeared.");
        }
        for (int i = 0; i < parentList.size(); i++) {
            individual.setGene(tmpChromosome.length+i,parentList.get(i));
        }
    }

    public void simpleRepeatableTest(List<Order> orderList, SetDataMap setDataMap){
        int[] chromosome=new int[409];
        chromosome= new int[]{362,328,8,166,361,242,241,94,188,223,218,184,50,258,295,115,45,257,213,125,25,397,392,407,340,341,338,355,343,352,339,348,358,177,190,349,150,221,168,32,383,375,385,379,9,10,261,36,259,377,85,305,360,42,111,285,331,233,95,240,291,395,149,252,256,37,372,113,329,157,156,208,203,70,76,216,193,92,232,194,129,24,23,75,199,380,376,60,197,298,47,219,54,167,182,283,265,272,18,400,57,251,382,312,366,277,65,336,0,209,237,116,132,134,136,363,142,77,212,198,17,46,89,253,118,337,84,319,238,152,332,69,391,122,123,296,399,390,278,6,401,398,405,145,102,353,266,249,147,300,186,346,214,255,247,234,44,306,323,148,41,5,11,239,236,292,403,175,53,49,48,159,52,220,90,264,267,250,229,309,235,151,144,301,158,215,146,126,109,93,210,161,162,62,119,180,334,364,173,7,21,279,217,373,211,139,26,406,369,313,58,254,271,3,30,73,154,384,202,1,64,163,367,164,195,294,402,140,327,274,165,128,333,275,83,121,71,393,386,63,189,138,40,359,87,88,107,86,308,185,347,356,176,354,315,357,351,350,342,316,378,35,260,4,287,225,14,299,172,325,282,16,31,303,302,137,204,13,326,365,110,20,270,55,117,22,19,97,381,317,224,169,368,174,179,153,33,51,29,59,61,227,297,66,181,230,322,320,141,404,318,387,370,321,171,68,12,106,143,160,178,183,324,72,273,388,130,228,120,304,74,246,244,371,394,91,127,201,191,310,114,28,98,268,15,288,82,38,389,108,103,263,284,2,135,396,374,43,311,112,192,248,196,286,81,289,124,67,335,155,262,206,344,101,39,205,207,133,280,314,170,105,408,96,222,290,34,269,187,100,99,345,131,276,307,78,56,27,330,104,79,80,200,231,293,226,245,243,281,
        };
        Vehicle vehicle=new Vehicle();
//        long startTime=System.currentTimeMillis();
        List<Vehicle> vehicleList=new ArrayList<>();
        vehicleList=vehicle.run(chromosome,orderList,setDataMap);
        double fitness=0;
        for (Vehicle v:vehicleList) {
            fitness+=v.fitness+v.FC;
        }
        System.out.println(fitness);
    }

}

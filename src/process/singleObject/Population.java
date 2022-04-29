package process.singleObject;

import java.text.ParseException;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Random;


/**
 * A population is an abstraction of a collection of individuals. The population
 * class is generally used to perform group-level operations on its individuals,
 * such as finding the strongest individuals, collecting stats on the population
 * as a whole, and selecting individuals to mutate or crossover.
 * 
 * @author bkanber
 *
 */
public class Population {
	private Individual population[];
	private double populationFitness = -1;
	private int leastStep;
	private int mostStep;
	private int leastDistance;
	private int mostDistance;



	/**
	 * Initializes blank population of individuals
	 * 
	 * @param populationSize
	 *            The number of individuals in the population
	 */
	public Population(int populationSize) {
		// Initial population
		this.population = new Individual[populationSize];

	}

	/**
	 * Initializes population of individuals
	 *
	 * @param populationSize
	 *            The number of individuals in the population
	 * @param chromosomeLength
	 *            The size of each individual's chromosome
	 */
	public Population(int populationSize, int chromosomeLength) throws ParseException {
		// Initialize the population as an array of individuals
		this.population = new Individual[populationSize];
		// Create each individual in turn
		for (int individualCount = 0; individualCount < populationSize; individualCount++) {
			// Create an individual, initializing its chromosome to the given
			// length
			Individual individual = new Individual(chromosomeLength);
//			System.out.println(individualCount);

			// Add individual to population
			this.population[individualCount] = individual;
		}

	}

	/**

	public Population() {
		this.population = new Individual[0];
		this.objectiveFunctions = new ArrayList<ObjectiveFunction>();
		// Add the relevant objective functions
		objectiveFunctions.add(new CountFunction());
		objectiveFunctions.add(new DistanceFunction());
	}

	/**
	 * Get individuals from the population
	 * 
	 * @return individuals Individuals in population
	 */
	public Individual[] getIndividuals() {
		return this.population;
	}

	/**
	 * Find an individual in the population by its fitness
	 * 
	 * This method lets you select an individual in order of its fitness. This
	 * can be used to find the single strongest individual (eg, if you're
	 * testing for a solution), but it can also be used to find weak individuals
	 * (if you're looking to cull the population) or some of the strongest
	 * individuals (if you're using "elitism").
	 * 
	 * @param offset
	 *            The offset of the individual you want, sorted by fitness. 0 is
	 *            the strongest, population.length - 1 is the weakest.
	 * @return individual Individual at offset
	 */
	public Individual getFittest(int offset) {
		// Order population by fitness
		Arrays.sort(this.population, new Comparator<Individual>() {
			@Override
			public int compare(Individual o1, Individual o2) {
				if (o1.getFitness() < o2.getFitness()) {
					return -1;
				} else if (o1.getFitness() > o2.getFitness()) {
					return 1;
				}
				return 0;
			}
		});

		// Return the fittest individual
		return this.population[offset];
	}

	/**
	 * Set population's group fitness
	 * 
	 * @param fitness
	 *            The population's total fitness
	 */
	public void setPopulationFitness(double fitness) {
		this.populationFitness = fitness;
	}

	/**
	 * Get population's group fitness
	 * 
	 * @return populationFitness The population's total fitness
	 */
	public double getPopulationFitness() {
		return this.populationFitness;
	}

	/**
	 * Get population's size
	 * 
	 * @return size The population's size
	 */
	public int size() {
		return this.population.length;
	}

	/**
	 * Set individual at offset
	 * 
	 * @param individual
	 * @param offset
	 * @return individual
	 */
	public Individual setIndividual(int offset, Individual individual) {
		return population[offset] = individual;
	}

	/**
	 * Get individual at offset
	 * 
	 * @param offset
	 * @return individual
	 */
	public Individual getIndividual(int offset) {
		return population[offset];
	}

	public int getLeastStep() {
		return leastStep;
	}

	public void setLeastStep(int leastStep) {
		this.leastStep = leastStep;
	}

	public int getMostStep() {
		return mostStep;
	}

	public void setMostStep(int mostStep) {
		this.mostStep = mostStep;
	}

	public int getLeastDistance() {
		return leastDistance;
	}

	public void setLeastDistance(int leastDistance) {
		this.leastDistance = leastDistance;
	}

	public int getMostDistance() {
		return mostDistance;
	}

	public void setMostDistance(int mostDistance) {
		this.mostDistance = mostDistance;
	}

	public Individual get(int i) { return this.population[i];
	}

	public void add(Individual individual) {
		int index=population.length;
		Individual[] newPopulation=new Individual[index+1];
		for (int i = 0; i <index ; i++) {
			newPopulation[i]=population[i];
		}
		newPopulation[index]=individual;
		this.population=newPopulation;
	}

	public void addAll(List<Individual> individuals) {
		int index=population.length;
		int totalLength=index+individuals.size();
		Individual[] newPopulation=new Individual[totalLength];
		for (int i = 0; i <totalLength ; i++) {
			if(i<index){
				newPopulation[i]=population[i];
			}
			else{
				newPopulation[i]=individuals.get(i-index);
			}
		}

		this.population=newPopulation;
	}
	/**
	 * Shuffles the population in-place
	 *
	 * @return void
	 */
	public void shuffle() {
		Random rnd = new Random();
		for (int i = population.length - 1; i > 0; i--) {
			int index = rnd.nextInt(i + 1);
			Individual a = population[index];
			population[index] = population[i];
			population[i] = a;
		}
	}


}
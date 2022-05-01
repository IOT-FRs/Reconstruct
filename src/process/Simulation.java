package process;

import databean.Order;
import databean.Vehicle;
import process.multiObject.SetDataMap;
import process.dataControl.CreateBasicData;
import process.dataControl.InputData;
import process.singleObject.GeneticAlgorithm;
import process.singleObject.Individual;
import process.singleObject.Population;

import java.text.ParseException;
import java.util.*;

public class Simulation {
    int populationSize = 200; // Population size for each generation
    int maxGenerations = 50; // Number of generations to simulate for

    private double crossoverProbability = 0.9;
    private double mutationProbability = 0.01;
    private int maxChromosomeLength;

    private long sortTime;
    private long crossoverTime;
    private long mutateTime;
    private long evaluateTime;

    public void start() throws ParseException {
        InputData inputData=new InputData();
        List<Order> orderList=new ArrayList<>();
        orderList=inputData.readOrderFile("src/data/RealOrderFile.csv");
        CreateBasicData createBasicData=new CreateBasicData();
        orderList=createBasicData.concludeOrderDatas(orderList);
//        new LoadTest().test();
        List<Vehicle> vehicleList=new ArrayList<>();
        vehicleList=inputData.readVehicleFile("src/data/RealVehicleFile.csv");
//        createBasicData.ConcludeVehicleDatas(vehicleList);
        SetDataMap setDataMap=new SetDataMap();
        setDataMap.start(orderList,vehicleList);
        int chromosomeLength=0;
        for (String str:setDataMap.getOrderIdCountMap().keySet()) {
            chromosomeLength+=setDataMap.getOrderIdCountMap().get(str);
        }
        this.maxChromosomeLength=chromosomeLength;

        GeneticAlgorithm ga = new GeneticAlgorithm(populationSize, mutationProbability, crossoverProbability, populationSize/10, 5);
//        Individual individual=new Individual(maxChromosomeLength);
        Population population=new Population(populationSize,maxChromosomeLength);
        ga.evalPopulation(population, orderList,setDataMap);
        System.out.println("chromosomeLength:"+maxChromosomeLength);

        int generation = 1;
        Individual fittest = population.getFittest(0);
        double totalTime=0,freeTime=0;
        while (!ga.isTerminationConditionMet(generation, maxGenerations)) {
            long startTime = System.currentTimeMillis();
            fittest = population.getFittest(0);
            totalTime=0;
            freeTime=0;
            for (Vehicle vehicle:fittest.vehicleList) {
                totalTime+=vehicle.presentWorkTime;
                freeTime+=vehicle.maxWorkTime-vehicle.presentWorkTime;
            }
            System.out.println("fitness:"+fittest.getFitness()+",vehicle count:"+fittest.vehicleList.size()+",totalTime:"+totalTime+",freeTime:"+freeTime);
            sortTime=System.currentTimeMillis();
//            System.out.println("the sortTime is:"+(sortTime-startTime));
            // Apply crossover
            population = ga.adaptedCrossover(population);
            crossoverTime=System.currentTimeMillis();
//            System.out.println("the crossoverTime is:"+(crossoverTime-sortTime));

            // Apply mutation
            population=ga.adaptedMutate(population);
            mutateTime=System.currentTimeMillis();
//            System.out.println("the mutateTime is:"+(mutateTime-crossoverTime));

            // Evaluate population
            ga.evalPopulation(population, orderList,setDataMap);
            evaluateTime=System.currentTimeMillis();
            System.out.println("the evaluateTime is:"+(evaluateTime-mutateTime));

            generation++;
        }

        System.out.println("Stopped after " + maxGenerations + " generations.");
//        Individual fittest = population.getFittest(0);
        fittest = population.getFittest(0);
        totalTime=0;
        freeTime=0;
        for (Vehicle vehicle:fittest.vehicleList) {
            totalTime+=vehicle.presentWorkTime;
            freeTime+=vehicle.maxWorkTime-vehicle.presentWorkTime;
//            for (Integer integer:vehicle.chromosome) {
//                System.out.print(integer+",");
//            }
            System.out.println(Arrays.toString(new List[]{vehicle.chromosome}));
        }
        System.out.println("fitness:"+fittest.getFitness()+",vehicle count:"+fittest.vehicleList.size()+",totalTime:"+totalTime+",freeTime:"+freeTime);

        System.out.println("Program Simulation finished.");
    }


}

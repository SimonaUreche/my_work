package example.Logic;

import example.GUI.SimulationFrame;
import example.Model.Server;
import example.Model.Task;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class SimulationManager implements Runnable {
    public int timeLimit;
    public int maxProcessingTime;
    public int minProcessingTime;
    public int maxArrivalTime;
    public int minArrivalTime;
    public int numberOfServers;
    public int numberOfClients;
    public SelectionPolicy selectionPolicy;
    private Scheduler scheduler;
    private SimulationFrame frame;
    private List<Task> generatedTasks;
    private double averageServiceTime;
    private double averageWaitingTimeTotal;
    private int peakTime = 0;
    private int peakClients = 0;
    private FileWriter outFile;
    public SimulationManager(SimulationFrame frame) {
        this.frame = frame;
        this.timeLimit = frame.getInterval().isEmpty() ? 0 : Integer.parseInt(frame.getInterval());
        this.numberOfServers = frame.getQueues().isEmpty() ? 0 : Integer.parseInt(frame.getQueues());
        this.numberOfClients = frame.getClients().isEmpty() ? 0 : Integer.parseInt(frame.getClients());
        this.minArrivalTime = frame.getMinArrivalTime().isEmpty() ? 0 : Integer.parseInt(frame.getMinArrivalTime());
        this.maxArrivalTime = frame.getMaxArrivalTime().isEmpty() ? 0 : Integer.parseInt(frame.getMaxArrivalTime());
        this.minProcessingTime = frame.getMinServiceTime().isEmpty() ? 0 : Integer.parseInt(frame.getMinServiceTime());
        this.maxProcessingTime = frame.getMaxServiceTime().isEmpty() ? 0 : Integer.parseInt(frame.getMaxServiceTime());

        if (this.numberOfClients == 0) {
            this.scheduler = new Scheduler(0, 0);
        }
        else
        {
            this.scheduler = new Scheduler(this.numberOfServers, this.numberOfClients / this.numberOfServers);
        }
        String value = frame.getComboBox().toString();
        if (value.equals("Time Strategy")) {
            selectionPolicy = SelectionPolicy.SHORTEST_TIME;
        } else
            selectionPolicy = SelectionPolicy.SHORTEST_QUEUE;
        this.scheduler.changeStrategy(selectionPolicy);
        this.generatedTasks = new ArrayList<>();
        generateNRandomTasks();

        try {
            outFile = new FileWriter("a.txt");
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    private void generateNRandomTasks() {
        Random random = new Random();
        for (int i = 0; i < numberOfClients; i++) {
            int arrivalTime = random.nextInt(maxArrivalTime - minArrivalTime + 1) + minArrivalTime; // Generăm un timp de sosire între 0 și timpul limită al simulării
            int serviceTime = random.nextInt(maxProcessingTime - minProcessingTime + 1) + minProcessingTime; // Generăm un timp de servire între minProcessingTime și maxProcessingTime
            Task task = new Task(i + 1, arrivalTime, serviceTime); // Creăm un nou task cu ID-ul i + 1, timpul de sosire și timpul de servire generate
            averageServiceTime += serviceTime;
            generatedTasks.add(task); //adaug task-ul generat în lista de task-uri generate
        }

        averageServiceTime = averageServiceTime / numberOfClients;

        Collections.sort(generatedTasks); // Sortăm lista de task-uri după timpul de sosire
        for (int i = 0; i < numberOfClients; i++) {
            System.out.println(generatedTasks.get(i));
        }
    }

    @Override
    public void run() {
        int currentTime = 0;

      //  while ((!generatedTasks.isEmpty() || inQueues()) && currentTime < timeLimit) {
        while (currentTime < timeLimit) {
            List<Task> tasksDispatch = new ArrayList<>();
            synchronized (generatedTasks) {
                for (Task task : generatedTasks) {
                    if (task.getArrivalTime() == currentTime) { //la fiecare secunda se verififca daca existe task uri care trebuie puse in cozi
                        tasksDispatch.add(task);
                    }
                }
                for (Task task : tasksDispatch) { //task-urile sunt puse intr-o lista dupa care
                    scheduler.dispatchTask(task); //sunt adaugate in scheduler pt a fi asignate serverelor
                    removeTask(task);
                }
            }

            frame.setTime(String.valueOf(currentTime));
            graphicalInterfaceConnection((scheduler.getServers()), currentTime);

            int maxim = scheduler.PeakHour();
            if (maxim > peakClients) {
                peakClients = maxim;
                peakTime = currentTime;

            }
            currentTime++;
            try {
                Thread.sleep(1000); //așteaptă 1 secundă (1000 milisecunde)
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        averageWaitingTimeTotal = (double) Server.getWaitingTimeAverage() / numberOfClients;

        frame.setAverageWaitingTimeJLabel(String.format("%.2f", averageWaitingTimeTotal));
        frame.setAverageServiceTimeJLabel(String.format("%.2f",averageServiceTime));
        frame.setPeakTimeJLabel(String.valueOf(peakTime));

        try {
            outFile.write("Average Waiting Time: " + String.format("%.2f", averageWaitingTimeTotal) + "\n");
            outFile.write("Average Service Time: " + String.format("%.2f", averageServiceTime) + "\n");
            outFile.write("Peak Time: " + peakTime + "\n");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        try {
            outFile.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void graphicalInterfaceConnection(List<Server> serverList, int time) {

        StringBuilder waitingClients = new StringBuilder();
        StringBuilder queuesEvolution = new StringBuilder();

        for (Task remainedTasks : generatedTasks) {
            waitingClients.append(remainedTasks).append("; ");
        }
        frame.setWaitingQueue(waitingClients.toString());
        int i = 0;
        for (Server queue : serverList) { //in serverList avem informatii despre starea fiecarei cozi
            queuesEvolution.append("Queue ").append(i + 1).append(": ");
            i++;
            Task[] clients = queue.getTasks();
            if (clients.length == 0) {
                queuesEvolution.append("closed");
            }
            for (Task task : clients) {
                queuesEvolution.append(task).append("; ");
            }
            queuesEvolution.append("\n");
        }
        frame.setQueueEvolution(queuesEvolution.toString());

        try {
            outFile.write("Time: " + time + "\n");
            outFile.write("Waiting clients: " + waitingClients + "\n");
            outFile.write(queuesEvolution + "\n");
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    private synchronized void removeTask(Task task) {
        generatedTasks.remove(task);
    }
    private boolean inQueues() {
        for (Server server : scheduler.getServers()) {
            System.out.println(server.getNrOfTasks());
            if (server.getNrOfTasks() > 0) {
                return true;
            }
        }
        return false;
    }

    public static void main(String[] args) {
        SimulationFrame frame = new SimulationFrame();
        frame.setVisible(true);
        SimulationManager sim = new SimulationManager(frame);

        if (sim.numberOfServers <= 0) {
        } else {
            ExecutorService executor = Executors.newFixedThreadPool(sim.numberOfServers);
            for (int i = 0; i < sim.numberOfServers; i++) {
                executor.execute(sim);
            }
            executor.shutdown(); //executor inchis si astepteptam terminarea executiei task-urilor
            try {
                executor.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

}
//simona
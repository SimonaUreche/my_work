package example.Logic;

import example.Model.Server;
import example.Model.Task;
import java.util.ArrayList;
import java.util.List;

public class Scheduler {

    private List<Server> servers = new ArrayList<>();
    private int maxNoServers;
    private static int maxTasksPerServer;
    private Strategy strategy;

    public Scheduler(int maxNoServers, int maxTasksPerServer) {
        this.maxNoServers = maxNoServers;
        this.maxTasksPerServer = maxTasksPerServer;

        // Crearea și pornirea thread-urilor pentru servere
        for (int i = 0; i < maxNoServers; i++) {
            Server server = new Server(maxTasksPerServer);
            servers.add(server);
        }

        for (Server server : servers) {
            Thread thread = new Thread(server);
            thread.start();
        }
    }
    public void changeStrategy(SelectionPolicy policy) {
        if (policy == SelectionPolicy.SHORTEST_TIME) {
            strategy = new ConcreteStrategyTime();
        } else if (policy == SelectionPolicy.SHORTEST_QUEUE) {
            strategy = new ConcreteStrategyQueue();
        }
    }
    public void dispatchTask(Task task) {
        strategy.addTask(servers, task);
    } //primeste o sarcina si o distribuie carte serverul adecvat in functie de strategie
    public List<Server> getServers() {
        return servers;
    }
    public static int getMaxTasksPerServer() {
        return maxTasksPerServer;
    }
    public int PeakHour(){ //momentul cu cea mai mare incarcatura din sistem
        int clients=0;
        for(Server s : servers){
            clients+=s.getTasks().length; //calc. nr. total de task-uri din toate serverele
        }
        return clients;
    }

}

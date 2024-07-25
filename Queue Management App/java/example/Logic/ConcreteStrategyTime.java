package example.Logic;

import example.Model.Server;
import example.Model.Task;
import java.util.List;

public class ConcreteStrategyTime implements Strategy{
    @Override
    public void addTask(List<Server> servers, Task t) {  //adaug task-ul in coada cu cel mai mic timp de asteptare

        Server minim = servers.get(0);
        for (Server server : servers) {
            if (server.getWaitingPeriod().intValue() < minim.getWaitingPeriod().intValue()) {
                minim = server;
            }
        }

        //verific daca este loc in coada cu cel mai mic timp de asteptare
        if (minim.getNrOfTasks() < Scheduler.getMaxTasksPerServer()) {
            minim.addTask(t);
        } else {
            Server server2 = null;
            for (Server server : servers) {
                if (server.getWaitingPeriod().intValue() > minim.getWaitingPeriod().intValue() &&
                        server.getNrOfTasks() < Scheduler.getMaxTasksPerServer()) {
                    server2 = server;
                    break;
                }
            }
            //daca gasim loc in a doua cautare de server, adaugam task-ul
            if (server2 != null) {
                server2.addTask(t);
            }
        }
    }

}

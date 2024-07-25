package example.Logic;

import example.Model.Server;
import example.Model.Task;
import java.util.List;

public class ConcreteStrategyQueue implements Strategy {
    @Override
    public void addTask(List<Server> servers, Task t) { //adaug task-ul in coada de lungime minima
        int minim = servers.get(0).getTasks().length;
        int queue = 0;

        for (int i = 1; i < servers.size(); i++) {
            int currentSize = servers.get(i).getTasks().length;
            if (currentSize < minim) {
                minim = currentSize;
                queue = i;
            }
        }
        //adaug sarcina la coada cu cel mai mic număr de sarcini în coadă
        servers.get(queue).addTask(t);
    }
}

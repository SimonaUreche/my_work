package example.Logic;

import example.Model.Server;
import example.Model.Task;
import java.util.List;

public interface Strategy {
    public void addTask(List<Server> servers, Task t);
}

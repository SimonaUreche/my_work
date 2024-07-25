package example.Model;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.atomic.AtomicInteger;
public class Server implements Runnable {
    private BlockingQueue<Task> tasks;
    private AtomicInteger waitingPeriod;
    private static AtomicInteger waitingPeriodAverage;
    public Server(int capacity) {
        this.tasks = new ArrayBlockingQueue<>(capacity);
        this.waitingPeriod = new AtomicInteger(0);
        this.waitingPeriodAverage = new AtomicInteger(0);
    }
    public void addTask(Task newTask) {
        tasks.add(newTask);
        waitingPeriod.addAndGet(newTask.getServiceTime()); //adaug timpul de servire al task-ului la waitingPeriod

        int waitingPeriod1 = waitingPeriod.get();
        int waitingPeriodAverage1 = waitingPeriodAverage.get();
        waitingPeriodAverage.set(waitingPeriodAverage1 + waitingPeriod1);
    }

    @Override
    public void run() {
        while (true) {
            synchronized (tasks) { //un singur fir de executie poate accesa resursa in acelasi timp
                //verific dacă există sarcini în coadă
                if (!tasks.isEmpty()) {
                    //egtrag prima sarcină din coadă
                    Task extracted = tasks.peek();
                    if (extracted != null) {
                        try {
                            //procesam sarcina timp de 1 sec
                            Thread.sleep(1000);
                            //daca sarcina esste in procesare scadem timpul de servire al sarcinii
                            extracted.decrementServiceTime();

                            if (extracted.getServiceTime() > 0) { //atunci cand sarcina este procesata partial
                                waitingPeriod.decrementAndGet(); //o parte din timpul de astepare al cozii este eliminat
                            }
                            if (extracted.getServiceTime() == 0) {
                                tasks.poll(); //cand sarcina este procesata complet o scoatem din coada
                            }
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                            return;
                        }
                    }
                }
            }
        }
    }
    public Task[] getTasks() {
        Task[] taskArray = new Task[tasks.size()];
        tasks.toArray(taskArray);
        return taskArray;
    }
    public AtomicInteger getWaitingPeriod() {
        return waitingPeriod;
    }
    public synchronized long getNrOfTasks() {
        return tasks.size();
    } //realizeaza o operatie atomica care trebuie protejata in cazul modificarilor din thread uri concurente
    public static int getWaitingTimeAverage() {
        if (waitingPeriodAverage == null) {
            return 0;
        }
        return waitingPeriodAverage.get();
    }
}
package example.Model;
public class Task implements Comparable<Task> {
    private int ID;
    private int arrivalTime;
    private int serviceTime; //timpul necesar pentu a servi clientul
    public Task(int ID, int arrivalTime, int serviceTime)
    {
        this.ID = ID;
        this.arrivalTime = arrivalTime;
        this.serviceTime = serviceTime;
    }

    public int getArrivalTime() {
        return arrivalTime;
    }
    public int getServiceTime() {
        return serviceTime;
    }
    public void decrementServiceTime() {
        serviceTime--;
    }

    @Override
    public int compareTo(Task otherTask) {
        return this.arrivalTime - otherTask.arrivalTime;
    }

    @Override
    public String toString(){
        return "(" + (this.ID) + ", " + this.arrivalTime + ", " + this .serviceTime + ")";
    }
}

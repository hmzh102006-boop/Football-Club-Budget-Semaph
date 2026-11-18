حمزة محمد - Class: 4TI - No: 12

class Manager extends Thread {
    int from, to, amount, times;

    public Manager(int from, int to, int amount, int times) {
        this.from = from;
        this.to = to;
        this.amount = amount;
        this.times = times;
    }

    public void run() {
        for (int i = 0; i < times; i++) {
            try {
                Thread.sleep(2);
            } catch (InterruptedException e) {
            }

            
            int firstLock, secondLock;
            if (from < to) {
                firstLock = from;
                secondLock = to;
            } else {
                firstLock = to;
                secondLock = from;
            }

            Main.locks[firstLock].acquire();
            Main.locks[secondLock].acquire();

        
            Main.balance[from] -= amount;
            Main.balance[to] += amount;

            Main.locks[secondLock].release();
            Main.locks[firstLock].release();
        }
    }
}

public class Main {
    static int[] balance = {900, 800, 700};
    static MySemaphore[] locks = {
        new MySemaphore(1),
        new MySemaphore(1),
        new MySemaphore(1)
    };

    public static void main(String[] args) throws InterruptedException {
        Manager t1 = new Manager(0, 2, 25, 16); 
        Manager t2 = new Manager(2, 1, 40, 9);  
        Manager t3 = new Manager(1, 0, 30, 14); 

        t1.start();
        t2.start();
        t3.start();

        t1.join();
        t2.join();
        t3.join();

        System.out.println("Balance Youth: " + balance[0]);
        System.out.println("Balance Coaching: " + balance[1]);
        System.out.println("Balance Stadium: " + balance[2]);
        
        int total = balance[0] + balance[1] + balance[2];
        System.out.println("Total: " + total);
    }
}
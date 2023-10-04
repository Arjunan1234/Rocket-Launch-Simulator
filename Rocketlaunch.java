import java.util.Scanner;

class RocketLaunchSimulator {
    private String stage = "Pre-Launch";
    private int fuel = 100;
    private int altitude = 0;
    private int speed = 0;
    private boolean launchStarted = false;

    public void startChecks() {
        if (launchStarted) {
            System.out.println("Launch has already begun. Cannot perform checks now.");
        } else {
            System.out.println("Performing system checks...");
            try {
                Thread.sleep(2000);  
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("All systems are 'Go' for launch.");
        }
    }

    public void launch() {
        if (stage.equals("Pre-Launch")) {
            launchStarted = true;
            System.out.println("Rocket launch initiated.");
            while (fuel > 0) {
                updateStatus();
                try {
                    Thread.sleep(1000);  
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            System.out.println("Mission Failed due to insufficient fuel.");
        }
    }

    public void fastForward(int seconds) {
        if (!launchStarted) {
            System.out.println("Launch has not started yet.");
            return;
        }

        for (int i = 0; i < seconds; i++) {
            updateStatus();
            try {
                Thread.sleep(1000);  // Simulate 1 second of flight
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void updateStatus() {
        if (fuel > 0) {
            stage = "In-Flight";
            fuel -= 10;
            altitude += 10;
            speed += 1000;  // 1000km/h
            System.out.println("Stage: " + stage + ", Fuel: " + fuel + "%, Altitude: " + altitude + " km, Speed: " + speed + " km/h");
        } else {
            stage = "Mission Failed";
        }

        if (altitude >= 100) {
            stage = "Orbit Placement";
            System.out.println("Orbit achieved! Mission Successful.");
            launchStarted = false;
        }
    }

    public static void main(String[] args) {
        RocketLaunchSimulator simulator = new RocketLaunchSimulator();
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.print("Enter a command (start_checks, launch, fast_forward X, or exit): ");
            String command = scanner.nextLine().trim();

            if (command.equals("start_checks")) {
                simulator.startChecks();
            } else if (command.equals("launch")) {
                simulator.launch();
            } else if (command.startsWith("fast_forward")) {
                try {
                    int seconds = Integer.parseInt(command.split(" ")[1]);
                    simulator.fastForward(seconds);
                } catch (NumberFormatException | ArrayIndexOutOfBoundsException e) {
                    System.out.println("Invalid command. Use 'fast_forward X' where X is the number of seconds.");
                }
            } else if (command.equals("exit")) {
                break;
            } else {
                System.out.println("Invalid command. Please enter a valid command.");
            }
        }

        scanner.close();
    }
}


import java.util.Scanner;

public class RocketLaunchSimulator {
    private String stage = "Pre-Launch";
    private int fuel = 100;
    private int altitude = 0;
    private int speed = 1000;
    private boolean launchStarted = false;
    private boolean statuscheck = false;
    private int counter = 1;

    public void startChecks() {

        System.out.println("Performing system checks...");
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        statuscheck = true;
        System.out.println("All systems are 'Go' for launch.");

    }

    public void launch() {
        if (statuscheck == true) {
            if (altitude >= 100) {
                System.out.println("Already  launched rocket");
            } else if (stage.equals("Pre-Launch")) {
                launchStarted = true;
                System.out.println("Rocket launch initiated.");
                while (altitude < 100 && fuel > 0) {
                    updateStatus();
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    if (fuel <= 0 && altitude < 100) {
                        System.out.println(" Mission Failed due to insufficient fuel.");
                        break;
                    }
                }

            }

        } else {
            System.out.println(" Cannot launched initiate start_checks");
        }
    }

    public void fastForward(int seconds) {
        if (statuscheck == true) {

            int tempcounter = counter;
            int tempaltitude = altitude;
            int tempfuel = fuel;
            for (int i = 0; i < seconds; i++) {
                tempfuel -= 10;
                tempaltitude += 10;
                if (tempaltitude >= 50 && tempcounter == 1) {
                    tempcounter = 2;

                }
                try {
                    Thread.sleep(1000); // Simulate 1 second of flight
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }
            System.out.println("Stage: " + tempcounter + ", Fuel: " + tempfuel + "%, Altitude: " + tempaltitude
                    + " km, Speed: " + speed + " km/h");
        } else {
            System.out.println(" First initiate start_checks ");
        }
    }

    public void updateStatus() {

        if (fuel > 0) {

            fuel -= 10;
            altitude += 10;
            if (altitude >= 50 && counter == 1) {
                counter = 2;
                System.out.println("Stage 1 complete. Separating stage. Entering Stage 2.\n");
            }
            System.out.println("Stage: " + counter + ", Fuel: " + fuel + "%, Altitude: " + altitude + " km, Speed: "
                    + speed + " km/h");

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
        System.out.print(
                "This is Rocket Launch Simulator \n ");
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

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Scanner;

public class CalculateRewardPoints {

    static class Rewards {
        int rewards[];
        String customerName;

        public Rewards(String name) {
            this.customerName = name;
            this.rewards = new int[13];
        }

        void addReward(int month, int reward) {
            rewards[month] = reward;
        }

        @Override
        public String toString() {
            String customer = "Customer: " + customerName + "\n";
            int idx = 1;
            while (idx <= 12) {
                customer += "Month " + idx + ": Rewards " + rewards[idx] + "\n";
                idx++;
            }
            return customer;
        }

    }

    public static int getRewards(int amount) {
        if (amount <= 50) {
            return 0;
        }
        if (amount <= 100) {
            return amount - 50;
        }
        return (amount - 100) * 2 + 50;
    }

    public static void main(String[] args) {
        // Fetching data from a txt file
        File rewardsFile = new File("/Users/vidhyadhar25/Desktop/RewardsFile.txt");

        HashMap<String, Rewards> customerRewards = new HashMap<>();

        try {
            Scanner scanner = new Scanner(rewardsFile);

            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                // Line has format: Name, Txn Amount, YYYY-MM-DD

                // Split string into tokens.
                String split[] = line.split(",");
                String custName = split[0];
                int saleAmount = Integer.parseInt(split[1]);
                int reward = getRewards(saleAmount);
                int month = Integer.parseInt(split[2].split("-")[1]);

                if (customerRewards.containsKey(custName)) {
                    customerRewards.get(custName).addReward(month, reward);
                } else {
                    Rewards rewards = new Rewards(custName);
                    rewards.addReward(month, reward);
                    customerRewards.put(custName, rewards);
                }
            }

            scanner.close();

            for (Rewards c : customerRewards.values()) {
                System.out.println(c);
            }

        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}

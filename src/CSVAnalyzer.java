import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
public class CSVAnalyzer {

    public static void main(String[] args) {
        String baseUrl = "https://people.sc.fsu.edu/~jburkardt/data/csv/";
        String[] csvFiles = {
                "airtravel.csv",
                "snakes_count_100.csv",
                "crash_catalonia.csv",
                "freshman_lbs.csv",
                "cities.csv",
                "ford_escort.csv",
                "letter_frequency.csv",
                "tally_cab.csv",
                "oscar_age_male.csv",
                "hw_200.csv",
                "lead_shot.csv",
                "freshman_kgs.csv",
                "snakes_count_1000.csv",
                "example.csv",
                "taxables.csv",
                "zillow.csv",
                "trees.csv",
                "nile.csv",
                "snakes_count_10000.csv",
                "oscar_age_female.csv",
                "mlb_teams_2012.csv",
                "grades.csv",
                "homes.csv",
                "faithful.csv",
                "biostats.csv",
                "news_decline.csv",
                "mlb_players.csv",
                "hooke.csv",
                "addresses.csv",
                "snakes_count_10.csv",
                "hurricanes.csv",
                "deniro.csv",
                "hw_25000.csv"
        };

        List<Thread> threads = new ArrayList<>();

        for (String csvFile : csvFiles) {
            String url = baseUrl + csvFile;
            Thread thread = new Thread(() -> processCSV(url));
            threads.add(thread);
            thread.start();
        }

        for (Thread thread : threads) {
            try {
                thread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private static void processCSV(String url) {
        try {
            HttpURLConnection connection = (HttpURLConnection) new URL(url).openConnection();

            if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                int lineCount = getLineCount(connection);
                System.out.println("File: " + url + " - Line Count: " + lineCount);
            } else {
                System.err.println("Error processing " + url + ": HTTP " + connection.getResponseCode());
            }

            connection.disconnect();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static int getLineCount(HttpURLConnection connection) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        int lineCount = 0;

        while (reader.readLine() != null) {
            lineCount++;
        }

        reader.close();
        return lineCount;
    }
}

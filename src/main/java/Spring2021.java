import java.io.File;
import java.io.IOException;
import java.util.Properties;
import java.util.Random;
import java.util.ArrayList;
import java.util.Map;

import com.codingame.gameengine.runner.MultiplayerGameRunner;

public class Spring2021 {

    static String[] DEFAULT_AI = new String[] {
        "python3", "config/Boss.py"
    };
    static String[] BOSS_WOOD2 = new String[] {
        "python3", "config/level1/Boss.py"
    };
    static String[] BOSS_WOOD1 = new String[] {
        "python3", "config/level2/Boss.py"
    };

    static String[] player1 = new String[] {
        "./player1.out"
    };

    static String[] player2  = new String[] {
        "./player2.out"
    };

    public static void main(String[] args) throws IOException, InterruptedException {
        launchGame();
    }

    public static void launchGame() throws IOException, InterruptedException {
        Random rng = new Random(0);
        ArrayList<Integer> win = new ArrayList<Integer>();
        win.add(0); win.add(0); win.add(0);
        for (int i = 0; i < 10000; i++){
            Long seed = rng.nextLong();
            for (int j = 0; j < 2; j++){
                MultiplayerGameRunner gameRunner = new MultiplayerGameRunner();
                gameRunner.setLeagueLevel(3);
                Properties gameParameters = new Properties();
                gameRunner.setGameParameters(gameParameters);

                if (j == 0){
                    gameRunner.addAgent(
                        player1,
                        "Tororo",
                        "https://static.codingame.com/servlet/fileservlet?id=61910307869345"
                    );
                    
                    gameRunner.addAgent(
                        player2,
                        "Ghilbib",
                        "https://static.codingame.com/servlet/fileservlet?id=61910289640958"
                    );
                } else {
                    gameRunner.addAgent(
                        player2,
                        "Ghilbib",
                        "https://static.codingame.com/servlet/fileservlet?id=61910289640958"
                    );

                    gameRunner.addAgent(
                        player1,
                        "Tororo",
                        "https://static.codingame.com/servlet/fileservlet?id=61910307869345"
                    );
                }
                
                gameRunner.setSeed(seed);

                Map<Integer, Integer> result = gameRunner.simulate().scores;
                if (j == 1){
                    Integer tmp = result.get(0);
                    result.put(0, result.get(1));
                    result.put(1, tmp);
                }
                
                if (result.get(0) > result.get(1)){
                    win.set(0, win.get(0) + 1);
                    System.out.println(String.format("(%d) 0: [%d], 1: %d", i, result.get(0), result.get(1)));
                }
                else if (result.get(0) < result.get(1)){
                    win.set(1, win.get(1) + 1);
                    System.out.println(String.format("(%d) 0: %d, 1: [%d]", i, result.get(0), result.get(1)));
                }
                else{
                    win.set(2, win.get(2) + 1);
                    System.out.println(String.format("(%d) 0: %d, 1: %d", i, result.get(0), result.get(1)));
                }
                if ((i + 1) % 10 == 0){
                    System.out.println(String.format("win 0: %f, win 1: %f, draw: %f",
                                        (double)win.get(0) / (i + 1), (double)win.get(1) / (i + 1), (double)win.get(2) / (i + 1)));
                }

                if (j == 0)
                    i++;
            }
        }
    }
}

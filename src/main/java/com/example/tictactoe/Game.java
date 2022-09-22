package com.example.tictactoe;

import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;

import java.net.URL;
import java.util.ArrayList;
import java.util.Random;
import java.util.ResourceBundle;

/**
 * Game class, this class is where everything happens
 * in this class has the functions, setPlayerIcon(), onMouseClicked(), ComputersPlay(), CheckBestPlay() and CheckWin()
 */
public class Game {
    @FXML
    private AnchorPane AnchorPane00, AnchorPane01, AnchorPane02, AnchorPane10, AnchorPane11, AnchorPane12, AnchorPane20, AnchorPane21, AnchorPane22;
    String Player, Bot;
    Color playercolor, botcolor;

    /**
     * This Function sets player icon by passing a String with icon name
     * @param player
     */
    public void setPlayerIcon(String player){
        if(player.equals("TIMES")){
            Player = player;
            Bot = "CIRCLE_ALT";
        }else{
            Player = player;
            Bot = "TIMES";
        }

    }

    /**
     * This Function sets player icon by passing a color value
     * @param color
     */

    public void setPlayerColor(Color color) {
        if (color.equals(Color.BLUE)) {
            playercolor = color;
            botcolor = Color.RED;
        }else{
            playercolor = color;
            botcolor = Color.BLUE;
        }
    }

    /**
     * This Fuction happens when the player clicks somewhere in the screen, its check if where the player clicked has alredy
     * an icon, if so it doesn't do anything otherwise it puts the player icon where the player clicked, than check if the player won
     * otherwise runs the ComputerPlay()
     * @param mouseEvent
     */
    public void onMouseClicked(MouseEvent mouseEvent) {
        Node source = (Node) mouseEvent.getSource();
        AnchorPane quadrante = (AnchorPane) source;
        if (quadrante.getChildren().isEmpty()) {
            FontAwesomeIconView player = new FontAwesomeIconView();
            player.setSize("100");
            player.setGlyphName(Player);
            player.setFill(playercolor);
            AnchorPane.setBottomAnchor(player, 0.0);
            AnchorPane.setTopAnchor(player, 0.0);
            AnchorPane.setLeftAnchor(player, 0.0);
            AnchorPane.setRightAnchor(player, 0.0);
            quadrante.getChildren().add(player);
            AnchorPane[] anchorPanes = {AnchorPane00, AnchorPane01, AnchorPane02, AnchorPane10, AnchorPane11, AnchorPane12, AnchorPane20, AnchorPane21, AnchorPane22};
            if(CheckWin()){
                for (AnchorPane anchorPane :anchorPanes) {
                    anchorPane.setOnMouseClicked(null);
                }
                if(Player.equals("TIMES")){
                    System.out.println("Vitória do X");
                }else{
                    System.out.println("Vitória do circulo");
                }
            }else{
                for (AnchorPane anchorPane: anchorPanes) {
                    if(anchorPane.getChildren().isEmpty()){
                        ComputersPlay();
                        return;
                    }
                }
                System.out.println("EMPATE");
                return;
            }
        }

    }

    /**
     * This Fuction when is called runs CheckBestPlay() if returns null, then it gets a random anchorpane from the list created
     * and play on the random anchorpane, and then check if it was a win, if it does than the game disable the function
     * onMouseClicked()
     *
     */
    public void ComputersPlay() {
        AnchorPane[] anchorPanes = {AnchorPane00, AnchorPane01, AnchorPane02, AnchorPane10, AnchorPane11, AnchorPane12, AnchorPane20, AnchorPane21, AnchorPane22};
        ArrayList<AnchorPane> possibilites = new ArrayList<>();
        for (AnchorPane anchorPane : anchorPanes) {
            if (anchorPane != null && anchorPane.getChildren().isEmpty()) {
                    possibilites.add(anchorPane);
            }
        }
        FontAwesomeIconView bot=new FontAwesomeIconView();
        bot.setSize("100");
        bot.setGlyphName(Bot);
        bot.setFill(botcolor);
        AnchorPane.setBottomAnchor(bot, 0.0);
        AnchorPane.setTopAnchor(bot, 0.0);
        AnchorPane.setLeftAnchor(bot, 0.0);
        AnchorPane.setRightAnchor(bot, 0.0);
        AnchorPane bestPlay =  CheckBestPlay(Bot);
        if(bestPlay==null){
            bestPlay = CheckBestPlay(Player);
        }
        if(bestPlay!=null) {
            bestPlay.getChildren().add(bot);
            if (CheckWin()) {
                for (AnchorPane anchorPane : anchorPanes) {
                    anchorPane.setOnMouseClicked(null);
                }
                if (Player.equals("TIMES")) {
                    System.out.println("Vitória do circulo");
                } else {
                    System.out.println("Vitória do X");
                }
                return;
            }
        }else{
                Random random = new Random();
                int rad = random.nextInt(possibilites.size());
                possibilites.get(rad).getChildren().add(bot);
                System.out.println("RANDOM");
        }

    }

    /**
     * This Fuction was the most complicated to make, and it was based on CheckWin(), it checks if on the grid has an array of the length 2 with the same value
     * if it does have, and the value is the same as the bot then return the anchorpane for the win, in case of any bots plays
     * forms an array of two, then its check if there's an array of player plays that form an array of two, then returns the
     * anchorpane value so the player can't win on the next round
     * @return AnchorPosition
     */
    public AnchorPane CheckBestPlay(String text) {
        AnchorPane[][] anchorPanesgrid = {{AnchorPane00, AnchorPane10, AnchorPane20}, {AnchorPane01, AnchorPane11, AnchorPane21}, {AnchorPane02, AnchorPane12, AnchorPane22}};
        for (int i = 0; i < 3; i++) { //line
            for (int j = 0; j < 3; j++) { //column
                if(!anchorPanesgrid[i][j].getChildren().isEmpty()){
                    if(((FontAwesomeIconView)anchorPanesgrid[i][j].getChildren().get(0)).getGlyphName().equals(text)) {
                        if(i==0 && j==0 && !anchorPanesgrid[i+1][j+1].getChildren().isEmpty()){//diagonal from up to down end
                            if (((FontAwesomeIconView)anchorPanesgrid[i][j].getChildren().get(0)).getGlyphName().equals(((FontAwesomeIconView)anchorPanesgrid[i + 1][j + 1].getChildren().get(0)).getGlyphName())) {
                                if (anchorPanesgrid[i + 2][j + 2].getChildren().isEmpty()) {
                                    return anchorPanesgrid[i + 2][j + 2];
                                }
                            }
                        }if ((i == 0 && j == 0 && !anchorPanesgrid[i + 2][j + 2].getChildren().isEmpty())) {//diagonal from up to down middle
                            if (((FontAwesomeIconView)anchorPanesgrid[i][j].getChildren().get(0)).getGlyphName().equals(((FontAwesomeIconView)anchorPanesgrid[i + 2][j + 2].getChildren().get(0)).getGlyphName())) {
                                if( anchorPanesgrid[i + 1][j + 1].getChildren().isEmpty()) {
                                    return anchorPanesgrid[i + 1][j + 1];
                                }
                            }
                        }if ((i == 1 && j == 1 && !anchorPanesgrid[i + 1][j + 1].getChildren().isEmpty())) {//diagonal from up to down start
                            if (((FontAwesomeIconView)anchorPanesgrid[i][j].getChildren().get(0)).getGlyphName().equals(((FontAwesomeIconView)anchorPanesgrid[i + 1][j + 1].getChildren().get(0)).getGlyphName())) {
                                if(anchorPanesgrid[i - 1][j - 1].getChildren().isEmpty()) {
                                    return anchorPanesgrid[i - 1][j - 1];
                                }
                            }
                        }if(i==2 && j==0 && !anchorPanesgrid[i-1][j+1].getChildren().isEmpty()){//diagonal from down to up end
                            if (((FontAwesomeIconView)anchorPanesgrid[i][j].getChildren().get(0)).getGlyphName().equals(((FontAwesomeIconView)anchorPanesgrid[i - 1][j + 1].getChildren().get(0)).getGlyphName())) {
                                if(anchorPanesgrid[i - 2][j + 2].getChildren().isEmpty()) {
                                    return anchorPanesgrid[i - 2][j + 2];
                                }
                            }
                        }if ((i == 2 && j == 0 && !anchorPanesgrid[i - 2][j + 2].getChildren().isEmpty())) {//diagonal from down to up middle
                            if (((FontAwesomeIconView)anchorPanesgrid[i][j].getChildren().get(0)).getGlyphName().equals(((FontAwesomeIconView)anchorPanesgrid[i - 2][j + 2].getChildren().get(0)).getGlyphName())) {
                                if(anchorPanesgrid[i - 1][j + 1].getChildren().isEmpty()) {
                                    return anchorPanesgrid[i - 1][j + 1];
                                }
                            }
                        }if ((i == 1 && j == 1 && !anchorPanesgrid[i - 1][j + 1].getChildren().isEmpty())) {//diagonal from down to up start
                            if (((FontAwesomeIconView)anchorPanesgrid[i][j].getChildren().get(0)).getGlyphName().equals(((FontAwesomeIconView)anchorPanesgrid[i - 1][j + 1].getChildren().get(0)).getGlyphName())) {
                                if(anchorPanesgrid[i + 1][j - 1].getChildren().isEmpty()) {
                                    return anchorPanesgrid[i + 1][j - 1];
                                }
                            }
                        }if (j == 0 && !anchorPanesgrid[i][j + 1].getChildren().isEmpty()) {//row at end
                            if (((FontAwesomeIconView)anchorPanesgrid[i][j].getChildren().get(0)).getGlyphName().equals(((FontAwesomeIconView)anchorPanesgrid[i][j + 1].getChildren().get(0)).getGlyphName())) {
                                if(anchorPanesgrid[i][j + 2].getChildren().isEmpty()) {
                                    return anchorPanesgrid[i][j + 2];
                                }
                            }
                        }if (j == 0 && !anchorPanesgrid[i][j + 2].getChildren().isEmpty()) {//row at middle
                            if (((FontAwesomeIconView)anchorPanesgrid[i][j].getChildren().get(0)).getGlyphName().equals(((FontAwesomeIconView)anchorPanesgrid[i][j +2].getChildren().get(0)).getGlyphName())) {
                                if(anchorPanesgrid[i][j + 1].getChildren().isEmpty()) {
                                    return anchorPanesgrid[i][j + 1];
                                }
                            }
                        }if (j == 1 && !anchorPanesgrid[i][j + 1].getChildren().isEmpty()) {//row at start
                            if (((FontAwesomeIconView)anchorPanesgrid[i][j].getChildren().get(0)).getGlyphName().equals(((FontAwesomeIconView)anchorPanesgrid[i][j + 1].getChildren().get(0)).getGlyphName())) {
                                if(anchorPanesgrid[i][j - 1].getChildren().isEmpty()) {
                                    return anchorPanesgrid[i][j - 1];
                                }
                            }
                        }
                        if (i == 0 && !anchorPanesgrid[i + 1][j].getChildren().isEmpty()) {//column at end
                            if (((FontAwesomeIconView)anchorPanesgrid[i][j].getChildren().get(0)).getGlyphName().equals(((FontAwesomeIconView)anchorPanesgrid[i + 1][j].getChildren().get(0)).getGlyphName())) {
                                if(anchorPanesgrid[i + 1][j].getChildren().isEmpty()) {
                                    return anchorPanesgrid[i + 1][j];
                                }
                            }
                        }if (i == 0 && !anchorPanesgrid[i + 2][j].getChildren().isEmpty()) {//column at middle
                            if (((FontAwesomeIconView)anchorPanesgrid[i][j].getChildren().get(0)).getGlyphName().equals(((FontAwesomeIconView)anchorPanesgrid[i + 2][j].getChildren().get(0)).getGlyphName())) {
                                if(anchorPanesgrid[i + 1][j].getChildren().isEmpty()) {
                                    return anchorPanesgrid[i + 1][j];
                                }
                            }
                        }if (i == 1 && !anchorPanesgrid[i + 1][j].getChildren().isEmpty()) {//column at start
                            if (((FontAwesomeIconView)anchorPanesgrid[i][j].getChildren().get(0)).getGlyphName().equals(((FontAwesomeIconView)anchorPanesgrid[i + 1][j].getChildren().get(0)).getGlyphName())) {
                                if(anchorPanesgrid[i - 1][j].getChildren().isEmpty()) {
                                    return anchorPanesgrid[i - 1][j];
                                }
                            }
                        }
                    }
                }
            }
        }
        return null;
    }

    /**
     * This Fuction Checks if the game is over, basically transforming the grind in a matrix and checking if
     * there's a row, column, or diagonal fulfilled with the same value, its return a boolean value
     * true or false if its true someone won, if false the game continues.
     * @return Win
     */
    public Boolean CheckWin() {
        AnchorPane[][] anchorPanesgrid = {{AnchorPane00, AnchorPane10, AnchorPane20}, {AnchorPane01, AnchorPane11, AnchorPane21}, {AnchorPane02, AnchorPane12, AnchorPane22}};
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                FontAwesomeIconView fontAwesomeIconView, fontAwesomeIconView1, fontAwesomeIconView2;
                if (!anchorPanesgrid[i][j].getChildren().isEmpty()) {
                    fontAwesomeIconView = (FontAwesomeIconView) anchorPanesgrid[i][j].getChildren().get(0);
                    if (i == 0 && j == 0) {
                        if (!(anchorPanesgrid[i + 1][j + 1].getChildren().isEmpty()) && !(anchorPanesgrid[i + 2][j + 2].getChildren().isEmpty())) {
                            fontAwesomeIconView1 = (FontAwesomeIconView) anchorPanesgrid[i+1][j+1].getChildren().get(0);
                            fontAwesomeIconView2 = (FontAwesomeIconView) anchorPanesgrid[i+2][j+2].getChildren().get(0);
                            if(fontAwesomeIconView.getGlyphName().equals(fontAwesomeIconView1.getGlyphName()) && fontAwesomeIconView.getGlyphName().equals(fontAwesomeIconView2.getGlyphName())){
                                return true;
                            }
                        }
                    }if (i == 0 && j == 2) {
                        if (!(anchorPanesgrid[i + 1][j - 1].getChildren().isEmpty()) && !(anchorPanesgrid[i + 2][j - 2].getChildren().isEmpty())) {
                            fontAwesomeIconView1 = (FontAwesomeIconView) anchorPanesgrid[i+1][j-1].getChildren().get(0);
                            fontAwesomeIconView2 = (FontAwesomeIconView) anchorPanesgrid[i+2][j-2].getChildren().get(0);
                            if(fontAwesomeIconView.getGlyphName().equals(fontAwesomeIconView1.getGlyphName()) && fontAwesomeIconView.getGlyphName().equals(fontAwesomeIconView2.getGlyphName())){
                                return true;
                            }
                        }
                    }if (j == 0) {
                        if (!(anchorPanesgrid[i][j + 1].getChildren().isEmpty()) && !(anchorPanesgrid[i][j + 2].getChildren().isEmpty())) {
                            fontAwesomeIconView1 = (FontAwesomeIconView) anchorPanesgrid[i][j+1].getChildren().get(0);
                            fontAwesomeIconView2 = (FontAwesomeIconView) anchorPanesgrid[i][j+2].getChildren().get(0);
                            if(fontAwesomeIconView.getGlyphName().equals(fontAwesomeIconView1.getGlyphName()) && fontAwesomeIconView.getGlyphName().equals(fontAwesomeIconView2.getGlyphName())){
                                return true;
                            }
                        }
                    }if (i == 0) {
                        if (!(anchorPanesgrid[i + 1][j].getChildren().isEmpty()) && !(anchorPanesgrid[i + 2][j].getChildren().isEmpty())) {
                            fontAwesomeIconView1 = (FontAwesomeIconView) anchorPanesgrid[i+1][j].getChildren().get(0);
                            fontAwesomeIconView2 = (FontAwesomeIconView) anchorPanesgrid[i+2][j].getChildren().get(0);
                            if(fontAwesomeIconView.getGlyphName().equals(fontAwesomeIconView1.getGlyphName()) && fontAwesomeIconView.getGlyphName().equals(fontAwesomeIconView2.getGlyphName())){
                                return true;
                            }
                        }
                    }
                }
            }
        }
        return false;
    }

}
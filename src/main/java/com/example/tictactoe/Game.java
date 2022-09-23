package com.example.tictactoe;

import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.Random;

/**
 * Game class, this class is where everything happens
 * in this class has the functions, setPlayerIcon(), onMouseClicked(), ComputersPlay(), CheckBestPlay() and CheckWin()
 */
public class Game {
    @FXML
    private AnchorPane AnchorPane00, AnchorPane01, AnchorPane02, AnchorPane10, AnchorPane11, AnchorPane12, AnchorPane20, AnchorPane21, AnchorPane22;
    @FXML
    private Label placar;
    @FXML
    private Button restartButton;
    int x=0, o=0;  //score for x and o
    String Player, Bot;
    Color playercolor, botcolor;


    /**
     * This Function sets player icon by passing a String with icon name
     * @param player String in which defines what is the player's icon
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
     * @param color Color in which defines what is the player's colors
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
     * @param mouseEvent MouseEvent and cast as an AnchorPane
     */
    public void onMouseClicked(MouseEvent mouseEvent) {
        Node source = (Node) mouseEvent.getSource();
        AnchorPane quadrante = (AnchorPane) source;
        if (quadrante.getChildren().isEmpty()) {
            quadrante.getChildren().add(InitializeIcon(Player, playercolor));
            AnchorPane[] anchorPanes = {AnchorPane00, AnchorPane01, AnchorPane02, AnchorPane10, AnchorPane11, AnchorPane12, AnchorPane20, AnchorPane21, AnchorPane22};
            if(CheckWin()){
                for (AnchorPane anchorPane :anchorPanes) {
                    anchorPane.setOnMouseClicked(null);
                }
                if(Player.equals("TIMES")){
                    x+=1;
                    placar.setText(x+" X "+o);
                }else{
                    o+=1;
                    placar.setText(x+" X "+o);
                }
                restartButton.setDisable(false);
            }else{
                for (AnchorPane anchorPane: anchorPanes) {
                    if(anchorPane.getChildren().isEmpty()){
                        ComputersPlay();
                        return;
                    }
                }
                restartButton.setDisable(false);
            }
        }

    }

    /**
     * This function is just for redundant usage of the same code, it initializes a FontAwesomeIconView for the player
     * and bot.
     * @param icon gets the player or bot icon's
     * @param color get the player or bot color's
     * @return return a new FontAwesomeIconView
     */
    private FontAwesomeIconView InitializeIcon(String icon, Color color) {
        FontAwesomeIconView player = new FontAwesomeIconView();
        player.setSize("100");
        player.setGlyphName(icon);
        player.setFill(color);
        AnchorPane.setBottomAnchor(player, 45.0);
        AnchorPane.setTopAnchor(player, 45.0);
        AnchorPane.setLeftAnchor(player, 45.0);
        AnchorPane.setRightAnchor(player, 45.0);
        return player;
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
        AnchorPane bestPlay =  CheckBestPlay(Bot);
        if(bestPlay==null){
            bestPlay = CheckBestPlay(Player);
        }
        if(bestPlay!=null) {
            bestPlay.getChildren().add(InitializeIcon(Bot, botcolor));
            if (CheckWin()) {
                for (AnchorPane anchorPane : anchorPanes) {
                    anchorPane.setOnMouseClicked(null);
                }
                if (Player.equals("TIMES")) {
                    o+=1;
                    placar.setText(x+" X "+o);
                } else {
                    x+=1;
                    placar.setText(x+" X "+o);
                }
                restartButton.setDisable(false);
            }
        }else{
            Random random = new Random();
            int rad = random.nextInt(possibilites.size());
            possibilites.get(rad).getChildren().add(InitializeIcon(Bot, botcolor));
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
                        if(i==0 && j==0 && !anchorPanesgrid[i+1][j+1].getChildren().isEmpty()){//diagonal from up to down, end
                            if (((FontAwesomeIconView)anchorPanesgrid[i][j].getChildren().get(0)).getGlyphName().equals(((FontAwesomeIconView)anchorPanesgrid[i + 1][j + 1].getChildren().get(0)).getGlyphName())) {
                                if (anchorPanesgrid[i + 2][j + 2].getChildren().isEmpty()) {
                                    return anchorPanesgrid[i + 2][j + 2];
                                }
                            }
                        }if ((i == 0 && j == 0 && !anchorPanesgrid[i + 2][j + 2].getChildren().isEmpty())) {//diagonal from up to down, middle
                            if (((FontAwesomeIconView)anchorPanesgrid[i][j].getChildren().get(0)).getGlyphName().equals(((FontAwesomeIconView)anchorPanesgrid[i + 2][j + 2].getChildren().get(0)).getGlyphName())) {
                                if( anchorPanesgrid[i + 1][j + 1].getChildren().isEmpty()) {
                                    return anchorPanesgrid[i + 1][j + 1];
                                }
                            }
                        }if ((i == 1 && j == 1 && !anchorPanesgrid[i + 1][j + 1].getChildren().isEmpty())) {//diagonal from up to down, start
                            if (((FontAwesomeIconView)anchorPanesgrid[i][j].getChildren().get(0)).getGlyphName().equals(((FontAwesomeIconView)anchorPanesgrid[i + 1][j + 1].getChildren().get(0)).getGlyphName())) {
                                if(anchorPanesgrid[i - 1][j - 1].getChildren().isEmpty()) {
                                    return anchorPanesgrid[i - 1][j - 1];
                                }
                            }
                        }if(i==2 && j==0 && !anchorPanesgrid[i-1][j+1].getChildren().isEmpty()){//diagonal from down to up, end
                            if (((FontAwesomeIconView)anchorPanesgrid[i][j].getChildren().get(0)).getGlyphName().equals(((FontAwesomeIconView)anchorPanesgrid[i - 1][j + 1].getChildren().get(0)).getGlyphName())) {
                                if(anchorPanesgrid[i - 2][j + 2].getChildren().isEmpty()) {
                                    return anchorPanesgrid[i - 2][j + 2];
                                }
                            }
                        }if ((i == 2 && j == 0 && !anchorPanesgrid[i - 2][j + 2].getChildren().isEmpty())) {//diagonal from down to up, middle
                            if (((FontAwesomeIconView)anchorPanesgrid[i][j].getChildren().get(0)).getGlyphName().equals(((FontAwesomeIconView)anchorPanesgrid[i - 2][j + 2].getChildren().get(0)).getGlyphName())) {
                                if(anchorPanesgrid[i - 1][j + 1].getChildren().isEmpty()) {
                                    return anchorPanesgrid[i - 1][j + 1];
                                }
                            }
                        }if ((i == 1 && j == 1 && !anchorPanesgrid[i - 1][j + 1].getChildren().isEmpty())) {//diagonal from down to up, start
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
                                if(anchorPanesgrid[i + 2][j].getChildren().isEmpty()) {
                                    return anchorPanesgrid[i + 2][j];
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

    public void onRestart() {
        AnchorPane[][] anchorPanesgrid = {{AnchorPane00, AnchorPane10, AnchorPane20}, {AnchorPane01, AnchorPane11, AnchorPane21}, {AnchorPane02, AnchorPane12, AnchorPane22}};
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (!anchorPanesgrid[i][j].getChildren().isEmpty()){
                    anchorPanesgrid[i][j].getChildren().remove(0);
                }
                anchorPanesgrid[i][j].setOnMouseClicked(this::onMouseClicked);
            }
        }
        restartButton.setDisable(true);
    }
}
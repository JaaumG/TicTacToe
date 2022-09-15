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

public class HelloController implements Initializable {
    @FXML
    private AnchorPane AnchorPane00, AnchorPane01, AnchorPane02, AnchorPane10, AnchorPane11, AnchorPane12, AnchorPane20, AnchorPane21, AnchorPane22;

    String Player, Bot;
    Color playercolor, botcolor;
    public void setPlayerIcon(String player){
        if(player.equals("TIMES")){
            Player = player;
            Bot = "CIRCLE_ALT";
        }else{
            Player = player;
            Bot = "TIMES";
        }

    }

    public void onMouseClicked(MouseEvent mouseEvent) {
        Node source = (Node) mouseEvent.getSource();
        AnchorPane quadrante = (AnchorPane) source;
        if (quadrante.getChildren().isEmpty()) {
            FontAwesomeIconView fontAwesomeIconView = new FontAwesomeIconView();
            fontAwesomeIconView.setSize("100");
            fontAwesomeIconView.setGlyphName(Player);
            fontAwesomeIconView.setFill(playercolor);
            AnchorPane.setBottomAnchor(fontAwesomeIconView, 0.0);
            AnchorPane.setTopAnchor(fontAwesomeIconView, 0.0);
            AnchorPane.setLeftAnchor(fontAwesomeIconView, 0.0);
            AnchorPane.setRightAnchor(fontAwesomeIconView, 0.0);
            quadrante.getChildren().add(fontAwesomeIconView);
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

    public void ComputersPlay() {
        AnchorPane[] anchorPanes = {AnchorPane00, AnchorPane01, AnchorPane02, AnchorPane10, AnchorPane11, AnchorPane12, AnchorPane20, AnchorPane21, AnchorPane22};
        ArrayList<AnchorPane> possibilites = new ArrayList<>();
        for (AnchorPane anchorPane : anchorPanes) {
            if (anchorPane != null && anchorPane.getChildren().isEmpty()) {
                    possibilites.add(anchorPane);
            }
        }
        FontAwesomeIconView fontAwesomeIconView = new FontAwesomeIconView();
        fontAwesomeIconView.setSize("100");
        fontAwesomeIconView.setGlyphName(Bot);
        fontAwesomeIconView.setFill(botcolor);
        AnchorPane.setBottomAnchor(fontAwesomeIconView, 0.0);
        AnchorPane.setTopAnchor(fontAwesomeIconView, 0.0);
        AnchorPane.setLeftAnchor(fontAwesomeIconView, 0.0);
        AnchorPane.setRightAnchor(fontAwesomeIconView, 0.0);
        if(CheckBestPlay()!=null) {
            if(CheckBestPlay().getChildren().isEmpty()) {
                CheckBestPlay().getChildren().add(fontAwesomeIconView);
                if (CheckWin()) {
                    for (AnchorPane anchorPane :anchorPanes) {
                        anchorPane.setOnMouseClicked(null);
                    }if(Player.equals("TIMES")){
                        System.out.println("Vitória do circulo");
                    }else{
                        System.out.println("Vitória do X");
                    }
                    return;
                }
            }else{
                System.out.println("Random1");
                Random random = new Random();
                int rad = random.nextInt(possibilites.size());
                possibilites.get(rad).getChildren().add(fontAwesomeIconView);
                return;
            }
        }else{
            System.out.println("Random2");
            Random random = new Random();
            int rad = random.nextInt(possibilites.size());
            possibilites.get(rad).getChildren().add(fontAwesomeIconView);
            return;
        }
    }
    public AnchorPane CheckBestPlay() {
        AnchorPane[][] anchorPanesgrid = {{AnchorPane00, AnchorPane10, AnchorPane20}, {AnchorPane01, AnchorPane11, AnchorPane21}, {AnchorPane02, AnchorPane12, AnchorPane22}};
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                FontAwesomeIconView fontAwesomeIconView, fontAwesomeIconView1;
                if (!anchorPanesgrid[i][j].getChildren().isEmpty()) {
                    fontAwesomeIconView = (FontAwesomeIconView) anchorPanesgrid[i][j].getChildren().get(0);
                    if(fontAwesomeIconView.getGlyphName().equals(Player)){
                        if (i == 0 && j == 0) {
                            if (!(anchorPanesgrid[i + 1][j + 1].getChildren().isEmpty()) && (anchorPanesgrid[i + 2][j + 2].getChildren().isEmpty())) {
                                fontAwesomeIconView1 = (FontAwesomeIconView) anchorPanesgrid[i+1][j+1].getChildren().get(0);
                                if(fontAwesomeIconView.getGlyphName().equals(fontAwesomeIconView1.getGlyphName())){
                                    return anchorPanesgrid[i+2][j+2];
                                }
                            }
                        }if (i == 2 && j == 0) {
                            if (!(anchorPanesgrid[i - 1][j + 1].getChildren().isEmpty()) && (anchorPanesgrid[i - 2][j + 2].getChildren().isEmpty())) {
                                fontAwesomeIconView1 = (FontAwesomeIconView) anchorPanesgrid[i-1][j+1].getChildren().get(0);
                                if(fontAwesomeIconView.getGlyphName().equals(fontAwesomeIconView1.getGlyphName())){
                                    return anchorPanesgrid[i-2][j+2];
                                }
                            }
                        }if (i == 0 && j == 2) {
                            if (!(anchorPanesgrid[i + 1][j - 1].getChildren().isEmpty()) && (anchorPanesgrid[i + 2][j - 2].getChildren().isEmpty())) {
                                fontAwesomeIconView1 = (FontAwesomeIconView) anchorPanesgrid[i+1][j-1].getChildren().get(0);
                                if(fontAwesomeIconView.getGlyphName().equals(fontAwesomeIconView1.getGlyphName())){
                                    return anchorPanesgrid[i+2][j-2];
                                }
                            }
                        }if (i == 2 && j == 2) {
                            if (!(anchorPanesgrid[i - 1][j - 1].getChildren().isEmpty()) && (anchorPanesgrid[i - 2][j - 2].getChildren().isEmpty())) {
                                fontAwesomeIconView1 = (FontAwesomeIconView) anchorPanesgrid[i-1][j-1].getChildren().get(0);
                                if(fontAwesomeIconView.getGlyphName().equals(fontAwesomeIconView1.getGlyphName())){
                                    return anchorPanesgrid[i-2][j-2];
                                }
                            }
                        }if (j == 0) {
                            if (!(anchorPanesgrid[i][j + 1].getChildren().isEmpty()) && (anchorPanesgrid[i][j + 2].getChildren().isEmpty())) {
                                fontAwesomeIconView1 = (FontAwesomeIconView) anchorPanesgrid[i][j+1].getChildren().get(0);
                                if(fontAwesomeIconView.getGlyphName().equals(fontAwesomeIconView1.getGlyphName())){
                                    return anchorPanesgrid[i][j+2];
                                }
                            }
                        }if (i == 0) {
                            if (!(anchorPanesgrid[i + 1][j].getChildren().isEmpty()) && (anchorPanesgrid[i + 2][j].getChildren().isEmpty())) {
                                fontAwesomeIconView1 = (FontAwesomeIconView) anchorPanesgrid[i+1][j].getChildren().get(0);
                                if(fontAwesomeIconView.getGlyphName().equals(fontAwesomeIconView1.getGlyphName())){
                                    return anchorPanesgrid[i+2][j];
                                }
                            }
                        }
                        if (j == 2) {
                            if (!(anchorPanesgrid[i][j - 1].getChildren().isEmpty()) && (anchorPanesgrid[i][j - 2].getChildren().isEmpty())) {
                                fontAwesomeIconView1 = (FontAwesomeIconView) anchorPanesgrid[i][j-1].getChildren().get(0);
                                if(fontAwesomeIconView.getGlyphName().equals(fontAwesomeIconView1.getGlyphName())){
                                    return anchorPanesgrid[i][j-2];
                                }
                            }
                        }if (i == 2) {
                            if (!(anchorPanesgrid[i - 1][j].getChildren().isEmpty()) && (anchorPanesgrid[i - 2][j].getChildren().isEmpty())) {
                                fontAwesomeIconView1 = (FontAwesomeIconView) anchorPanesgrid[i-1][j].getChildren().get(0);
                                if(fontAwesomeIconView.getGlyphName().equals(fontAwesomeIconView1.getGlyphName())){
                                    return anchorPanesgrid[i-2][j];
                                }
                            }
                        }
                    }
                    else {
                        if (i == 0 && j == 0) {
                            if (!(anchorPanesgrid[i + 1][j + 1].getChildren().isEmpty()) && (anchorPanesgrid[i + 2][j + 2].getChildren().isEmpty())) {
                                fontAwesomeIconView1 = (FontAwesomeIconView) anchorPanesgrid[i + 1][j + 1].getChildren().get(0);
                                if (fontAwesomeIconView.getGlyphName().equals(fontAwesomeIconView1.getGlyphName())) {
                                    return anchorPanesgrid[i + 2][j + 2];
                                }
                            }
                        }
                        if (i == 2 && j == 0) {
                            if (!(anchorPanesgrid[i - 1][j + 1].getChildren().isEmpty()) && (anchorPanesgrid[i - 2][j + 2].getChildren().isEmpty())) {
                                fontAwesomeIconView1 = (FontAwesomeIconView) anchorPanesgrid[i - 1][j + 1].getChildren().get(0);
                                if (fontAwesomeIconView.getGlyphName().equals(fontAwesomeIconView1.getGlyphName())) {
                                    return anchorPanesgrid[i - 2][j + 2];
                                }
                            }
                        }if (i == 0 && j == 2) {
                            if (!(anchorPanesgrid[i + 1][j - 1].getChildren().isEmpty()) && (anchorPanesgrid[i + 2][j - 2].getChildren().isEmpty())) {
                                fontAwesomeIconView1 = (FontAwesomeIconView) anchorPanesgrid[i + 1][j - 1].getChildren().get(0);
                                if (fontAwesomeIconView.getGlyphName().equals(fontAwesomeIconView1.getGlyphName())) {
                                    return anchorPanesgrid[i + 2][j - 2];
                                }
                            }
                        }if (i == 2 && j == 2) {
                            if (!(anchorPanesgrid[i - 1][j - 1].getChildren().isEmpty()) && (anchorPanesgrid[i - 2][j - 2].getChildren().isEmpty())) {
                                fontAwesomeIconView1 = (FontAwesomeIconView) anchorPanesgrid[i-1][j-1].getChildren().get(0);
                                if(fontAwesomeIconView.getGlyphName().equals(fontAwesomeIconView1.getGlyphName())){
                                    return anchorPanesgrid[i-2][j-2];
                                }
                            }
                        }if (j == 0) {
                            if (!(anchorPanesgrid[i][j + 1].getChildren().isEmpty()) && (anchorPanesgrid[i][j + 2].getChildren().isEmpty())) {
                                fontAwesomeIconView1 = (FontAwesomeIconView) anchorPanesgrid[i][j + 1].getChildren().get(0);
                                if (fontAwesomeIconView.getGlyphName().equals(fontAwesomeIconView1.getGlyphName())) {
                                    return anchorPanesgrid[i][j + 2];
                                }
                            }
                        }
                        if (i == 0) {
                            if (!(anchorPanesgrid[i + 1][j].getChildren().isEmpty()) && (anchorPanesgrid[i + 2][j].getChildren().isEmpty())) {
                                fontAwesomeIconView1 = (FontAwesomeIconView) anchorPanesgrid[i + 1][j].getChildren().get(0);
                                if (fontAwesomeIconView.getGlyphName().equals(fontAwesomeIconView1.getGlyphName())) {
                                    return anchorPanesgrid[i + 2][j];
                                }
                            }
                        }if (j == 2) {
                            if (!(anchorPanesgrid[i][j - 1].getChildren().isEmpty()) && (anchorPanesgrid[i][j - 2].getChildren().isEmpty())) {
                                fontAwesomeIconView1 = (FontAwesomeIconView) anchorPanesgrid[i][j - 1].getChildren().get(0);
                                if (fontAwesomeIconView.getGlyphName().equals(fontAwesomeIconView1.getGlyphName())) {
                                    return anchorPanesgrid[i][j - 2];
                                }
                            }
                        }
                        if (i == 2) {
                            if (!(anchorPanesgrid[i - 1][j].getChildren().isEmpty()) && (anchorPanesgrid[i - 2][j].getChildren().isEmpty())) {
                                fontAwesomeIconView1 = (FontAwesomeIconView) anchorPanesgrid[i - 1][j].getChildren().get(0);
                                if (fontAwesomeIconView.getGlyphName().equals(fontAwesomeIconView1.getGlyphName())) {
                                    return anchorPanesgrid[i - 2][j];
                                }
                            }
                        }
                    }
                    if(fontAwesomeIconView.getGlyphName().equals(Bot)){
                        if (i == 0 && j == 0) {
                            if ((anchorPanesgrid[i + 1][j + 1].getChildren().isEmpty()) && !(anchorPanesgrid[i + 2][j + 2].getChildren().isEmpty())) {
                                fontAwesomeIconView1 = (FontAwesomeIconView) anchorPanesgrid[i + 2][j + 2].getChildren().get(0);
                                if(fontAwesomeIconView.getGlyphName().equals(fontAwesomeIconView1.getGlyphName())){
                                    return anchorPanesgrid[i + 1][j + 1];
                                }
                            }
                        }if (i == 2 && j == 0) {
                            if ((anchorPanesgrid[i - 1][j + 1].getChildren().isEmpty()) && !(anchorPanesgrid[i - 2][j + 2].getChildren().isEmpty())) {
                                fontAwesomeIconView1 = (FontAwesomeIconView) anchorPanesgrid[i - 2][j + 2].getChildren().get(0);
                                if(fontAwesomeIconView.getGlyphName().equals(fontAwesomeIconView1.getGlyphName())){
                                    return anchorPanesgrid[i - 1][j + 1];
                                }
                            }
                        }if (i == 0 && j == 2) {
                            if ((anchorPanesgrid[i + 1][j - 1].getChildren().isEmpty()) && !(anchorPanesgrid[i + 2][j - 2].getChildren().isEmpty())) {
                                fontAwesomeIconView1 = (FontAwesomeIconView) anchorPanesgrid[i + 2][j - 2].getChildren().get(0);
                                if(fontAwesomeIconView.getGlyphName().equals(fontAwesomeIconView1.getGlyphName())){
                                    return anchorPanesgrid[i + 1][j - 1];
                                }
                            }
                        }if (i == 2 && j == 2) {
                            if ((anchorPanesgrid[i - 1][j - 1].getChildren().isEmpty()) && !(anchorPanesgrid[i - 2][j - 2].getChildren().isEmpty())) {
                                fontAwesomeIconView1 = (FontAwesomeIconView) anchorPanesgrid[i - 2][j - 2].getChildren().get(0);
                                if(fontAwesomeIconView.getGlyphName().equals(fontAwesomeIconView1.getGlyphName())){
                                    return anchorPanesgrid[i - 1][j - 1];
                                }
                            }
                        }if (j == 0) {
                            if ((anchorPanesgrid[i][j + 1].getChildren().isEmpty()) && !(anchorPanesgrid[i][j + 2].getChildren().isEmpty())) {
                                fontAwesomeIconView1 = (FontAwesomeIconView) anchorPanesgrid[i][j + 2].getChildren().get(0);
                                if(fontAwesomeIconView.getGlyphName().equals(fontAwesomeIconView1.getGlyphName())){
                                    return anchorPanesgrid[i][j + 1];
                                }
                            }
                        }if (i == 0) {
                            if ((anchorPanesgrid[i + 1][j].getChildren().isEmpty()) && !(anchorPanesgrid[i + 2][j].getChildren().isEmpty())) {
                                fontAwesomeIconView1 = (FontAwesomeIconView) anchorPanesgrid[i + 2][j].getChildren().get(0);
                                if(fontAwesomeIconView.getGlyphName().equals(fontAwesomeIconView1.getGlyphName())){
                                    return anchorPanesgrid[i + 1][j];
                                }
                            }
                        }if (j == 2) {
                            if ((anchorPanesgrid[i][j - 1].getChildren().isEmpty()) && !(anchorPanesgrid[i][j - 2].getChildren().isEmpty())) {
                                fontAwesomeIconView1 = (FontAwesomeIconView) anchorPanesgrid[i][j - 2].getChildren().get(0);
                                if(fontAwesomeIconView.getGlyphName().equals(fontAwesomeIconView1.getGlyphName())){
                                    return anchorPanesgrid[i][j - 1];
                                }
                            }
                        }if (i == 2) {
                            if ((anchorPanesgrid[i - 1][j].getChildren().isEmpty()) && !(anchorPanesgrid[i - 2][j].getChildren().isEmpty())) {
                                fontAwesomeIconView1 = (FontAwesomeIconView) anchorPanesgrid[i - 2][j].getChildren().get(0);
                                if(fontAwesomeIconView.getGlyphName().equals(fontAwesomeIconView1.getGlyphName())){
                                    return anchorPanesgrid[i - 1][j];
                                }
                            }
                        }
                    }
                    else {
                        if (i == 0 && j == 0) {
                            if ((anchorPanesgrid[i + 1][j + 1].getChildren().isEmpty()) && !(anchorPanesgrid[i + 2][j + 2].getChildren().isEmpty())) {
                                fontAwesomeIconView1 = (FontAwesomeIconView) anchorPanesgrid[i + 2][j + 2].getChildren().get(0);
                                if (fontAwesomeIconView.getGlyphName().equals(fontAwesomeIconView1.getGlyphName())) {
                                    return anchorPanesgrid[i + 1][j + 1];
                                }
                            }
                        }
                        if (i == 2 && j == 0) {
                            if ((anchorPanesgrid[i - 1][j + 1].getChildren().isEmpty()) && !(anchorPanesgrid[i - 2][j + 2].getChildren().isEmpty())) {
                                fontAwesomeIconView1 = (FontAwesomeIconView) anchorPanesgrid[i - 2][j + 2].getChildren().get(0);
                                if (fontAwesomeIconView.getGlyphName().equals(fontAwesomeIconView1.getGlyphName())) {
                                    return anchorPanesgrid[i - 1][j + 1];
                                }
                            }
                        }
                        if (i == 0 && j == 2) {
                            if ((anchorPanesgrid[i + 1][j - 1].getChildren().isEmpty()) && !(anchorPanesgrid[i + 2][j - 2].getChildren().isEmpty())) {
                                fontAwesomeIconView1 = (FontAwesomeIconView) anchorPanesgrid[i + 2][j - 2].getChildren().get(0);
                                if (fontAwesomeIconView.getGlyphName().equals(fontAwesomeIconView1.getGlyphName())) {
                                    return anchorPanesgrid[i + 1][j - 1];
                                }
                            }
                        }if (i == 2 && j == 2) {
                            if ((anchorPanesgrid[i - 1][j - 1].getChildren().isEmpty()) && !(anchorPanesgrid[i - 2][j - 2].getChildren().isEmpty())) {
                                fontAwesomeIconView1 = (FontAwesomeIconView) anchorPanesgrid[i - 2][j - 2].getChildren().get(0);
                                if(fontAwesomeIconView.getGlyphName().equals(fontAwesomeIconView1.getGlyphName())){
                                    return anchorPanesgrid[i - 1][j - 1];
                                }
                            }
                        }if (j == 0) {
                            if ((anchorPanesgrid[i][j + 1].getChildren().isEmpty()) && !(anchorPanesgrid[i][j + 2].getChildren().isEmpty())) {
                                fontAwesomeIconView1 = (FontAwesomeIconView) anchorPanesgrid[i][j + 2].getChildren().get(0);
                                if (fontAwesomeIconView.getGlyphName().equals(fontAwesomeIconView1.getGlyphName())) {
                                    return anchorPanesgrid[i][j + 1];
                                }
                            }
                        }
                        if (i == 0) {
                            if ((anchorPanesgrid[i + 1][j].getChildren().isEmpty()) && !(anchorPanesgrid[i + 2][j].getChildren().isEmpty())) {
                                fontAwesomeIconView1 = (FontAwesomeIconView) anchorPanesgrid[i + 2][j].getChildren().get(0);
                                if (fontAwesomeIconView.getGlyphName().equals(fontAwesomeIconView1.getGlyphName())) {
                                    return anchorPanesgrid[i + 1][j];
                                }
                            }
                        }if (j == 2) {
                            if ((anchorPanesgrid[i][j - 1].getChildren().isEmpty()) && !(anchorPanesgrid[i][j - 2].getChildren().isEmpty())) {
                                fontAwesomeIconView1 = (FontAwesomeIconView) anchorPanesgrid[i][j - 2].getChildren().get(0);
                                if (fontAwesomeIconView.getGlyphName().equals(fontAwesomeIconView1.getGlyphName())) {
                                    return anchorPanesgrid[i][j - 1];
                                }
                            }
                        }
                        if (i == 2) {
                            if ((anchorPanesgrid[i - 1][j].getChildren().isEmpty()) && !(anchorPanesgrid[i - 2][j].getChildren().isEmpty())) {
                                fontAwesomeIconView1 = (FontAwesomeIconView) anchorPanesgrid[i - 2][j].getChildren().get(0);
                                if (fontAwesomeIconView.getGlyphName().equals(fontAwesomeIconView1.getGlyphName())) {
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

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {


    }

    public void setPlayerColor(Color color) {
        if (color.equals(Color.BLUE)) {
            playercolor = color;
            botcolor = Color.RED;
        }else{
            playercolor = color;
            botcolor = Color.BLUE;
        }
    }
}
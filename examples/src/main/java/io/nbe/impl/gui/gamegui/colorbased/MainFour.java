package io.nbe.impl.gui.gamegui.colorbased;

import io.nbe.squarly.model.Color;
import io.nbe.squarly.view.GameScreen;
import io.nbe.squarly.view.GameSquareClicked;
import io.nbe.squarly.view.MapPrinter;
import io.nbe.squarly.model.Cord;
import io.nbe.squarly.model.GameGenerator;

import java.awt.event.MouseEvent;

/**
 * @author Nicolas Beaussart
 * @since 27/10/16
 */
public class MainFour {
    private final static Color COLOR_WALL=new Color(0,0,0);
    private final static Color COLOR_ROOM=new Color(255,255,255);
    private final static Color COLOR_DOOR=new Color(255,0,0);
    public static void generate(GameMap gm){
        gm.getMapData().clear();
        for (int y = 0; y < gm.sizeY(); y++) {
            for (int x = 0; x < gm.sizeX(); x++) {
                gm.add(new GameSquare(COLOR_WALL, Cord.get(x,y), gm));
            }
        }
        System.out.println("Starging");
        long start = System.currentTimeMillis();
        new GameGenerator<>(gm).useDungeonGenerator().generate();
        long end = System.currentTimeMillis();
        System.out.println("Done in " + (end - start) + "ms");
        gm.getMapData().values().forEach(gameSquare -> {
            switch (gameSquare.getState()){
                case DOOR:
                    gameSquare.setColorWithoutUpdate(COLOR_DOOR);
                    break;
                case ROOM:
                    gameSquare.setColorWithoutUpdate(COLOR_ROOM);
                    break;
                case WALL:
                    gameSquare.setColorWithoutUpdate(COLOR_WALL);
                    break;
            }
        });
        System.out.println("Done Coloring");
        gm.setChanged(null);
    }


    public static void main(String... args){
        GameMap gm = new GameMap(80, 80, 20, 20);
        generate(gm);
        MapPrinter<GameSquare> mapPrinter = new MapPrinter<>(gm);
        GameScreen.createGameScreen("Hello world", mapPrinter);
        mapPrinter.addGameClicked(new GameSquareClicked<GameSquare>() {
            @Override
            public void mouseClicked(GameSquare square, MouseEvent e) {
                //System.out.println(square);
                generate(gm);
            }
        });
    }
}

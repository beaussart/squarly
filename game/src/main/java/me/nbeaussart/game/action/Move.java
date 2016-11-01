package me.nbeaussart.game.action;

import com.google.common.base.Preconditions;
import me.nbeaussart.engine.model.Cord;
import me.nbeaussart.engine.model.Direction;
import me.nbeaussart.game.map.GameMap;
import me.nbeaussart.game.entity.Entity;

/**
 * Created by beaussan on 31/10/16.
 */
public class Move extends Action {
    private final Direction direction;

    public Move(Entity source, Direction direction) {
        super(source);
        this.direction = direction;
    }

    @Override
    public void doStuff() {
        Preconditions.checkState(getSource().isALive(), "Entity %s is dead then cannot move !", getSource());
        GameMap gameMap = getSource().getGameSquare().getGameMap();
        Cord newCord = getSource().getGameSquare().getCord().add(direction.getCords());
        gameMap.getFromCords(newCord).ifPresent(gameSquare -> {
            if (getSource().canPassOn(gameSquare)){
                getSource().setGameSquare(gameSquare);
            }
        });
    }
}

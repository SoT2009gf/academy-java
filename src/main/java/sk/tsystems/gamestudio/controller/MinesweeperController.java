package sk.tsystems.gamestudio.controller;

import java.util.Formatter;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.context.WebApplicationContext;

import sk.tsystems.gamestudio.game.minesweeper.core.Clue;
import sk.tsystems.gamestudio.game.minesweeper.core.Field;
import sk.tsystems.gamestudio.game.minesweeper.core.Mine;
import sk.tsystems.gamestudio.game.minesweeper.core.Tile;

@Controller
@Scope(WebApplicationContext.SCOPE_SESSION)
@RequestMapping("/minesweeper")
public class MinesweeperController {

    private Field field;

    @RequestMapping
    public String index() {
        field = new Field(9, 9, 10);
        return "minesweeper";
    }

    @RequestMapping("/open")
    public String open(int row, int column) {
        field.openTile(row, column);
        return "minesweeper";
    }

    @RequestMapping("/mark")
    public String mark(int row, int column) {
        field.markTile(row, column);
        return "minesweeper";
    }

    public String getHtmlField() {
        @SuppressWarnings("resource")
        Formatter formatter = new Formatter();
        formatter.format("<div>\n");
        for (int row = 0; row < field.getRowCount(); row++) {
            formatter.format("<div class='row'>\n");
            for (int column = 0; column < field.getColumnCount(); column++) {
                formatter.format("<div class='cell'>\n");
                Tile tile = field.getTile(row, column);
                if (tile.getState() == Tile.State.CLOSED) {
                    formatter.format(
                            "<a href='/minesweeper/open?row=%d&column=%d' class='tile'><img src='/img/minesweeper/closedTile.png' alt='Closed minefield tile.'/></a>",
                            row, column);
                } else if (tile.getState() == Tile.State.MARKED) {
                    formatter.format(
                            "<a href='/minesweeper/mark?row=%d&column=%d' class='tile'><img src='/img/minesweeper/markedTile.png' alt='Marked minefield tile.'/></a>",
                            row, column);
                } else {
                    if (tile instanceof Mine) {
                        formatter.format("<img class='openedTile' src='/img/minesweeper/mine.png' alt='Exploded mine.'/>");
                    } else if (tile instanceof Clue) {
                        formatter.format("<img class='openedTile' src='/img/minesweeper/%d.png' alt='Open minefield tile.'/>",
                                ((Clue) tile).getValue());
                    }
                }
                formatter.format("</div>\n");
            }
            formatter.format("</div>\n");
        }
        formatter.format("</div>\n");
        return formatter.toString();
    }
}

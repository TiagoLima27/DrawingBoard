package io.codeforall.fanstatics;

import org.academiadecodigo.simplegraphics.graphics.Color;
import org.academiadecodigo.simplegraphics.graphics.Rectangle;
import org.academiadecodigo.simplegraphics.keyboard.Keyboard;
import org.academiadecodigo.simplegraphics.keyboard.KeyboardEvent;
import org.academiadecodigo.simplegraphics.keyboard.KeyboardEventType;
import org.academiadecodigo.simplegraphics.keyboard.KeyboardHandler;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class DrawingBoard implements KeyboardHandler {

    private Cell[][] cells;
    int cellSize = 20;
    int PADDING = 10;
    static Rectangle player;
    int playerX = 0;
    int playerY = 0;


    List<int[]> paintedCells = new ArrayList<>();
    private static final String SAVE_FILE = "paintedCells.txt";

    public DrawingBoard() {
        drawGrid();
        drawPlayer();
        initKeyboard();
    }

    public void drawGrid() {
        cells = new Cell[50][50];
        for (int i = 0; i < 50; i++) {
            for (int j = 0; j < 50; j++) {
                Cell cell = new Cell(j * cellSize + PADDING, i * cellSize + PADDING, cellSize, cellSize);
                cell.draw();
                cells[i][j] = cell;
            }
        }
    }

    public void drawPlayer() {
        player = new Rectangle(PADDING, PADDING, cellSize, cellSize);
        player.setColor(Color.GREEN);
        player.fill();
    }

    private void initKeyboard() {
        Keyboard keyboard = new Keyboard(this);

        KeyboardEvent upEvent = new KeyboardEvent();
        upEvent.setKey(KeyboardEvent.KEY_UP);
        upEvent.setKeyboardEventType(KeyboardEventType.KEY_PRESSED);
        keyboard.addEventListener(upEvent);

        KeyboardEvent downEvent = new KeyboardEvent();
        downEvent.setKey(KeyboardEvent.KEY_DOWN);
        downEvent.setKeyboardEventType(KeyboardEventType.KEY_PRESSED);
        keyboard.addEventListener(downEvent);

        KeyboardEvent leftEvent = new KeyboardEvent();
        leftEvent.setKey(KeyboardEvent.KEY_LEFT);
        leftEvent.setKeyboardEventType(KeyboardEventType.KEY_PRESSED);
        keyboard.addEventListener(leftEvent);

        KeyboardEvent rightEvent = new KeyboardEvent();
        rightEvent.setKey(KeyboardEvent.KEY_RIGHT);
        rightEvent.setKeyboardEventType(KeyboardEventType.KEY_PRESSED);
        keyboard.addEventListener(rightEvent);

        KeyboardEvent spaceEvent = new KeyboardEvent();
        spaceEvent.setKey(KeyboardEvent.KEY_SPACE);
        spaceEvent.setKeyboardEventType(KeyboardEventType.KEY_PRESSED);
        keyboard.addEventListener(spaceEvent);

        KeyboardEvent cEvent = new KeyboardEvent();
        cEvent.setKey(KeyboardEvent.KEY_C);
        cEvent.setKeyboardEventType(KeyboardEventType.KEY_PRESSED);
        keyboard.addEventListener(cEvent);

        KeyboardEvent lEvent = new KeyboardEvent();
        lEvent.setKey(KeyboardEvent.KEY_L);
        lEvent.setKeyboardEventType(KeyboardEventType.KEY_PRESSED);
        keyboard.addEventListener(lEvent);

        KeyboardEvent sEvent = new KeyboardEvent();
        sEvent.setKey(KeyboardEvent.KEY_S);
        sEvent.setKeyboardEventType(KeyboardEventType.KEY_PRESSED);
        keyboard.addEventListener(sEvent);
    }

    @Override
    public void keyPressed(KeyboardEvent e) {
        int key = e.getKey();

        switch (key) {
            case KeyboardEvent.KEY_UP -> {
                if (playerY > 0) {
                    player.translate(0, -cellSize);
                    playerY--;
                }
            }
            case KeyboardEvent.KEY_DOWN -> {
                if (playerY < 49) {
                    player.translate(0, cellSize);
                    playerY++;
                }
            }
            case KeyboardEvent.KEY_LEFT -> {
                if (playerX > 0) {
                    player.translate(-cellSize, 0);
                    playerX--;
                }
            }
            case KeyboardEvent.KEY_RIGHT -> {
                if (playerX < 49) {
                    player.translate(cellSize, 0);
                    playerX++;
                }
            }
            case KeyboardEvent.KEY_SPACE -> Paint();
            case KeyboardEvent.KEY_S -> savePaintedCells();
            case KeyboardEvent.KEY_L -> loadPaintedCells();
            case KeyboardEvent.KEY_C -> clearGrid();
        }
    }

    @Override
    public void keyReleased(KeyboardEvent keyboardEvent) {

    }


    private void Paint() {
        if (cells[playerY][playerX].isPainted) {
            cells[playerY][playerX].delete();
            cells[playerY][playerX].isPainted = false;
            removePaintedCell(playerY, playerX);
        } else {
            cells[playerY][playerX].paint();
            cells[playerY][playerX].isPainted = true;
            paintedCells.add(new int[]{playerY, playerX});
        }
    }

    private void removePaintedCell(int y, int x) {
        paintedCells.removeIf(cell -> cell[0] == y && cell[1] == x);
    }


    private void savePaintedCells() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(SAVE_FILE))) {
            for (int[] cell : paintedCells) {
                writer.write(cell[0] + "," + cell[1]);
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    private void loadPaintedCells() {
        try (BufferedReader reader = new BufferedReader(new FileReader(SAVE_FILE))) {
            clearGrid();
            String line;
            while ((line = reader.readLine()) != null) {
                String[] coords = line.split(",");
                int y = Integer.parseInt(coords[0]);
                int x = Integer.parseInt(coords[1]);
                cells[y][x].paint();
                cells[y][x].isPainted = true;
                paintedCells.add(new int[]{y, x});
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    private void clearGrid() {
        for (int i = 0; i < 50; i++) {
            for (int j = 0; j < 50; j++) {
                if (cells[i][j].isPainted) {
                    cells[i][j].delete();
                    cells[i][j].isPainted = false;
                }
            }
        }
        paintedCells.clear();
    }
}

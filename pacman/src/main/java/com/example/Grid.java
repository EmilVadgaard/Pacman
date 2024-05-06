package com.example;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Grid {
    private Entity[][] map;

    public Grid(File blueprint) {
        try {
            this.map = build(blueprint);
        }
        catch(FileNotFoundException ex) {
            System.out.println("No such file: " + map.toString());
            ex.printStackTrace();
        }
    }

    private Entity[][] build(File blueprint) throws FileNotFoundException{
        char[][] charMatrix = fileToCharMatrix(blueprint);
        Entity[][] map = new Entity[charMatrix.length][charMatrix[0].length];
        
        for (int y = 0; y < charMatrix.length; y++) {
            for (int x = 0; x < charMatrix[0].length; x++) {
                switch(charMatrix[y][x]) {
                    case 'W':
                        Entity wallType = calculateWallType(x, y, charMatrix);
                        map[y][x] = Entity.wall;
                        break;
                    case 'E':
                        map[y][x] = Entity.empty;
                        break;
                    case 'P':
                        map[y][x] = Entity.bigPellet;
                        break;
                    case 'p':
                        map[y][x] = Entity.pellet;
                        break;
                    default:
                        break;
                }
            }
        }
        return map;
    }

    private int[] dimensions(File blueprint) throws FileNotFoundException {
        Scanner sc = new Scanner(blueprint);

        // Find the collums and rows of the map grid
        int collums = 0;
        int rows = 1;
        String line = sc.nextLine();
        for (int i = 0; i < line.length(); i++) {
            collums++;
        }
        while (sc.hasNextLine()) {
            sc.nextLine();
            rows++;
        }

        int[] result = {rows,collums};
        sc.close();
        return result;
    }

    private char[][] fileToCharMatrix(File blueprint) throws FileNotFoundException{
        int[] dimensions = dimensions(blueprint);
        Scanner sc = new Scanner(blueprint);

        // Convert text file to a matrix of characters

        char[][] charMap = new char[dimensions[0]][dimensions[1]];
        int row = 0;

        while (sc.hasNextLine()) {
            String line = sc.nextLine();
            for (int col = 0; col < line.length(); col++) {
                charMap[row][col] = line.charAt(col);
            }
            row++;
        }
        sc.close();
        return charMap;
    }

    private Entity calculateWallType(int x, int y, char[][] charMatrix) {
        int wallType = 0;
        int multiplier = 1;
        System.out.println(charMatrix.length + " - y length, " + charMatrix[0].length + "- x length");
        for (int i = -1; i <= 1; i++) {
            for (int j = -1; j <= 1; j++) {
                if (y+i >= 0 && y+i < charMatrix.length && x+j >= 0 && x+j < charMatrix[0].length) {
                    if (charMatrix[y+i][x+j] == 'W') {
                        wallType += multiplier;
                    }
                    if (charMatrix[y+i][x+j] == 'O') {
                        // to-do: Add outside spaces to map.txt
                    }
                }
                multiplier *= 2;
            }
        }
        switch(wallType) {
            default:
                return Entity.wall;
        }
    }

    public Entity[][] getMap() {
        return this.map;
    }

    public void setEntity(int x, int y, Entity entity) {
        map[y][x] = Entity.empty;
    }

    public Entity getEntity(int x, int y) {
        return map[y][x];
    }

    public int getLengthX() {
        return map[0].length;
    }

    public int getLengthY() {
        return map.length;
    }
}
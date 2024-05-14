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
            System.out.println("This file: " + map.toString() + " does not exist.");
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
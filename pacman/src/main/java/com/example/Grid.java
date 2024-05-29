package com.example;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 * A 2D array representation of the Pac-man world. 
 */
public class Grid {
    private Entity[][] map;

    /**
     * Creates an instance of a Grid. Only works with a valid blueprint.
     * The blueprint must only contain any of the following characters:
     * "D - door.
     * W - wall.
     * E - empty.
     * p - pellet.
     * P - power pellet."
     * @param blueprint a .txt file of how the map looks.
     */
    public Grid(File blueprint) {
        try {
            this.map = build(blueprint);
        }
        catch(FileNotFoundException ex) {
            System.out.println("This file: " + map.toString() + " does not exist.");
            ex.printStackTrace();
        }
    }

    /**
     * Builds up the 2D-array according to the given file.
     * @param blueprint
     * @return 2D array of entities
     * @throws FileNotFoundException
     */
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
                    case 'D':
                        map[y][x] = Entity.door;
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

    /*
     * Find the collums and rows of the map grid.
     */
    private int[] dimensions(File blueprint) throws FileNotFoundException {
        Scanner sc = new Scanner(blueprint);

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

    /*
     * Convert text file to a matrix of characters
     */
    private char[][] fileToCharMatrix(File blueprint) throws FileNotFoundException{
        int[] dimensions = dimensions(blueprint);
        Scanner sc = new Scanner(blueprint);

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

    /**
     * Sets an empty entity inside the 2D array, 
     * according to specified values of x and y.
     * @param x Index of outter array
     * @param y Index of inner array
     * @param entity A value from the enum 'Entity'
     */
    public void setEntity(int x, int y, Entity entity) {
        map[y][x] = entity;
    }

    /**
     * @param x Index of outter array
     * @param y Index of inner array
     * @return The entity located on the 2D array, 
     * according to the specified values of x and y.
     */
    public Entity getEntity(int x, int y) {
        return map[y][x];
    }

    /**
     * @return The length in x-direction of the 2D array. 
     */
    public int getLengthX() {
        return map[0].length;
    }

    /**
     * @return The length in y-direction of the 2D array. 
     */
    public int getLengthY() {
        return map.length;
    }
}
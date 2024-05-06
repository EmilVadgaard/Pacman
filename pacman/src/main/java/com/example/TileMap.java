package com.example;

public class TileMap {
    boolean[] solid = {true,true,true,true};
    boolean[] horizontal = {false,false,true,true};
    boolean[] vertical = {true,true,false,false};
    boolean[] corner1 = {false,true,true,false};
    boolean[] corner2 = {false,true,false,true};
    boolean[] corner3 = {true,false,false,true};
    boolean[] corner4 = {true,false,true,false};

    boolean[][] options = {corner1, corner2, corner3, corner4, horizontal, vertical, solid};

    Entity[] type = {Entity.corner1, Entity.corner2, Entity.corner3, Entity.corner4, 
                     Entity.horizontal, Entity.vertical, Entity.solid};

    
    public Entity[][] tileMap(Entity[][] grid){
        Entity[][] newGrid = grid;

        for (int y = 0; y < grid.length; y++){
            for (int x = 0; x < grid[0].length; x++){
                boolean[] walls = checkWalls(grid, x, y);

                for (int i = 0; i < options.length; i++){
                    if(walls == options[i]){
                        newGrid[x][y] = type[i];
                    }
                }


            }
        }

        return newGrid;
    }

    public boolean[] checkWalls(Entity[][] grid, int x, int y){
        boolean[] walls = new boolean[4];

        if(0 <= x && x < grid[0].length && 0 <= y && y < grid.length){
            if (x-1 != -1){
                if (grid[x-1][y] == Entity.wall){
                    walls[0] = true;
                }
            }
            if (x+1 != grid[0].length){
                if (grid[x+1][y] == Entity.wall){
                    walls[1] = true;
                }
            }
            if (y+1 != grid.length){
                if (grid[x][y+1] == Entity.wall){
                    walls[2] = true;
                }
            }
            if (y-1 != -1){
                if (grid[x][y-1] == Entity.wall){
                    walls[3] = true;
                }
            }
        }

        return walls;
    }

    //public void
}

package core;

import tileengine.TETile;
import tileengine.Tileset;

import java.util.*;

public class World {

    // build your own world!
    TETile[][] world;
    int HEIGHT = 50;
    int WIDTH = 100;
    int LIMIT = 4;
    Random rand;
    Set<Room> roomSet;
    public World(long seed){
        rand = new Random(seed);
        world = new TETile[WIDTH][HEIGHT];
        roomSet = new HashSet<>();
        genWord();

    }

    private class Room{
        int x;
        int y;
        int height;
        int width;
        public Room(int x, int y, int height, int width){
            this.x = x;
            this.y = y;
            this.height = height;
            this.width = width;
        }
    }

    // Check whether there is another room or a path in the range of the given room
    public boolean RoomExists(Room room){
        for(int i = room.x; i < room.x + room.width && i < WIDTH - 1; i++){
            for(int j = room.y; j < room.y + room.height && j < HEIGHT - 1; j++){
                if(world[i][j] != Tileset.NOTHING){
                    return true;
                }
            }
        }
        return false;
    }

    //Check whether the given room is touching or occupying over the boundary
    public boolean BoundaryExist(Room room){
        return room.x + room.width >= WIDTH - 1 || room.y + room.height >= HEIGHT - 1;
    }

    public boolean RoomLegal(Room room){
        return !BoundaryExist(room) && !RoomExists(room);
    }

    public Room genRoom(){
        Room newRoom;
        int tries = 0;
        do{
            if(tries == LIMIT){
                return null;
            }
            tries += 1;
            newRoom = new Room(rand.nextInt(WIDTH - 2) + 1, rand.nextInt(HEIGHT - 2) + 1, rand.nextInt(10) + 2, rand.nextInt(10) + 2);
        }while (!RoomLegal(newRoom));
        roomSet.add(newRoom);
        return newRoom;
    }
    public void drawRoom(Room room){
        for(int i = room.x; i <= Xboundry(room); i++){
            for(int j = room.y; j <= Yboundry(room); j++){
                    world[i][j] = Tileset.FLOOR;
            }
        }
    }

    private int Xboundry(Room room){
        return room.x + room.width - 1;
    }

    private int Yboundry(Room room){
        return room.y + room.height - 1;
    }
    public enum Direction{
        UP,
        DOWN,
        LEFT,
        RIGHT
    }

    // Draw paths to get rooms connected
//    public void connectRoom(Room room){
//        Direction[] dirs = Direction.values();
//        Direction dir = dirs[rand.nextInt(dirs.length)];
//        switch (dir){
//            case UP -> {
//                int y = Yboundry(room) + 1;
//                int x = rand.nextInt(Xboundry(room) + 1);
//                while (!connectFromPoint(x, y, Direction.UP)){
//                    x = rand.nextInt(Xboundry(room) + 1);
//                };
//            }
//            case DOWN -> {
//                int y = room.y - 1;
//                int x = rand.nextInt(Xboundry(room) + 1);
//                while (!connectFromPoint(x, y, Direction.DOWN)){
//                    x = rand.nextInt(Xboundry(room) + 1);
//                };
//            }
//            case LEFT -> {
//                int x = room.x - 1;
//                int y = rand.nextInt(Yboundry(room) + 1);
//                while(!connectFromPoint(x, y, Direction.LEFT)){
//                    y = rand.nextInt(Yboundry(room) + 1);
//                };
//            }
//            case RIGHT -> {
//                int x = Xboundry(room) + 1;
//                int y = rand.nextInt(Yboundry(room) + 1);
//                while (!connectFromPoint(x, y, Direction.RIGHT)){
//                    y = rand.nextInt(Yboundry(room) + 1);
//                };
//            }
//        }
//    }

    public void connectRoom(Room room) {
        List<Direction> dirs = new ArrayList<>(List.of(Direction.values()));
        Collections.shuffle(dirs);
        for (Direction dir : dirs) {
            if (tryConnect(room, dir)) {
                if(rand.nextBoolean())
                    break; // 一旦成功连接且随机选择不继续，就停止
            }
        }
    }

    private boolean tryConnect(Room room, Direction dir) {
        int x = 0, y = 0;
        switch (dir) {
            case UP -> {
                y = Yboundry(room) + 1;
                x = rand.nextInt(room.width) + room.x;
            }
            case DOWN -> {
                y = room.y - 1;
                x = rand.nextInt(room.width) + room.x;
            }
            case LEFT -> {
                x = room.x - 1;
                y = rand.nextInt(room.height) + room.y;
            }
            case RIGHT -> {
                x = Xboundry(room) + 1;
                y = rand.nextInt(room.height) + room.y;
            }
        }
        return connectFromPoint(x, y, dir);
    }


    // Check if there exist a path to another path or room, start zuobiao is outside the room
    public boolean connectFromPoint(int x, int y, Direction dir){
        int i = x;
        int j = y;
        switch (dir){
            case UP -> {
                while ( j < HEIGHT - 1 && world[i][j] == Tileset.NOTHING ){
                    j++;
                }
                if(j == HEIGHT - 1){
                    return false;
                }
                else{
                    // y == start, j == end
                    drawPath(y, x, j, false);
                    return true;
                }
            }
            case DOWN -> {
                while (j > 0 && world[i][j] == Tileset.NOTHING){
                    j--;
                }
                if(j == 0){
                    return false;
                }
                else{
                    // y == start, j == end
                    drawPath(j, x, y, false);
                    return true;
                }
            }
            case LEFT -> {
                while (i > 0 && world[i][j] == Tileset.NOTHING ){
                    i--;
                }
                if(i == 0){
                    return false;
                }
                else{
                    drawPath(i, y, x, true);
                    return true;
                }
            }
            case RIGHT -> {
                while (i < WIDTH - 1 && world[i][j] == Tileset.NOTHING){
                    i++;
                }
                if(i == WIDTH - 1){
                    return false;
                }
                else{
                    drawPath(x, y, i, true);
                    return true;
                }
            }
        }
        return true;
    }

    public void drawPath(int start, int otherStart, int end, boolean isX){
        for (int k = Math.min(start, end); k <= Math.max(start, end); k++) {
            if (isX) {
                world[k][otherStart] = Tileset.FLOOR;
            } else {
                world[otherStart][k] = Tileset.FLOOR;
            }
        }

    }


    public void genWord(){
        fillWord(Tileset.NOTHING);

        Room newRoom = genRoom();
        while (newRoom != null){
            drawRoom(newRoom);
            newRoom = genRoom();
        };

        for(Room room : roomSet){
            connectRoom(room);
        }

        drawWall();
    }

    public TETile[][] getWorld(){
        return world;
    }
    private void fillWord(TETile tile) {
        for (int i = 0; i < world.length; i++) {
            for (int j = 0; j < world[0].length; j++) {
                world[i][j] = tile;
            }
        }
    }

    public void drawWall(){
        for (int i = 0; i < world.length; i++) {
            for (int j = 0; j < world[0].length; j++) {
                if(world[i][j] == Tileset.NOTHING && nearFloor(i, j))
                    world[i][j] = Tileset.WALL;
            }
        }
    }

    // 检查一个空块旁边是否有floor
    private boolean nearFloor(int x, int y) {
        // 上、下、左、右四个方向
        int[][] directions = {
                {0, 1},   // 上
                {0, -1},  // 下
                {-1, 0},  // 左
                {1, 0},    // 右
                {-1, -1},
                {-1, 1},
                {1, 1},
                {1, -1}
        };

        for (int[] dir : directions) {
            int nx = x + dir[0];
            int ny = y + dir[1];

            // 边界检查
            if (nx >= 0 && nx < WIDTH && ny >= 0 && ny < HEIGHT) {
                if (world[nx][ny] == Tileset.FLOOR) {
                    return true;
                }
            }
        }
        return false;
    }

}

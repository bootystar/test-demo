package dataStructure.algorithm;

/**
 * 使用递归找寻迷宫中的路线
 * 二维数组模拟迷宫，
 * 使用0表示未走过，使用1表示不能走，使用2表示走过，使用3表示走过，但走不通
 *
 * @author booty
 * @date 2021/6/24 09:23
 */
public class Maze {
    private String[][] maze;
    private final static String WALL = "田";
    private final static String WAY = "一";
    private final static String OUT = "二";
    private final static String DEAD = "三";

    public Maze(int x, int y) {
        maze = new String[y + 2][x + 2];

        for (int i = 0; i < maze.length; i++) {
            for (int j = 0; j < maze[i].length; j++) {
                maze[i][j] = WAY;
            }
        }

        for (int i = 0; i < maze.length; i++) {
            maze[i][0] = WALL;
            maze[i][x + 1] = WALL;
            for (int j = 0; j < maze[i].length; j++) {
                maze[0][j] = WALL;
                maze[y + 1][j] = WALL;
            }
        }
    }

    public void showMaze() {
        System.out.flush();
        for (int i = 0; i < maze.length; i++) {
            for (int j = 0; j < maze[i].length; j++) {
                System.out.print(maze[i][j] + "  ");
            }
            System.out.println();
        }
        System.out.println("================");
    }

    /**
     * 设置障碍
     *
     * @param x 障碍的x坐标
     * @param y 障碍的y坐标
     * @return 链式调用
     */
    public Maze setUpBarriers(int x, int y) {
        maze[y][x] = WALL;
        return this;
    }

    /**
     * 找寻出路
     *
     * @param currentX 起点x轴
     * @param currentY 起点y轴
     * @param endX     终点x轴
     * @param endY     终点y轴
     * @return 是否走通
     */
    public boolean wayOut(int currentX, int currentY, int endX, int endY) {
        //若目标已经被走过，直接return
        if (maze[endY][endX].equals(OUT)) {
            return true;
        } else {
            //判断当前点是否为未走过的路
            if (maze[currentY][currentX].equals(WAY)) {
                //将当前点标记已走过
                maze[currentY][currentX] = OUT;


                if (wayOut(currentX + 1, currentY, endX, endY)) {
                    //往右能走通
                    return true;
                } else if (wayOut(currentX, currentY + 1, endX, endY)) {
                    //往下能走通
                    return true;
                } else if (wayOut(currentX - 1, currentY, endX, endY)) {
                    //往左能走通
                    return true;
                } else if (wayOut(currentX, currentY - 1, endX, endY)) {
                    //往上能走通
                    return true;
                } else {
                    //其上下左右均走不通，将该点标记为走不通
                    maze[currentY][currentX] = DEAD;
                    return false;
                }
            } else {
                //当前点不为未走过的路时，当前点要么为墙，要么为已走过的路（未走通和已走通），此时直接返回
                return false;
            }
        }

    }





    private void check(int x, int y) {
        if (maze[y][x].equals(WALL)) throw new RuntimeException("位置已被障碍占用");
    }


    public static void main(String[] args) {
        Maze maze = new Maze(7, 7)
                //第一堵墙
                .setUpBarriers(1, 2)
                .setUpBarriers(2, 2)
                .setUpBarriers(3, 2)
                .setUpBarriers(4, 2)
                .setUpBarriers(5, 2)
                .setUpBarriers(6, 2)
                //第二堵墙
                .setUpBarriers(2, 4)
                .setUpBarriers(3, 4)
                .setUpBarriers(4, 4)
                .setUpBarriers(5, 4)
                .setUpBarriers(6, 4)
                .setUpBarriers(7, 5)
                //第三堵墙
                .setUpBarriers(2, 6)
                .setUpBarriers(3, 6)
                .setUpBarriers(4, 6)
                .setUpBarriers(5, 6)
                .setUpBarriers(6, 6)

                .setUpBarriers(0, 0);

        maze.showMaze();
        maze.wayOut( 1, 1, 5, 5);

        maze.showMaze();
    }

}

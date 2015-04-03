
import java.util.*;

/**
 * ??ù??????????
 * ???????????????????У???????????????????????????д??????ν????????????
 * 1 . ???????????????к??????
 * 2 . ??????е????????
 * 3 . ?ж????????????????????????????????????????????????????????
 * 4 . ???????????????????????????ж?????????????????????г???????????????????????
 * 5 . ????????????
 */
public class Game {
    public static void main(String[] args) {
        String test = "三等份";
        System.out.println(test);
        Set<Mask> set = new HashSet<>();
        Queue<State> queue = new LinkedList<>();
        State init = new State();
        Block[] blocks = init.getBlocks();
        /*
            ?????
           4   2   2   6
           4   2   2   6
           7   6   6   9
           7   9   10   9
          11   0   0   12
         */
        /*blocks[0] = new Block(Shape.VERTICAL,0,0);
        blocks[1] = new Block(Shape.SQUARE,1,0);
        blocks[2] = new Block(Shape.VERTICAL,3,0);
        blocks[3] = new Block(Shape.VERTICAL,0,2);
        blocks[4] = new Block(Shape.HORIZON,1,2);
        blocks[5] = new Block(Shape.VERTICAL,3,2);
        blocks[6] = new Block(Shape.SINGLE,1,3);
        blocks[7] = new Block(Shape.SINGLE,2,3);
        blocks[8] = new Block(Shape.SINGLE,0,4);
        blocks[9] = new Block(Shape.SINGLE,3,4);*/
        /*
          4   2   2   6
          4   2   2   6
          7   0   0   9
          7   6   6   9
         9   10   11   12
         */
        blocks[0] = new Block(Shape.VERTICAL,0,0);
        blocks[1] = new Block(Shape.SQUARE,1,0);
        blocks[2] = new Block(Shape.VERTICAL,3,0);
        blocks[3] = new Block(Shape.VERTICAL,0,2);
        blocks[4] = new Block(Shape.HORIZON,1,3);
        blocks[5] = new Block(Shape.VERTICAL,3,2);
        blocks[6] = new Block(Shape.SINGLE,0,4);
        blocks[7] = new Block(Shape.SINGLE,1,4);
        blocks[8] = new Block(Shape.SINGLE,2,4);
        blocks[9] = new Block(Shape.SINGLE,3,4);
        queue.add(init);
        set.add(init.toMask());
        Stack<State> path = new Stack<>();
        while (!queue.isEmpty()) {
            State cur = queue.remove();
            if (cur.isSolved()) { //?????
                System.out.println("done " + cur.getStep() + " steps");
                path.push(cur);
                while(cur.getPre() != null) {
                    path.push(cur.getPre());
                    cur = cur.getPre();
                }
                break;
            } else if (cur.getStep() > 1000) { //????????
                System.out.println("no answer");
                break;
            }
            move(cur, queue, set);
        }
        int step = 0;

        while(!path.isEmpty()) {
            System.out.println("-------------step "+step+++"----------------");
            State s = path.pop();
            s.toPrintedMask().print();
        }
    }

    /**
     * ??????鳢?????????????????
     * @param cur
     * @param queue
     * @param set
     */
    public static void move(State cur , Queue<State> queue, Set<Mask> set) {
        Mask mask = cur.toMask();
        Block[] blocks = cur.getBlocks();
        for (int i=0; i<blocks.length; i++) {
            Block block = blocks[i];
            //up
            if (block.getTop() > 0 && mask.isEmpty(block.getLeft(), block.getTop()-1) && mask.isEmpty(block.getRight(), block.getTop()-1)) {
                State next = new State(cur);
                next.setPre(cur);
                next.setStep(cur.getStep() + 1);
                next.getBlocks()[i].setTop(block.getTop() - 1);
                if (!set.contains(next.toMask())) {
                    set.add(next.toMask());
                    queue.add(next);
                }
            }
            // right
            if (block.getRight() < mask.getCol()-1 && mask.isEmpty(block.getRight()+1, block.getTop()) && mask.isEmpty(block.getRight()+1, block.getBottom())) {
                State next = new State(cur);
                next.setPre(cur);
                next.setStep(cur.getStep() + 1);
                next.getBlocks()[i].setLeft(block.getLeft() + 1);
                if (!set.contains(next.toMask())) {
                    set.add(next.toMask());
                    queue.add(next);
                }
            }
            // down
            if (block.getBottom() < mask.getRow()-1 && mask.isEmpty(block.getLeft(), block.getBottom()+1) && mask.isEmpty(block.getRight(), block.getBottom()+1)) {
                State next = new State(cur);
                next.setPre(cur);
                next.setStep(cur.getStep() + 1);
                next.getBlocks()[i].setTop(block.getTop() + 1);
                if (!set.contains(next.toMask())) {
                    set.add(next.toMask());
                    queue.add(next);
                }
            }
            // left
            if (block.getLeft() > 0 && mask.isEmpty(block.getLeft()-1, block.getTop()) && mask.isEmpty(block.getLeft()-1, block.getBottom())) {
                State next = new State(cur);
                next.setPre(cur);
                next.setStep(cur.getStep() + 1);
                next.getBlocks()[i].setLeft(block.getLeft() - 1);
                if (!set.contains(next.toMask())) {
                    set.add(next.toMask());
                    queue.add(next);
                }
            }
        }
    }

}

enum Shape {
    DOOR,SQUARE,HORIZON,SINGLE,VERTICAL;
}

/**
 * ????????????????
 */
class Block {
    private Shape shape;
    private int left;
    private int top;

    public Block(Shape shape, int left, int top) {
        this.shape = shape;
        this.left = left;
        this.top = top;
    }


    public Shape getShape() {
        return shape;
    }

    public int getLeft() {
        return left;
    }

    public int getTop() {
        return top;
    }

    public void setLeft(int left) {
        this.left = left;
    }

    public void setTop(int top) {
        this.top = top;
    }

    public int getRight() {
        switch (shape) {
            case HORIZON:
            case SQUARE: return left+1;
            case VERTICAL:
            case SINGLE: return left;
        }
        return left;
    }

    public int getBottom() {
        switch (shape) {
            case HORIZON:
            case SINGLE: return top;
            case VERTICAL:
            case SQUARE: return top+1;
        }
        return top;
    }

    public void mask(Mask mask) {
        mask.set(shape,top,left);
    }

}

/**
 * ?????????
 */
class Mask {
    private int[][] map = new int[5][4];

    public Mask() {
        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map[i].length; j++) {
                map[i][j] = 0;
            }
        }
    }

    public int getRow() {
        return map.length;
    }

    public int getCol() {
        return map[0].length;
    }

    public int[][] getMap() {
        return map;
    }

    public void set(Shape shape , int top , int left) {
        map[top][left] = shape.ordinal();
        switch (shape) {
            case HORIZON: map[top][left+1] = shape.ordinal(); break;
            case VERTICAL: map[top+1][left] = shape.ordinal(); break;
            case SQUARE:
                map[top+1][left] = shape.ordinal();
                map[top][left+1] = shape.ordinal();
                map[top+1][left+1] = shape.ordinal(); break;
        }
    }

    public boolean isEmpty(int y , int x) {
        return map[x][y] == 0;
    }

    public void print() {
        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map[i].length; j++) {
                System.out.print("   " + map[i][j]);
            }
            System.out.println();
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Mask)) return false;
        Mask other = (Mask) o;
        if (other.getCol() != getCol() || other.getRow() != getRow() ) return false;
        int[][] otherMap = other.getMap();
        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map[i].length; j++) {
                if (map[i][j] != otherMap[i][j]) return false;
            }
        }
        return true;
    }

    @Override
    public int hashCode() {
        int code = 47;
        for (int[] row : map) {
            for (int m : row) {
                code = 47 * code + m;
            }
        }
        return code;
    }
}

/**
 * ????
 */
class State {
    private Block[] blocks = new Block[10]; //????10???????飬??????
    private int step; //????
    private State pre; //?????????

    public State() {
    }

    public State(State state) {
        Block[] bs = state.getBlocks();
        for (int i = 0; i < bs.length; i++) {
            blocks[i] = new Block(bs[i].getShape(),bs[i].getLeft(),bs[i].getTop());
        }
        this.step = state.getStep();
    }

    public boolean isSolved() {
        Block square = blocks[1];
        return square.getShape() == Shape.SQUARE && square.getLeft() == 1 && square.getTop() == 3;
    }

    public Mask toMask() {
        Mask mask = new Mask();
        for (Block block : blocks) {
            block.mask(mask);
        }
        return mask;
    }

    public Mask toPrintedMask() {
        Mask mask = new Mask();
        for (int i = 0; i < blocks.length; i++) {
            Block block = blocks[i];
            int top = block.getTop();
            int left = block.getLeft();
            Shape shape = block.getShape();
            int val = shape.ordinal()+i;
            mask.getMap()[top][left] = val;
            switch (shape) {
                case HORIZON: mask.getMap()[top][left+1] = val; break;
                case VERTICAL: mask.getMap()[top+1][left] = val; break;
                case SQUARE:
                    mask.getMap()[top+1][left] = val;
                    mask.getMap()[top][left+1] = val;
                    mask.getMap()[top+1][left+1] = val; break;
            }
        }
        return mask;
    }

    public Block[] getBlocks() {
        return blocks;
    }

    public int getStep() {
        return step;
    }

    public void setStep(int step) {
        this.step = step;
    }

    public State getPre() {
        return pre;
    }

    public void setPre(State pre) {
        this.pre = pre;
    }
}

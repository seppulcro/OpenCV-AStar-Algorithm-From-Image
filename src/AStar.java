import java.awt.Point;
import java.util.ArrayList;

public class AStar
{
   int[][]          matrix;
   ArrayList<Point> closedList;

   public AStar(int[][] matrix)
   {
      this.setMatrix(matrix);
   }

   public void setMatrix(int[][] m)
   {
      this.matrix = m;
   }
}

package sample;

import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Shape;
import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

public class Obstacle extends GameComponents {
    private int rotateSpeed;
    private final int numColours = 4;
    private int myFlag;
    private String colours[];
    protected transient Group grp;
    protected transient Group grp1;
    protected transient Star s;

    protected static HashMap<Integer, Color> colourMap;

    protected transient static ArrayList<Integer> shuffler;

    public Obstacle(){
        grp = new Group();
        grp1 = new Group();
        colours =new String[4];
        shuffler = new ArrayList<>(4);
        shuffler.add(1);
        shuffler.add(2);
        shuffler.add(3);
        shuffler.add(4);
        if(Game.getInstance().getScore().getCurrentScore() > 30){
            myFlag = 10;
        }
        else{
            myFlag = 0;
        }
        setColours();
        if(getMyFlag() == 0){
            setRotateSpeed(9000);
        }
        else {
            setRotateSpeed(5000);
        }
    }

    private ArrayList<Integer> chooser(){
        Collections.shuffle(shuffler);
        return shuffler;
    }

    public void setMyColours(String[] col){
        colours = col;
    }

    public String[] getMyColours() {
        return colours;
    }

    public int getMyFlag(){
        return myFlag;
    }

    public void setMyFlag(int flag){
        myFlag = flag;
    }

    public void setColours() {
        chooser();
        colourMap = new HashMap<>();
        colourMap.put(shuffler.get(0), Color.YELLOW);
        colourMap.put(shuffler.get(1), Color.rgb(17, 231, 243 ));
        colourMap.put(shuffler.get(2), Color.rgb(100, 33, 204 ));
        colourMap.put(shuffler.get(3), Color.rgb(243, 17, 130 ));
        for(int i = 0; i<4; i++)
            colours[i] = String.format(""+colourMap.get(i+1));
    }

    public int getRotateSpeed() {
        return rotateSpeed;
    }

    public void setRotateSpeed(int rotateSpeed) {
        this.rotateSpeed = rotateSpeed;
    }

    public void setMyPosition(){
        this.getMyPosition().setY((float)this.pos());
    }

    public Shape[] getShapes(){
        Shape s[] = new Shape[2];
        return s;
    }

    @Override
    public String toString() {
        return "Obstacle{" +
                "rotateSpeed=" + rotateSpeed + ", numColours=" + numColours +
                '}';
    }

    public void serialize(String filename) throws IOException {
        ObjectOutputStream out = null;
        FileOutputStream fil = null;
        try {
            fil = new FileOutputStream(filename);
            out = new ObjectOutputStream (fil);
            System.out.println(this.getClass());
            out.writeObject(this);
        }
        finally {
            out.close();
            assert fil != null;
            fil.close();
        }
    }

    public void deserialize(String filename, AnchorPane pane) throws ClassNotFoundException, IOException{
        ObjectInputStream in = null;
        Obstacle r1;
        FileInputStream fil = null;
        try {
            fil = new FileInputStream(filename);
            in = new ObjectInputStream (fil);
            r1 = (Obstacle) in.readObject();in.close();
        }
        catch (FileNotFoundException e){
            r1 = new RectangleObs();
        }
        finally {
            if(fil!=null)
                fil.close();
        }
    }

    private void writeObject(ObjectOutputStream os) throws IOException {
        try {
            os.defaultWriteObject();
            double x1 = getMyPosition().getX();
            double y1 = getMyPosition().getY();
            os.writeDouble(x1);
            os.writeDouble(y1);
        } catch (Exception e) { e.printStackTrace(); }
    }

    private void readObject(ObjectInputStream is) throws IOException, ClassNotFoundException {
        try {
            is.defaultReadObject();
            double x1 = (is.readDouble());
            double y1 = (is.readDouble());
        } catch (Exception e) { e.printStackTrace(); }
    }

    public void checkCollision(Ball ball) throws FileNotFoundException {
            for (int i = 0; i < 4; i++) {
                Shape intersect = Shape.intersect(ball.ball, (this).getShapes()[i]);
                if (intersect.getBoundsInLocal().getWidth() != -1) {
                    if(!(this).getShapes()[i].getFill().equals(ball.getMyColour())){
                        Game.getInstance().setOver(true);
                    }
                    break;
                }
            }
    }

    public Group rg(){
        return grp;
    }

    @Override
    public Node retGrp() {
        return null;
    }

    public Star retMyStar(){
        return s;
    }
}

import org.frice.game.Game;
import org.frice.game.obj.sub.ShapeObject;
import org.frice.game.resource.graphics.ColorResource;
import org.frice.game.utils.graphics.shape.FCircle;
import org.frice.game.utils.graphics.shape.FRectangle;
import org.frice.game.utils.time.FTimer;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Queue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * Created by ice1000 on 2016/8/23.
 *
 * @author ice1000
 */
public class Demo15 extends Game {
    public static void main(String[] args) {
        new Demo15();
    }

    private int direction = KeyEvent.VK_LEFT, x = 30, y = 20, fx = 10, fy = 10;
    private FTimer timer;
    private boolean isIncreasing = true;
    private Queue<ShapeObject> body;
    private ShapeObject food;

    @Override
    protected void onInit() {
        super.onInit();
        setSize(600, 400);
        timer = new FTimer(30);
        setTitle("Snake game demo");
        addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
            }

            @Override
            public void keyPressed(KeyEvent e) {
                direction = e.getKeyCode();
            }

            @Override
            public void keyReleased(KeyEvent e) {
            }
        });
        body = new LinkedBlockingQueue<>();
        food = generateFood(10, 10);
        addObject(food);
        ShapeObject object = generateBody(30, 20);
        body.add(object);
        addObject(object);
    }

    @Override
    protected void onRefresh() {
        super.onRefresh();
        if (timer.ended()) {
            ShapeObject object;
            switch (direction) {
                case KeyEvent.VK_LEFT:
                    object = generateBody(--x, y);
                    break;
                case KeyEvent.VK_RIGHT:
                    object = generateBody(++x, y);
                    break;
                case KeyEvent.VK_UP:
                    object = generateBody(x, --y);
                    break;
//                case KeyEvent.VK_DOWN:
                default:
                    object = generateBody(x, ++y);
                    break;
            }
            x += 60;
            x %= 60;
            y += 40;
            y %= 40;
            if (x == fx && y == fy) {
                removeObject(food);
                isIncreasing = true;
                fx = getRandom().nextInt(59);
                fy = getRandom().nextInt(39);
                food = generateFood(fx, fy);
                addObject(food);
            }
            body.add(object);
            addObject(object);
            if (!isIncreasing) removeObject(body.poll());
            isIncreasing = false;
        }
    }

    private ShapeObject generateBody(int x, int y) {
        return new ShapeObject(ColorResource.get吾王蓝(), new FRectangle(10, 10), x * 10, y * 10);
    }

    private ShapeObject generateFood(int x, int y) {
        return new ShapeObject(ColorResource.get如果奇迹有颜色那么一定是橙色(), new FCircle(5), x * 10, y * 10);
    }
}

import org.frice.Game;
import org.frice.obj.sub.ShapeObject;
import org.frice.resource.graphics.ColorResource;
import org.frice.util.shape.FCircle;
import org.frice.util.shape.FRectangle;
import org.frice.util.time.FTimer;

import java.awt.event.KeyEvent;
import java.util.Arrays;
import java.util.Queue;
import java.util.Random;
import java.util.concurrent.LinkedBlockingQueue;

import static org.frice.Initializer.launch;

/**
 * Created by ice1000 on 2016/8/23.
 *
 * @author ice1000, KirCute
 */
public class Demo15 extends Game {
	public static void main(String[] args) {
		launch(Demo15.class);
	}

	private int direction = KeyEvent.VK_LEFT, x = 30, y = 20, fx = 10, fy = 10;
	private FTimer timer;
	private boolean isIncreasing = true;
	private Queue<ShapeObject> body;
	private ShapeObject food;
	private Random random = new Random(System.currentTimeMillis());

	@Override
	public void onInit() {
		super.onInit();
		this.setLocationRelativeTo(null);
		this.setSize(600, 400);
		timer = new FTimer(100);
		this.setTitle("Snake game demo");
		this.addKeyListener(null, (KeyEvent e) -> {
			if (Arrays.asList(KeyEvent.VK_LEFT, KeyEvent.VK_RIGHT, KeyEvent.VK_UP, KeyEvent.VK_DOWN).contains(e.getKeyCode()))
				direction = e.getKeyCode();
		}, null);
		body = new LinkedBlockingQueue<>();
		food = generateFood(10, 10);
		this.addObject(food);
		ShapeObject object = generateBody(30, 20);
		body.add(object);
		this.addObject(object);
		this.setAutoGC(false);
		this.setResizable(false);
	}

	@Override
	public void onRefresh() {
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
				case KeyEvent.VK_DOWN:
					object = generateBody(x, ++y);
					break;
				default:
					return;
			}
			// x += 60;
			// x %= 60;
			// y += 40;
			// y %= 40;
			x = this.moved(x, true);
			y = this.moved(y, false);
			if (x == fx && y == fy) {
				this.removeObject(food);
				isIncreasing = true;
				fx = random.nextInt(this.getWidth() / 10 - 1);
				fy = random.nextInt(this.getHeight() / 10 - 1);
				food = generateFood(fx, fy);
				this.addObject(food);
			}
			body.add(object);
			this.addObject(object);
			if (!isIncreasing) this.removeObject(body.poll());
			isIncreasing = false;
		}
	}

	private ShapeObject generateBody(int x, int y) {
		return new ShapeObject(ColorResource.八云蓝, new FRectangle(10, 10), x * 10, y * 10);
	}

	private ShapeObject generateFood(int x, int y) {
		return new ShapeObject(ColorResource.如果奇迹有颜色那么一定是橙色, new FCircle(5), x * 10, y * 10);
	}

	private int moved(int i, boolean isX) {
		return isX ? (i + this.getWidth() / 10 - 2) % (this.getWidth() / 10 - 2) : (i + this.getHeight() / 10 - 4) % (this.getHeight() / 10 - 4);
	}
}

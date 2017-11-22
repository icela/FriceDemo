import org.frice.Game;
import org.frice.anim.move.AccelerateMove;
import org.frice.anim.move.SimpleMove;
import org.frice.obj.SideEffect;
import org.frice.obj.sub.ShapeObject;
import org.frice.platform.adapter.JvmImage;
import org.frice.resource.graphics.ColorResource;
import org.frice.utils.data.FileUtils;
import org.frice.utils.message.FDialog;
import org.frice.utils.shape.FCircle;
import org.frice.utils.shape.FRectangle;
import org.frice.utils.time.FTimer;

import java.util.Random;

import static org.frice.Initializer.launch;

public class Demo7 extends Game {
	public static void main(String[] args) {
		launch(Demo7.class);
	}

	private FTimer timer = new FTimer(3000);
	private ShapeObject object;
	private SideEffect gameOver;

	@Override
	public void onInit() {
		setSize(500, 800);
		setTitle("Flappy bird demo by ice1000");
		object = new ShapeObject(ColorResource.宝强绿, new FCircle(20), 50, 200);
		object.addAnim(AccelerateMove.getGravity());
		addObject(object);
		gameOver = () -> {
			new Thread(() -> FileUtils.image2File((JvmImage) getScreenCut().getImage(), "截屏.png")).start();
			setPaused(true);
			new FDialog(this).show("Game Over");
			System.exit(0);
		};
		addKeyListener(null, e -> {
			object.stopAnims();
			object.addAnim(AccelerateMove.getGravity());
			object.addAnim(new SimpleMove(0, -400));
		}, null);
	}

	@Override
	public void onRefresh() {
		if (object.getY() > getHeight() + 20) gameOver.invoke();
		if (timer.ended()) addObject(getObj());
	}
	Random random = new Random(System.currentTimeMillis());

	private ShapeObject[] getObj() {
		int height = random.nextInt(400);
		return new ShapeObject[]{new ShapeObject(ColorResource.教主黄, new FRectangle(50, height), 550, 0) {{
			addAnim(new SimpleMove(-150, 0));
			addCollider(object, gameOver);
		}}, new ShapeObject(ColorResource.教主黄, new FRectangle(50, getHeight() - height - 400), 550, height + 400) {{
			addAnim(new SimpleMove(-150, 0));
			addCollider(object, gameOver);
		}}};
	}
}

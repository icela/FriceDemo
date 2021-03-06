import org.frice.Game;
import org.frice.anim.move.AccelerateMove;
import org.frice.anim.move.SimpleMove;
import org.frice.obj.SideEffect;
import org.frice.obj.sub.ShapeObject;
import org.frice.resource.graphics.ColorResource;
import org.frice.utils.FileUtils;
import org.frice.utils.shape.FCircle;
import org.frice.utils.shape.FRectangle;
import org.frice.utils.time.FTimer;

import static org.frice.Initializer.launch;

public class Demo7 extends Game {
	public static void main(String[] args) {
		launch(Demo7.class);
	}

	private FTimer timer = new FTimer(3000);
	private ShapeObject object;
	private SideEffect gameOver = () -> {
		new Thread(() -> FileUtils.image2File(getScreenCut().getImage(), "截屏.png")).start();
		dialogShow("Game Over");
		System.exit(0);
	};

	@Override public void onInit() {
		setSize(500, 800);
		setTitle("Flappy bird demo by ice1000");
		object = new ShapeObject(ColorResource.宝强绿, new FCircle(20), 50, 200) {{
			addAnim(AccelerateMove.getGravity());
		}};
		addObject(object);
		addKeyListener(null, e -> {
			object.stopAnims();
			object.addAnim(AccelerateMove.getGravity());
			object.addAnim(new SimpleMove(0, -400));
		}, null);
	}

	@Override public void onRefresh() {
		if (object.getY() > getHeight() + 20) gameOver.invoke();
		if (timer.ended()) addObject(getObj((int) (Math.random() * 400)));
	}

	private ShapeObject[] getObj(int height) {
		return new ShapeObject[]{new ShapeObject(ColorResource.教主黄, new FRectangle(50, height), 550, 0) {{
			addAnim(new SimpleMove(-150, 0));
			addCollider(object, gameOver);
		}}, new ShapeObject(ColorResource.教主黄, new FRectangle(50, getHeight() - height - 400), 550, height + 400) {{
			addAnim(new SimpleMove(-150, 0));
			addCollider(object, gameOver);
		}}};
	}
}

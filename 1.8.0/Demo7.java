import org.frice.Game;
import org.frice.anim.move.AccelerateMove;
import org.frice.anim.move.SimpleMove;
import org.frice.obj.sub.ShapeObject;
import org.frice.resource.graphics.ColorResource;
import org.frice.util.FileUtils;
import org.frice.util.shape.FCircle;
import org.frice.util.shape.FRectangle;
import org.frice.util.time.FTimer;

import java.util.ArrayList;
import java.util.List;

import static org.frice.Initializer.launch;

public class Demo7 extends Game {
	public static void main(String[] args) {
		launch(Demo7.class);
	}

	private FTimer timer = new FTimer(3000);
	private ShapeObject bird;
	private List<ShapeObject> objects = new ArrayList<>();

	@Override
	public void onInit() {
		setSize(500, 800);
		setTitle("Flappy bird demo by ice1000");
		bird = new ShapeObject(ColorResource.宝强绿, new FCircle(20), 50, 200) {{
			addAnim(new AccelerateMove(0, 800));
		}};
		addObject(bird);
		addKeyListener(null, e -> {
			bird.stopAnims();
			bird.addAnim(new AccelerateMove(0, 800));
			bird.addAnim(new SimpleMove(0, -400));
		}, null);
	}

	@Override
	public void onRefresh() {
		if (timer.ended()) addObject(getObj((int) (Math.random() * 400)));
		objects.removeIf(ShapeObject::getDied);
		if (bird.getY() > getHeight() + 20 || objects.stream().anyMatch(o -> o.collides(bird))) {
			new Thread(() -> FileUtils.image2File(getScreenCut().getImage(), "截屏.png")).start();
			dialogShow("Game Over");
			System.exit(0);
		}
	}

	private ShapeObject[] getObj(int height) {
		return new ShapeObject[]{new ShapeObject(ColorResource.八云紫, new FRectangle(50, height), 550, 0) {{
			addAnim(new SimpleMove(-150, 0));
			objects.add(this);
		}}, new ShapeObject(ColorResource.八云紫, new FRectangle(50, getHeight() - height - 400), 550, height + 400) {{
			addAnim(new SimpleMove(-150, 0));
			objects.add(this);
		}}};
	}
}

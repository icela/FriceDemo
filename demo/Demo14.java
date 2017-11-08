import org.frice.Game;
import org.frice.anim.move.AccelerateMove;
import org.frice.anim.move.SimpleMove;
import org.frice.obj.SideEffect;
import org.frice.obj.button.FText;
import org.frice.obj.button.SimpleText;
import org.frice.obj.sub.ShapeObject;
import org.frice.platform.adapter.JvmImage;
import org.frice.resource.graphics.ColorResource;
import org.frice.utils.data.Preference;
import org.frice.utils.message.FDialog;
import org.frice.utils.shape.FCircle;
import org.frice.utils.shape.FRectangle;
import org.frice.utils.time.FTimer;
import org.jetbrains.annotations.NotNull;

import javax.imageio.ImageIO;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.IOException;

import static org.frice.Initializer.launch;

public class Demo14 extends Game {
	public static void main(String[] args) {
		launch(Demo14.class);
	}

	private FTimer timer = new FTimer(3000);
	private FText text;
	private ShapeObject object;
	private SideEffect gameOver;
	private int score = -1, best;
	private Preference preference;
	private static final String key = "FUCK";

	@Override
	public void onInit() {
		setSize(500, 800);
		setAutoGC(false);
		preference = new Preference("fuckGod.properties");
		best = (int) preference.query(key, 0);
		setBackground(ColorResource.IntelliJ_IDEA黑.getColor());
		setTitle("Flappy bird demo by ice1000");
		object = new ShapeObject(ColorResource.宝强绿, new FCircle(20.0), 50.0, 200.0);
		object.addAnim(AccelerateMove.getGravity());
		text = new SimpleText("Press any key to jump", 20, 20);
		addObject(text, object);
		gameOver = () -> {
			new Thread(() -> {
				try {
					ImageIO.write(((JvmImage) getScreenCut().getImage()).getImage(), "png", new File("车祸现场.png"));
				} catch (IOException ignored) {
				}
			}).start();
			new FDialog(this).show("Game Over");
			System.exit(0);
		};
		addKeyPressedEvent(KeyEvent.VK_SPACE, e -> {
			object.getAnims().clear();
			object.addAnim(AccelerateMove.getGravity());
			object.addAnim(new SimpleMove(0, -400));
		});
	}

	@Override
	public void onRefresh() {
		if (object.getY() > getHeight() + 20) gameOver.invoke();
		if (timer.ended()) {
			addObject(getObj());
			score++;
			if (score > best) best = score;
			text.setText("score: " + score + "  best: " + best);
			preference.insert(key, best);
		}
	}

	@NotNull
	private ShapeObject[] getObj() {
		int height = getRandom().nextInt(400);
		return new ShapeObject[]{new ShapeObject(ColorResource.吾王蓝,
				new FRectangle(50, height), 550.0, 0.0) {{
			addAnim(new SimpleMove(-150, 0));
			addCollider(object, gameOver);
		}}, new ShapeObject(ColorResource.吾王蓝,
				new FRectangle(50, getHeight() - height - 400), 550.0, height + 400.0) {{
			addAnim(new SimpleMove(-150, 0));
			addCollider(object, gameOver);
		}}};
	}
}
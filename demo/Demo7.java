import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import org.frice.game.Game;
import org.frice.game.anim.move.*;
import org.frice.game.obj.sub.ShapeObject;
import org.frice.game.platform.adapter.JvmImage;
import org.frice.game.resource.graphics.ColorResource;
import org.frice.game.utils.data.FileUtils;
import org.frice.game.utils.graphics.shape.*;
import org.frice.game.utils.message.FDialog;
import org.frice.game.utils.time.FTimer;

public class Demo7 extends Game {
	public static void main(String[] args) {
		launch(Demo7.class);
	}

	private FTimer timer = new FTimer(3000);
	private ShapeObject object;
	private Function0<Unit> gameOver;

	@Override
	public void onInit() {
		setSize(500, 800);
		setTitle("Flappy bird demo by ice1000");
		object = new ShapeObject(ColorResource.宝强绿, new FCircle(20), 50, 200);
		object.addAnim(AccelerateMove.getGravity());
		addObject(object);
		gameOver = () -> {
			new Thread(() -> FileUtils.image2File((JvmImage) getScreenCut().getImage(), "截屏.png")).start();
			new FDialog(this).show("Game Over");
			System.exit(0);
			return Unit.INSTANCE;
		};
		addKeyListener(null, e -> {
			object.stopAnims();
			object.addAnim(AccelerateMove.getGravity());
			object.addAnim(new SimpleMove(0, -400));
			return Unit.INSTANCE;
		}, null);
	}

	@Override
	public void onRefresh() {
		if (object.getY() > getHeight() + 20) gameOver.invoke();
		if (timer.ended()) addObject(getObj());
	}

	private ShapeObject[] getObj() {
		int height = getRandom().nextInt(400);
		return new ShapeObject[]{new ShapeObject(ColorResource.教主黄, new FRectangle(50, height), 550, 0) {{
			addAnim(new SimpleMove(-150, 0));
			addCollider(object, gameOver);
		}}, new ShapeObject(ColorResource.教主黄, new FRectangle(50, getHeight() - height - 400), 550, height + 400) {{
			addAnim(new SimpleMove(-150, 0));
			addCollider(object, gameOver);
		}}};
	}
}

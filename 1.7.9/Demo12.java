import org.frice.Game;
import org.frice.anim.move.AccelerateMove;
import org.frice.anim.move.SimpleMove;
import org.frice.obj.sub.ShapeObject;
import org.frice.resource.graphics.ColorResource;
import org.frice.utils.shape.FCircle;
import org.frice.utils.shape.FRectangle;
import org.frice.utils.time.FTimer;

import java.util.Random;

import static org.frice.Initializer.launch;

/**
 * An awesome demo
 *
 * @author SuperSodaSea
 */
public class Demo12 extends Game {

	private ColorResource[] colors;
	private FTimer timer2;
	private Random random = new Random(System.currentTimeMillis());

	@Override
	public void onInit() {
		setSize(300, 300);
		timer2 = new FTimer(30);
		colors = new ColorResource[]{ColorResource.东条希, ColorResource.高坂穗乃果, ColorResource.西木野真姬, ColorResource.矢泽妮可, ColorResource.洵濑绘理, ColorResource.星空凛, ColorResource.南小鸟};
		addObject(new ShapeObject(ColorResource.IntelliJ_IDEA黑, new FRectangle(getBounds())));
	}

	@Override
	public void onRefresh() {
		if (timer2.ended()) {
			addObject(new ShapeObject(colors[(int) ((System.currentTimeMillis() / 100) % colors.length)], new FCircle(10), mouse.getX(), mouse.getY()) {{
				addAnim(new AccelerateMove(0, 800));
				addAnim(new SimpleMove((int) (random.nextInt(Demo12.this.getWidth()) - getX()), -10));
			}});
		}
	}

	public static void main(String[] args) {
		launch(Demo12.class);
	}
}

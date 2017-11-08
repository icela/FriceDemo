import org.frice.Game;
import org.frice.anim.move.SimpleMove;
import org.frice.obj.sub.ShapeObject;
import org.frice.resource.graphics.ColorResource;
import org.frice.utils.shape.FCircle;
import org.frice.utils.shape.FRectangle;
import org.frice.utils.time.FTimer;

import static org.frice.Initializer.launch;

/**
 * An awesome demo
 *
 * @author SuperSodaSea
 */
public class Demo11 extends Game {

	private ColorResource colors[];
	private double a = 0;
	private double b = 0;
	private FTimer timer = new FTimer(10);

	@Override
	public void onInit() {
		colors = new ColorResource[]{
				ColorResource.东条希,
				ColorResource.南小鸟,
				ColorResource.园田海未,
				ColorResource.小泉花阳,
				ColorResource.星空凛,
				ColorResource.洵濑绘理,
				ColorResource.矢泽妮可,
				ColorResource.西木野真姬,
				ColorResource.高坂穗乃果
		};
		setSize(1200, 720);
		setTitle("IAmSoSquare Demo");
		setAutoGC(true);
		addObject(new ShapeObject(ColorResource.IntelliJ_IDEA黑, new FRectangle(1200, 720)));
	}

	@Override
	public void onRefresh() {
		super.onRefresh();
		if (timer.ended()) {
			a += 0.0002;
			b += a;
			addObject(new ShapeObject(colors[(int) (System.currentTimeMillis() / 100 % colors.length)],
					new FCircle(2), getWidth() / 2, getHeight() / 2) {{
				addAnim(new SimpleMove((int) (Math.sin(b) * 256), (int) (Math.cos(b) * 256)));
			}});
		}
	}

	public static void main(String[] args) {
		launch(Demo11.class);
	}
}

import org.frice.Game;
import org.frice.anim.move.AccelerateMove;
import org.frice.anim.move.SimpleMove;
import org.frice.obj.FObject;
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
public class Demo13 extends Game {

	private ColorResource[] colors;
	private FTimer timer2;
	private FObject object;

	@Override
	public void onInit() {
		timer2 = new FTimer(30);
		colors = new ColorResource[]{
				ColorResource.东条希,
				ColorResource.高坂穗乃果,
				ColorResource.西木野真姬,
				ColorResource.矢泽妮可,
				ColorResource.洵濑绘理,
				ColorResource.星空凛,
				ColorResource.南小鸟
		};
		object = new ShapeObject(new ColorResource(0xffffff), new FRectangle(getWidth(), 10), 1, getHeight() - 50);
		addObject(object);
		setBackground(new ColorResource(0x2b2b2b).getColor());
	}

	@Override
	public void onRefresh() {
		if (timer2.ended()) {
			addObject(new ShapeObject(colors[(int) ((System.currentTimeMillis() / 100) % colors.length)],
					new FCircle(10), getMousePosition().getX(), getMousePosition().getY()) {{
				addAnim(new SimpleMove((int) (getRandom().nextInt(Demo13.this.getWidth()) - getX()), -500));
				addAnim(AccelerateMove.getGravity(20));
				addCollider(object, () -> {
					getAnims().remove(1);
					addAnim(AccelerateMove.getGravity(20));
				});
			}});
		}
	}

	public static void main(String[] args) {
		launch(Demo13.class);
	}
}

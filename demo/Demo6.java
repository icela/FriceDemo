import org.frice.Game;
import org.frice.anim.move.AccelerateMove;
import org.frice.anim.move.SimpleMove;
import org.frice.anim.scale.SimpleScale;
import org.frice.event.OnMouseEvent;
import org.frice.obj.sub.ShapeObject;
import org.frice.resource.graphics.ColorResource;
import org.frice.utils.message.FLog;
import org.frice.utils.shape.FOval;
import org.frice.utils.time.FTimer;

import static org.frice.Initializer.launch;

/**
 * Created by ice1000 on 2016/8/15.
 *
 * @author ice1000
 */
public class Demo6 extends Game {
	public static void main(String[] args) {
		launch(Demo6.class);
	}

	public FTimer timer = new FTimer(2000);

	@Override
	public void onInit() {
		setSize(900, 900);
	}

	@Override
	public void onRefresh() {
		super.onRefresh();
		if (timer.ended()) {
			addObject(new ShapeObject(ColorResource.基佬紫,
					new FOval(80.0, 120.0), 10, 750) {{
				addAnim(new SimpleScale(1.1, 1.1));
				addAnim(AccelerateMove.getGravity());
				addAnim(new SimpleMove(0, -700));
				addAnim(new SimpleMove(100, 0));
			}});
		}
	}

	@Override
	public void onMouse(OnMouseEvent e) {
		setPaused(!getPaused());
		FLog.e("paused");
	}
}

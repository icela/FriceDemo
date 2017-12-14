import org.frice.Game;
import org.frice.anim.move.AccelerateMove;
import org.frice.obj.button.SimpleText;
import org.frice.obj.sub.ShapeObject;
import org.frice.resource.graphics.ColorResource;
import org.frice.utils.shape.FCircle;
import org.frice.utils.time.FTimer;

import static org.frice.Initializer.launch;

/**
 * Created by ice1000 on 2016/8/29.
 *
 * @author ice1000
 */
public class Demo20 extends Game {
	private FTimer timer = new FTimer(10);

	@Override
	public void onLastInit() {
		SimpleText text = new SimpleText(ColorResource.西木野取款姬, "Intel", 20, 100);
		text.setTextSize(100);
		addObject(text);
	}

	@Override
	public void onRefresh() {
		try {
			if (timer.ended())
				addObject(new ShapeObject(ColorResource.BLUE, new FCircle(30), getMousePosition().x - 30, getMousePosition().y - 30) {{
					addAnim(new AccelerateMove(0, 500));
				}});
		} catch (NullPointerException ignored) {
		}
	}

	public static void main(String[] args) {
		launch(Demo20.class);
	}
}

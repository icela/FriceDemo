package demo;

import org.frice.Game;
import org.frice.anim.move.AccelerateMove;
import org.frice.obj.sub.ShapeObject;
import org.frice.resource.graphics.ColorResource;
import org.frice.utils.shape.FCircle;

import static org.frice.Initializer.launch;

/**
 * Created by ice1000 on 2016/8/29.
 *
 * @author ice1000
 */
public class Demo20 extends Game {
	@Override
	public void onRefresh() {
		addObject(new ShapeObject(ColorResource.BLUE, new FCircle(30), getMousePosition().x, getMousePosition().y) {{
			addAnim(AccelerateMove.getGravity());
		}});
	}

	public static void main(String[] args) {
		launch(Demo20.class);
	}
}

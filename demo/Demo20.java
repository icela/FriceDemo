package demo;

import org.frice.game.Game;
import org.frice.game.anim.move.AccelerateMove;
import org.frice.game.obj.sub.ShapeObject;
import org.frice.game.resource.graphics.ColorResource;
import org.frice.game.utils.graphics.shape.FCircle;

/**
 * Created by ice1000 on 2016/8/29.
 *
 * @author ice1000
 */
public class Demo20 extends Game {
	@Override
	protected void onRefresh() {
		addObject(new ShapeObject(ColorResource.BLUE, new FCircle(30), getMousePosition().x, getMousePosition().y) {{
			getAnims().add(AccelerateMove.getGravity());
		}});
	}

	public static void main(String[] args) {
		new Demo20();
	}
}

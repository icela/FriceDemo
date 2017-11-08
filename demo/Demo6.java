import org.frice.Game;
import org.frice.anim.move.AccelerateMove;
import org.frice.anim.move.SimpleMove;
import org.frice.anim.scale.SimpleScale;
import org.frice.event.OnClickEvent;
import org.frice.obj.sub.ShapeObject;
import org.frice.resource.graphics.ColorResource;
import org.frice.utils.message.FLog;
import org.frice.utils.shape.FOval;
import org.frice.utils.time.FTimeListener;

/**
 * Created by ice1000 on 2016/8/15.
 *
 * @author ice1000
 */
public class Demo6 extends Game {
	public static void main(String[] args) {
		new Demo6();
	}

	@Override
	public void onInit() {
		setSize(900, 900);
		addTimeListener(new FTimeListener(2000, () -> addObject(new ShapeObject(ColorResource.基佬紫,
				new FOval(80.0, 120.0), 10, 750) {{
			addAnim(new SimpleScale(1.1, 1.1));
			addAnim(AccelerateMove.getGravity());
			addAnim(new SimpleMove(0, -700));
			addAnim(new SimpleMove(100, 0));
		}})));
	}

	@Override
	public void onClick(OnClickEvent e) {
		setPaused(!getPaused());
		FLog.e("paused");
	}
}

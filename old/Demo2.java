import org.frice.Game;
import org.frice.Initializer;
import org.frice.anim.move.AccelerateMove;
import org.frice.anim.move.AccurateMove;
import org.frice.obj.sub.ImageObject;
import org.frice.resource.image.WebImageResource;
import org.frice.utils.time.FTimer;
import org.jetbrains.annotations.NotNull;

import static org.frice.Initializer.launch;

/**
 * Demo for accelerate (AccelerateMove), this is a simple gravity mode.
 * <p>
 * Created by ice1000 on 2016/8/15.
 *
 * @author ice1000
 * @since v0.2.1
 */
public class Demo2 extends Game {

	private FTimer timer;
	private ImageObject object1;
	private ImageObject object2;
	public static final String URL = "https://avatars3.githubusercontent.com/u/16398479";

	public static void main(String[] args) {
		launch(Demo2.class);
	}

	@Override
	public void onInit() {
		setSize(800, 800);
		object1 = new ImageObject(new WebImageResource(URL), 0, 620);
		object1.addAnim(new AccelerateMove(0, 10));
		object1.addAnim(new AccurateMove(0, -600));
		object1.addAnim(new AccurateMove(100, 0));
		addObject(object1, object2 = make());
		timer = new FTimer(5000);
	}

	@Override
	public void onRefresh() {
		if (timer.ended()) {
			removeObject(object1, object2);
			addObject(object1 = make(), object2 = make());
		}
	}

	@NotNull
	private ImageObject make() {
		return new ImageObject(
				new WebImageResource(URL),
//				new FileImageResource("1.png"),
				20, 720) {{
			addAnim(new AccelerateMove(0, 10));
			addAnim(new AccurateMove(0, -700));
			addAnim(new AccurateMove(280, 0));
		}};
	}
}

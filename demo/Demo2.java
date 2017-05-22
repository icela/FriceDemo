import org.frice.game.Game;
import org.frice.game.anim.move.AccelerateMove;
import org.frice.game.anim.move.AccurateMove;
import org.frice.game.obj.sub.ImageObject;
import org.frice.game.resource.image.WebImageResource;
import org.frice.game.utils.time.FTimer;
import org.jetbrains.annotations.NotNull;

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

	public static void main(String[] args) {
		new Demo2();
	}

	@Override
	public void onInit() {
		setSize(800, 800);
		object1 = new ImageObject(new WebImageResource("https://avatars3.githubusercontent.com/u/16398479"), 0, 620);
		object1.getAnims().add(new AccelerateMove(0, 10));
		object1.getAnims().add(new AccurateMove(0, -600));
		object1.getAnims().add(new AccurateMove(100, 0));
		addObject(object1);
		addObject(object2 = make());
		timer = new FTimer(5000);
	}

	@Override
	public void onRefresh() {
		if (timer.ended()) {
			removeObject(object1);
			removeObject(object2);
			addObject(object1 = make());
			addObject(object2 = make());
		}
	}

	@NotNull
	private ImageObject make() {
		return new ImageObject(
				new WebImageResource("https://avatars3.githubusercontent.com/u/16398479"),
//				new FileImageResource("1.png"),
				20, 720) {{
			getAnims().add(new AccelerateMove(0, 10));
			getAnims().add(new AccurateMove(0, -700));
			getAnims().add(new AccurateMove(280, 0));
		}};
	}
}

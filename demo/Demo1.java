import org.frice.Game;
import org.frice.anim.move.SimpleMove;
import org.frice.event.OnMouseEvent;
import org.frice.obj.FObject;
import org.frice.obj.sub.ImageObject;
import org.frice.obj.sub.ShapeObject;
import org.frice.resource.graphics.ColorResource;
import org.frice.resource.image.FileImageResource;
import org.frice.utils.shape.FOval;
import org.frice.utils.time.FTimer;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

import static org.frice.Initializer.launch;

/**
 * Demo for the simplest use of Frice Engine
 * Created by ice1000 on 2016/8/13.
 *
 * @author ice1000
 * @since v0.1
 */
public class Demo1 extends Game {

	private ArrayList<FObject> objects = new ArrayList<>();
	private FTimer timer;
	private FTimer timer2;
	private int fuck = 0;
	private int mode = 0;

	@Override
	public void onInit() {
		timer = new FTimer(800);
		timer2 = new FTimer(200);
		setBackground(ColorResource.PINK.getColor());
		setBounds(100, 100, 800, 800);
		setTitle("Fuck Fuck Fuck");
		addObject(new ShapeObject(ColorResource.DARK_GRAY, new FOval(50, 50)) {{
			addAnim(new SimpleMove(10, 20));
		}});
	}

	@Override
	public void onRefresh() {
		if (timer2.ended()) {
			if (fuck > 500) mode = 1;
			if (fuck < 1) mode = 0;
		}
		if (timer.ended()) {
			FObject object;
			switch (mode) {
				case 1:
					object = objects.get(objects.size() - 1);
					removeObject(object);
					objects.remove(object);
					fuck -= 100;
					break;
				case 0:
					fuck += 100;
					object = new ImageObject(new FileImageResource("test.png"), fuck, fuck);
					addObject(object);
					objects.add(object);
					break;
			}
		}
	}

	@Override
	public void onMouse(@NotNull OnMouseEvent onClickEvent) {
//		dialog.show("fuck!!!!!!");
		if (dialogConfirmYesNo("Choose")) {
			dialogShow("Yes!");
		} else {
			dialogShow("No!");
		}
	}

	public static void main(String[] args) {
		launch(Demo1.class);
	}
}

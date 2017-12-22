import org.frice.Game;
import org.frice.anim.rotate.SimpleRotate;
import org.frice.obj.sub.ShapeObject;
import org.frice.resource.graphics.ColorResource;
import org.frice.util.shape.FOval;

import static org.frice.Initializer.launch;

public class DemoRotate extends Game {
	@Override
	public void onInit() {
		setSize(400, 400);
		ShapeObject object = new ShapeObject(ColorResource.西木野取款姬, new FOval(40.0, 80.0), 160.0, 120.0);
		object.addAnim(new SimpleRotate(-10.0));
		ShapeObject object1 = new ShapeObject(ColorResource.八云蓝, new FOval(40.0, 80.0), 160.0, 120.0);
		object1.addAnim(new SimpleRotate(10.0));
		addObject(object, object1);
	}

	public static void main(String[] args) {
		launch(DemoRotate.class);
	}
}

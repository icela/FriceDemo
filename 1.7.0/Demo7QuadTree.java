import org.frice.Game;
import org.frice.anim.move.AccelerateMove;
import org.frice.anim.move.SimpleMove;
import org.frice.obj.AbstractObject;
import org.frice.obj.PhysicalObject;
import org.frice.obj.SideEffect;
import org.frice.obj.sub.ShapeObject;
import org.frice.resource.graphics.ColorResource;
import org.frice.utils.QuadTree;
import org.frice.utils.message.FLog;
import org.frice.utils.shape.FCircle;
import org.frice.utils.shape.FQuad;
import org.frice.utils.shape.FRectangle;
import org.frice.utils.time.FTimer;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import static org.frice.Initializer.launch;

public class Demo7QuadTree extends Game {
	public static void main(String[] args) {
		launch(Demo7QuadTree.class);
	}

	private Random random = new Random();
	private FTimer timer = new FTimer(3000);
	private ShapeObject object;
	private SideEffect gameOver;

	@Override
	public void onInit() {
		setSize(500, 800);
		setTitle("Flappy bird demo by ice1000");
		object = new ShapeObject(ColorResource.宝强绿, new FCircle(20.0), 50.0, 200.0);
		object.getAnims().add(AccelerateMove.getGravity());
		addObject(object);
		gameOver = () -> {
			dialogShow("Game Over");
			System.exit(0);
		};
		addKeyListener(null, e -> {
			object.stopAnims();
			object.addAnim(AccelerateMove.getGravity());
			object.addAnim(new SimpleMove(0, -400));
		}, null);
	}

	@Override
	public void onRefresh() {
		QuadTree quadTree = new QuadTree(0, new FQuad(0, 0, 500, 800));
		if (object.getY() > getHeight() + 20) gameOver.invoke();
		if (timer.ended()) {
			ShapeObject[] newObjects = getObj(random.nextInt(400));
			addObject(newObjects);
			Collections.addAll(objects(), newObjects);
		}

		quadTree.clear();
		objects().stream().filter(obj -> obj instanceof PhysicalObject).map(obj -> (PhysicalObject) obj).forEachOrdered(quadTree::insert);
		ArrayList<List<PhysicalObject>> returnObjects = new ArrayList<>();
		quadTree.retrieve(returnObjects, object);
		FLog.e(" size " + returnObjects.size());

		returnObjects.stream().filter(objs -> objs.contains(object)).forEachOrdered(rets -> {
			rets.remove(object);
			rets.forEach(physicalObject -> {
				((ShapeObject) physicalObject).setRes(ColorResource.基佬紫);
				if (object.collides(physicalObject)) gameOver.invoke();
			});
		});
	}

	@NotNull
	private List<AbstractObject> objects() {
		return getLayers()[0].getObjects();
	}

	private ShapeObject[] getObj(int height) {
		return new ShapeObject[]{new ShapeObject(ColorResource.教主黄, new FRectangle(50, height), 550, 0) {{
			addAnim(new SimpleMove(-150, 0));
			addCollider(object, gameOver);
		}}, new ShapeObject(ColorResource.教主黄, new FRectangle(50, getHeight() - height - 400), 550, height + 400) {{
			addAnim(new SimpleMove(-150, 0));
			addCollider(object, gameOver);
		}}};
	}
}

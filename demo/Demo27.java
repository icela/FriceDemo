package demo;

import org.frice.game.Game;
import org.frice.game.anim.move.AccelerateMove;
import org.frice.game.anim.move.AccurateMove;
import org.frice.game.anim.move.SimpleMove;
import org.frice.game.obj.FObject;
import org.frice.game.obj.PhysicalObject;
import org.frice.game.obj.sub.ShapeObject;
import org.frice.game.resource.graphics.ColorResource;
import org.frice.game.utils.graphics.shape.FCircle;
import org.frice.game.utils.graphics.shape.FQuad;
import org.frice.game.utils.graphics.shape.FRectangle;
import org.frice.game.utils.message.FDialog;
import org.frice.game.utils.message.log.FLog;
import org.frice.game.utils.time.FTimer;

import java.util.ArrayList;
import java.util.Collections;

public class Demo27 extends Game {
	public static void main(String[] args) {
		new Demo27();
	}

	private FTimer timer = new FTimer(3000);
	private ShapeObject object;
	private FObject.OnCollideEvent gameOver;

	@Override
	protected void onInit() {
		setSize(500, 800);
		setTitle("Flappy bird demo by ice1000");
		object = new ShapeObject(ColorResource.宝强绿, new FCircle(20.0), 50.0, 200.0);
		object.getAnims().add(AccelerateMove.getGravity());
		addObject(object);
		gameOver = () -> {
			new FDialog(this).show("Game Over");
			System.exit(0);
		};
		listenKeyPressed(e -> {
			object.getAnims().clear();
			object.getAnims().add(AccelerateMove.getGravity());
			object.getAnims().add(new SimpleMove(0, -400));
		});
	}

	@Override
	protected void onRefresh() {
		QuadTree quadTree = new QuadTree(0, new FQuad(0, 0, 500, 800));

		if (object.getY() > getHeight() + 20) gameOver.handle();

		ShapeObject[] newObjects;

		if (timer.ended()) {
			newObjects = getObj();
			addObjects(newObjects);
			Collections.addAll(objects, newObjects);
		}

		quadTree.clear();
		objects.stream().filter(abstractObject ->
				abstractObject instanceof PhysicalObject).forEach(abstractObject -> {
			quadTree.insert((PhysicalObject) abstractObject);
		});

		ArrayList<ArrayList<PhysicalObject>> returnObjects = new ArrayList<>();

		returnObjects.clear();
		quadTree.retrieve(returnObjects, object);

		FLog.error(" size " + returnObjects.size());

		returnObjects.stream().filter(returnObject -> returnObject.contains(object)).forEach(returnObject -> {
			returnObject.remove(object);
			for (PhysicalObject physicalObject : returnObject) {

				((ShapeObject) physicalObject).setRes(ColorResource.基佬紫);

				if (rectCollideRect(object, physicalObject)) {
					gameOver.handle();
				}
			}
		});
	}

	public boolean rectCollideRect(PhysicalObject A, PhysicalObject B) {
		return A.getX() + A.getWidth() >= B.getX()
				&& B.getY() <= A.getY() + A.getHeight()
				&& A.getX() <= B.getX() + B.getWidth()
				&& A.getY() <= B.getY() + B.getHeight();
	}


	private ShapeObject[] getObj() {
		int height = getRandom().nextInt(400);
		return new ShapeObject[]{
				new ShapeObject(ColorResource.教主黄,
						new FRectangle(50, height), 550.0, 0.0) {{
					getAnims().add(new AccurateMove(-150, 0));
				}},
				new ShapeObject(ColorResource.教主黄,
						new FRectangle(50, height), 600.0, 0.0) {{
					getAnims().add(new AccurateMove(-150, 0));
				}},
				new ShapeObject(ColorResource.教主黄,
						new FRectangle(50, height), 700, 0.0) {{
					getAnims().add(new SimpleMove(-150, 0));
				}},
				new ShapeObject(ColorResource.教主黄,
						new FRectangle(50, height), 900, 0.0) {{
					getAnims().add(new SimpleMove(-150, 0));
				}},
				new ShapeObject(ColorResource.教主黄,
						new FRectangle(50, getHeight() - height - 400), 600.0, height + 400.0) {{
					getAnims().add(new SimpleMove(-150, 0));
				}},
				new ShapeObject(ColorResource.教主黄,
						new FRectangle(50, getHeight() - height - 400), 550.0, height + 400.0) {{
					getAnims().add(new SimpleMove(-150, 0));
				}}};
	}
}
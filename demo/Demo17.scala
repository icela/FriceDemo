import org.frice.Game
import org.frice.anim.move.SimpleMove
import org.frice.obj.sub.ShapeObject
import org.frice.resource.graphics.ColorResource
import org.frice.utils.shape.FCircle
import org.frice.utils.time.FTimer
import org.frice.Initializer.launch

/**
	* Created by ice1000 on 2016/8/28.
	*
	* @author ice1000
	*/
class Demo17 extends Game {

	var obj: ShapeObject = _
	val timer = new FTimer(200)

	override def onInit(): Unit = {
		super.onInit()
		super.setAutoGC(true)
	}

	override def onRefresh(): Unit = {
		super.onRefresh()
		if (timer.ended()) {
			obj = new ShapeObject(ColorResource.西木野真姬, new FCircle(10), 10, 10)
			obj setMass 5.0
			obj.addForce(0, 50)
			obj.getAnims add new SimpleMove(400, 0)
			super.addObject(obj)
		}
	}
}

object Demo17 {
	def main(args: Array[String]): Unit = {
		launch(new Demo17)
	}
}
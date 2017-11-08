import kotlin.jvm.functions.Function1
import org.frice.Game
import org.frice.obj.effects.FunctionEffect
import org.frice.utils.time.FTimer
import org.frice.Initializer.launch

/**
	* Created by ice1000 on 2016/8/28.
	*
	* @author ice1000
	*/
class Demo18 extends Game {

	var obj: FunctionEffect = _
	val timer = new FTimer(50)
	var i = 5.0

	override def onInit(): Unit = {
		super.onInit()
		super.setAutoGC(true)
		obj = getFunction(5)
		super.addObject(obj)
	}

	override def onRefresh(): Unit = {
		super.onRefresh()
		if (timer.ended()) {
			i += 0.3
			super.removeObject(obj)
			obj = getFunction(i)
			super.addObject(obj)
		}
	}

	def getFunction(d: Double): Function1[Double, Double] = new FunctionEffect(
		new Function1[Double, Double] {
			override def invoke(double: Double): Double = Math.sin(double / d) * 100 + 200
		}, 10, 10, 600, 500)
}

object Demo18 {
	def main(args: Array[String]): Unit = {
		launch(new Demo18)
	}
}
import org.frice.Game
import org.frice.anim.move.AccelerateMove
import org.frice.anim.move.SimpleMove
import org.frice.event.OnMouseEvent
import org.frice.launch
import org.frice.obj.SideEffect
import org.frice.obj.sub.ImageObject
import org.frice.obj.sub.ShapeObject
import org.frice.resource.graphics.ColorResource
import org.frice.resource.image.ImageResource
import org.frice.resource.image.WebImageResource
import org.frice.utils.message.FDialog
import org.frice.utils.message.FLog
import org.frice.utils.shape.FRectangle
import org.frice.utils.unless
import java.awt.MouseInfo
import java.util.*

/**
 * Created by ifdog on 2016/8/22.
 *
 * @author ifdog, ice1000
 */
class Demo16 : Game() {
	private lateinit var 拍: ShapeObject
	private lateinit var 球: ImageObject
	private lateinit var 底部: ShapeObject
	private var 发球 = false
	private var xa = -200
	private var ya = -200
	private var r = 5.0
	private var sum = 0

	override fun onInit() {
		setBounds(100, 100, 800, 600)
		title = "蛤蛤打砖块"
		r = 32.0
		拍 = ShapeObject(ColorResource.BLACK, FRectangle(100, 15), 100.0, 500.0)
		球 = ImageObject(ImageResource.fromWeb("http://download.easyicon.net/png/540680/32/"), 120.0, 470.0)
		底部 = ShapeObject(ColorResource.BLACK, FRectangle(850, 100), -20.0, 600.0)
		addObject(拍)
		addObject(球)
		addObject(底部)
		addBlocks()
	}

	override fun onRefresh() {
		val point = MouseInfo.getPointerInfo().location
		val theX = point.x - this.x.toDouble() - 拍.width / 2
		if (0 < theX && theX < width - 拍.width) {
			拍.x = theX
			if (!发球) 球.x = 拍.x + 拍.width / 2 - 球.width / 2
		}
		if (球.y + r > height) {
			FDialog(this).show("Game over")
			System.exit(0)
		}
		if (球.y + r > 480 && 球.y + r < 拍.y && 球.x + r > 拍.x && 球.x + r < 拍.x + 拍.width) {
			球.anims.clear()
			球.y = 460.0
			ya = (-1.05 * ya).toInt()
			球.anims.add(SimpleMove(xa, ya))
			FLog.v("q")
		}
		if (球.y + r < 0.0) {
			球.anims.clear()
			球.y = -5.0
			ya = (-1.05 * ya).toInt()
			球.anims.add(SimpleMove(xa, ya))
			FLog.v("w")
		}
		if (球.x + r < 0.0) {
			球.anims.clear()
			xa = (-1.05 * xa).toInt()
			球.x = -5.0
			球.anims.add(SimpleMove(xa, ya))
			FLog.v("e")
		}
		if (球.x + r > 800.0) {
			球.anims.clear()
			xa = (-1.05 * xa).toInt()
			球.x = 795.0
			球.anims.add(SimpleMove(xa, ya))
			FLog.v("r")
		}
	}

	val random = Random(System.currentTimeMillis())

	override fun onMouse(e: OnMouseEvent) {
		unless(发球) {
			发球 = true
			xa = ((random.nextGaussian() - 0.5) * 50 + 200).toInt()
			ya = 0 - ((random.nextGaussian() - 0.5) * 50 + 200).toInt()
			球.anims.add(SimpleMove(xa, ya))
		}
	}


	private fun check(activeObj: ImageObject, staticObj: ImageObject) {
		val x1 = staticObj.x
		val x2 = staticObj.x + staticObj.width
		val y1 = staticObj.y
		val y2 = staticObj.y + staticObj.height
		val x = activeObj.x + r
		val y = activeObj.y + r
		when {
			(x > x1 - 5) && (x < x2 + 5) && (y <= y1 + 5) -> ya = (-1.05 * ya).toInt()
			(x > x1 - 5) && (x < x2 + 5) && (y > y2 - 5) -> ya = (-1.05 * ya).toInt()
			(x <= x1 + 5) && (y > y1 - 5) && (y <= y2 + 5) -> xa = (-1.05 * xa).toInt()
			(x > x2 - 5) && (y > y1 - 5) && (y < y2 + 5) -> xa = (-1.05 * xa).toInt()
		}
	}

	fun addBlocks() {
		val y = WebImageResource("http://download.easyicon.net/png/1147697/24/")
		(0..9).forEach { i ->
			(1..8).forEach { j ->
				val t = ImageObject(y, (80 * i).toDouble(), (20 + j * 25).toDouble())
				addObject(t)
				球.targets += t to SideEffect {
					if (t.anims.isEmpty()) {
						t.addAnim(AccelerateMove.getGravity())
						t.targets += 底部 to SideEffect {
							t.died = true
							removeObject(t)
							sum--
							FLog.v(sum)
						}
						t.targets += 拍 to SideEffect {
							removeObject(t)
							t.died = true
							sum++
							FLog.v(sum)
						}
						球.anims.clear()
						check(球, t)
						球.addAnim(SimpleMove(xa, ya))
					}
				}
			}
		}
	}

	companion object {
		@JvmStatic
		fun main(args: Array<String>) {
			launch(Demo16::class.java)
		}
	}
}

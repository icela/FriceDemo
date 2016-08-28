import org.frice.game.Game
import org.frice.game.anim.move.AccelerateMove
import org.frice.game.anim.move.SimpleMove
import org.frice.game.event.OnClickEvent
import org.frice.game.obj.collide.OnCollideEvent
import org.frice.game.obj.sub.ImageObject
import org.frice.game.obj.sub.ShapeObject
import org.frice.game.resource.graphics.ColorResource
import org.frice.game.resource.image.WebImageResource
import org.frice.game.utils.graphics.shape.FRectangle
import org.frice.game.utils.message.FDialog
import org.frice.game.utils.message.log.FLog
import java.awt.MouseInfo
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
	private val url = ""
	private var sum = 0

	public override fun onInit() {
		setBounds(100, 100, 800, 600)
		title = "蛤蛤打砖块"
		r = 32.0
		拍 = ShapeObject(ColorResource.BLACK, FRectangle(100, 15), 100.0, 500.0)
		//球 = ShapeObject(ColorResource.南小鸟,FCircle(r),120.0,489.0)
		球 = ImageObject(WebImageResource("http://download.easyicon.net/png/540680/32/"), 120.0, 470.0)
		底部 = ShapeObject(ColorResource.BLACK, FRectangle(1000, 100), -100.0, 590.0)
		addObject(拍)
		addObject(球)
		addObject(底部)
		AddBlocks()

	}

	public override fun onRefresh() {
		val point = MouseInfo.getPointerInfo().location
		val theX = point.x - this.x.toDouble() - 拍.width / 2
		if (0 < theX && theX < 800 - 拍.width) {
			拍.x = theX
			if (!发球) {
				球.x = 拍.x + 拍.width / 2 - 球.width / 2
			}
		}
		if (球.y + r > 600) {
			FDialog(this).show("Game over")
			System.exit(0)
		}
		if (球.y + r > 480 && 球.y + r < 500 && 球.x + r > 拍.x && 球.x + r < 拍.x + 拍.width) {
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
		拍.scale(Pair(((10 + sum)).toDouble() / 10, 1.0))
	}

	public override fun onClick(e: OnClickEvent?) {
		if (发球) return
		发球 = true
		xa = ((random.nextGaussian() - 0.5) * 50 + 200).toInt()
		ya = 0 - ((random.nextGaussian() - 0.5) * 50 + 200).toInt()
		球.anims.add(SimpleMove(xa, ya))
	}


	public fun Check(active_obj: ImageObject, static_obj: ImageObject) {
		val x1 = static_obj.x
		val x2 = static_obj.x + static_obj.width
		val y1 = static_obj.y
		val y2 = static_obj.y + static_obj.height
		val x = active_obj.x + r
		val y = active_obj.y + r
		when {
			(x > x1 - 5) && (x < x2 + 5) && (y <= y1 + 5) -> ya = (-1.05 * ya).toInt()
			(x > x1 - 5) && (x < x2 + 5) && (y > y2 - 5) -> ya = (-1.05 * ya).toInt()
			(x <= x1 + 5) && (y > y1 - 5) && (y <= y2 + 5) -> xa = (-1.05 * xa).toInt()
			(x > x2 - 5) && (y > y1 - 5) && (y < y2 + 5) -> xa = (-1.05 * xa).toInt()
		}
	}

	fun AddBlocks() {
		val x = random
		val y = WebImageResource("http://download.easyicon.net/png/1147697/24/")
		val colors = arrayOf(ColorResource.BLUE, ColorResource.CYAN, ColorResource.GREEN, ColorResource.WHITE, ColorResource.PINK,
				ColorResource.MAGENTA, ColorResource.RED, ColorResource.YELLOW, ColorResource.SHIT_YELLOW)
		for (i in 0..9) {
			for (j in 1..8) {
				//val t = ShapeObject(colors[x.nextInt(colors.size)],FRectangle(75,15),(80*i).toDouble(),(20+j*20).toDouble())
				val t = ImageObject(y, (80 * i).toDouble(), (20 + j * 25).toDouble())
				addObject(t)
				球.targets.add(Pair(t, object : OnCollideEvent {
					override fun handle() {
						if (!t.anims.isEmpty()) return
						t.anims.add(AccelerateMove.getGravity())
						t.targets.add(Pair(底部, object : OnCollideEvent {
							override fun handle() {
								t.died = true
								removeObject(t)
								sum--
								FLog.v(sum)
							}
						}))
						t.targets.add(Pair(拍, object : OnCollideEvent {
							override fun handle() {
								removeObject(t)
								t.died = true
								sum++
								FLog.v(sum)
							}
						}))
						球.anims.clear()
						Check(球, t)
						球.anims.add(SimpleMove(xa, ya))
					}
				}))
			}
		}
	}
	
	companion object {
		@JvmStatic fun main(args: Array<String>) {
			Demo16()
		}
	}
}

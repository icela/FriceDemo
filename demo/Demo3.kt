import org.frice.Game
import org.frice.launch
import org.frice.obj.sub.ImageObject
import org.frice.resource.image.FileImageResource
import org.frice.utils.message.FLog
import org.frice.utils.time.FTimer
import java.awt.Rectangle
import java.io.File
import java.util.*

/**
 * Demo for timer
 *
 * Created by ice1000 on 2016/8/13.
 * @author ice1000
 * @since v0.1
 */
class Demo3 : Game() {
	private val dickTimer = FTimer(1000)

	private var fuck = 0.0

	private val objList = arrayListOf<ImageObject>()
	private var mode = 0
	override fun onInit() {
		bounds = Rectangle(100, 100, 640, 480)
		title = "Demo3 of Frice"
//		refreshPerSecond = 100
//		back = ColorResource.BLUE
	}

	override fun onRefresh() {
		if (dickTimer.ended()) {
			val texture = FileImageResource("tres" + File.separator + "display.png")
			val obj: ImageObject
			if (fuck > 300) mode = 1 else if (fuck < 1) mode = 0
			when (mode) {
				0 -> {
					obj = ImageObject(texture, fuck, fuck)
					objList.add(obj)
					addObject(obj)
					fuck += 100
				}
				1 -> {
					obj = objList[objList.size - 1]
					objList.remove(obj)
					removeObject(obj)
					fuck -= 100
				}
			}
			FLog.v("objList.size = ${objList.size}")
		}
	}

	companion object {
		@JvmStatic
		fun main(args: Array<String>) {
			launch(Demo3::class.java)
		}
	}
}
